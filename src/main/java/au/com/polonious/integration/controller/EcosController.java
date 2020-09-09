package au.com.polonious.integration.controller;

import au.com.polonious.integration.dtos.ecosDto.*;
import au.com.polonious.integration.dtos.frissDto.FrissResponseCreateCase;
import au.com.polonious.integration.dtos.frsDto.ContactInfo;
import au.com.polonious.integration.dtos.frsDto.PrimaryAddress;
import au.com.polonious.integration.utils.EcosFeignClient;
import au.com.polonious.integration.utils.PoloniusUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@Log
public class EcosController {
    @Autowired
    EcosFeignClient ecosFeignClient;
    @Autowired
    ResourceLoader resourceLoader;
    final List<String> xmlInput = Arrays.asList("Y2VAC46318_1_SOAP11_OUTBOUND_REQUEST_2020_07_06_T_07_38_03_0760.xml",
            "Y2VAP46317_1_SOAP11_OUTBOUND_REQUEST_2020_07_06_T_08_06_43_0731.xml");

    @GetMapping("input/xml")
    public String getInput(@RequestParam int index) {
        try
        {
            Resource resource = resourceLoader.getResource(String.format("classpath:xml/%s", xmlInput.get(index)));
            InputStream inputStream = resource.getInputStream();

            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String data = new String(bdata, StandardCharsets.UTF_8);
            log.info(data);
            return data;
        }
        catch (IOException e)
        {
            log.warning(String.format("IOException: %s", e));
            return "Error: Value Not found";
        }
    }
    public List<XmlInput> getAllInput() {
        List<XmlInput> resultSet = new ArrayList<>();
        for (int i = 0; i < xmlInput.size(); i++) {
            XmlInput input = new XmlInput(i, xmlInput.get(i), getInput(i));
            resultSet.add(input);
        }
        return resultSet;
    }
    @PostMapping(value = "ecos/createCase",
            consumes = { MediaType.APPLICATION_XML_VALUE}, // "application/xml;charset=UTF-8",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<FrissResponseCreateCase> createCase(@RequestBody EcosPayloadDto payload) throws Exception{
        log.info("Payload " + payload.toString());

        //  Get Token
        String token = PoloniusUtil.getToken();

        //  Map Ecos Xml payload to EcosCreateCaseDto as required by Polonius
        EcosCreateCaseDto ecosCreateCaseDto = createEcosDto(payload);

        ObjectMapper mapper = new ObjectMapper();

        String dtoAsString = mapper.writeValueAsString(ecosCreateCaseDto);


        //  Use the above created payload to create a case in Polonius system
        FrissResponseCreateCase createCaseResponse = ecosFeignClient.createEcosCase(ecosCreateCaseDto);

        //  The conventional RestTemplate tries to send postBody as xml, hence used FeignClient
//        ResponseEntity<FrissResponseCreateCase> createCaseResponse = PoloniusUtil.ecosCreateCase(token, ecosCreateCaseDto, dtoAsString);
        if (createCaseResponse == null){
            throw new RuntimeException("Unable to create case");
        }

        //  Create ContactInfo from the Friss Payload and then (1) find/create contact, (2) Link with task
        List<ContactInfo> allContacts = getAllContacts(payload);
        for (int i = 0; i < allContacts.size(); i++) {
            ContactInfo contactInfo = allContacts.get(i);
            Long personId = PoloniusUtil.findOrCreatePerson(token, contactInfo.getPersonTypeDescription(), contactInfo);

            log.info("PersonId = " + personId);
            if (personId != null){
                contactInfo.getRoles().stream().forEach(c -> {
                    PoloniusUtil.createTaskPersonLink(token, createCaseResponse.getTaskId(), c, personId);
                });
            }
        }

        return ResponseEntity.ok(createCaseResponse);
    }

    private List<ContactInfo> getAllContacts(EcosPayloadDto payload) {
        List<ContactInfo> contactInfoList = new ArrayList<>();
        List<EcosPerson> personObjects = payload.getBody().getReferralRequest().getPersonInfo();
        System.out.printf("Found %s contacts\n", personObjects.size());

        for (int i = 0; i < personObjects.size(); i++) {
            EcosPerson personObject = personObjects.get(i);
            ContactInfo contactInfo = createContactInfo(personObject);

            contactInfoList.add(contactInfo);
        }
        return contactInfoList;
    }

    private ContactInfo createContactInfo(EcosPerson personObject) {
        ContactInfo contactInfo = new ContactInfo();
        //  Use person uid as unique identifier. If blank, use a combination of firstName+lastName
        if (personObject.getPersonUid() != null && !personObject.getPersonUid().isEmpty())
            contactInfo.setContactPID(personObject.getPersonUid());
        else{
            String uniqueId = "" + (personObject.getName().getFirstname()!= null? personObject.getName().getFirstname(): "")
                    + (personObject.getName().getLastname() != null? ("_"+ personObject.getName().getLastname()): "");
            contactInfo.setContactPID(uniqueId);
        }

        //  Makeshift arrangement to filter out personDescriptionType other than Claimant and Insured
        if (personObject.getPersonType().equals("Claimant") || personObject.getPersonType().equals("Insured"))
            contactInfo.setPersonTypeDescription(personObject.getPersonType());
        else contactInfo.setPersonTypeDescription("Staff");

        //  Add a role DRIVER for the time being to test.
        contactInfo.getRoles().add(personObject.getPersonType());

        contactInfo.setFirstName(personObject.getName().getFirstname());
        contactInfo.setMiddleName("");
        contactInfo.setLastName(personObject.getName().getLastname());
        contactInfo.setPrimaryAddress(createAddress(personObject.getAddress()));
        contactInfo.setPhoneNumber(personObject.getPhone());
        return contactInfo;
    }
    private PrimaryAddress createAddress(EcosAddress addressObject){
        if (addressObject == null) return null;
        PrimaryAddress primaryAddress = new PrimaryAddress();

        if (addressObject.getAddressline1() != null) primaryAddress.setAddressLine1(addressObject.getAddressline1());
        if (addressObject.getCity() != null) primaryAddress.setCity(addressObject.getCity());
        if (addressObject.getZip() != null) primaryAddress.setZipCode(addressObject.getZip());
        if (addressObject.getCountry() != null) primaryAddress.setCountry(addressObject.getCountry());
        return primaryAddress;
    }

    private EcosCreateCaseDto createEcosDto(EcosPayloadDto payload) {
        ReferralRequest referralRequest = payload.getBody().getReferralRequest();
        EcosCreateCaseDto ecosCreateCaseDto = new EcosCreateCaseDto();
        ecosCreateCaseDto.setDescription("");
        ecosCreateCaseDto.setLossState2("California");
        ecosCreateCaseDto.setLob(referralRequest.getClaimInfo().getLob());
        ecosCreateCaseDto.setBusinessTypeDesc(referralRequest.getClaimInfo().getBusinessTypeDesc());

        ecosCreateCaseDto.setLossDate(referralRequest.getClaimInfo().getLossDate());
        ecosCreateCaseDto.setLossLocAddrLine1(referralRequest.getClaimInfo().getLossLocation().getAddressline1());
        ecosCreateCaseDto.setLossLocCity(referralRequest.getClaimInfo().getLossLocation().getCity());
        ecosCreateCaseDto.setLossLocState(referralRequest.getClaimInfo().getLossLocation().getState());
        ecosCreateCaseDto.setLossLocCountry(referralRequest.getClaimInfo().getLossLocation().getCountry());
        ecosCreateCaseDto.setLossLocZip(referralRequest.getClaimInfo().getLossLocation().getZip());

        ecosCreateCaseDto.setBusinessTypeCode(referralRequest.getClaimInfo().getBusinessTypeCode());
        ecosCreateCaseDto.setClaim_sym(referralRequest.getClaimInfo().getClaim_sym());
        ecosCreateCaseDto.setClaimDesc(referralRequest.getClaimInfo().getClaimDesc());
        ecosCreateCaseDto.setClaimNumber(referralRequest.getClaimInfo().getClaimNumber());
        ecosCreateCaseDto.setClaimOffice(referralRequest.getClaimInfo().getClaimOffice());
        ecosCreateCaseDto.setClaimPlusIndicator(referralRequest.getClaimInfo().getClaimPlusIndicator());
        ecosCreateCaseDto.setClaimStatusAtReferral(referralRequest.getClaimInfo().getClaimStatusAtReferral());
        ecosCreateCaseDto.setEventNum(referralRequest.getClaimInfo().getEventNum());
        ecosCreateCaseDto.setExposureId(referralRequest.getClaimInfo().getExposureId());
        ecosCreateCaseDto.setFinancialTotalReserves(referralRequest.getClaimInfo().getFinancialTotalReserves().getTotalReserves());
        ecosCreateCaseDto.setHubOffice(referralRequest.getClaimInfo().getHubOffice());
        ecosCreateCaseDto.setImportReason(referralRequest.getImportReason());
        ecosCreateCaseDto.setLargeLoss(referralRequest.getClaimInfo().getLargeLoss());
        ecosCreateCaseDto.setLossDesc(referralRequest.getClaimInfo().getLossDesc());

        ecosCreateCaseDto.setPolicyState(referralRequest.getPolicyInfo().getPolicyState());
        ecosCreateCaseDto.setPolicyEffectiveDate(referralRequest.getPolicyInfo().getPolicyEffectiveDate());
        ecosCreateCaseDto.setPolicyExpiryDate(referralRequest.getPolicyInfo().getPolicyExpiryDate());
        ecosCreateCaseDto.setPolicyNum(referralRequest.getPolicyInfo().getPolicyNum());
        ecosCreateCaseDto.setPolicyOrigEffectiveDate(referralRequest.getPolicyInfo().getPolicyOrigEffectiveDate());
        ecosCreateCaseDto.setPolicyState2("California");

        ecosCreateCaseDto.setReferralId(referralRequest.getReferralId());
        ecosCreateCaseDto.setReferralSource(referralRequest.getReferralSource());
        ecosCreateCaseDto.setReferralNote(referralRequest.getReferralNote().replace("|", "\n"));
        return ecosCreateCaseDto;
    }

}
