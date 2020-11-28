package au.com.polonious.integration.controller;

import au.com.polonious.integration.dtos.ecosDto.*;
import au.com.polonious.integration.dtos.frissDto.FrissResponseCreateCase;
import au.com.polonious.integration.dtos.frsDto.ContactInfo;
import au.com.polonious.integration.service.impl.Mapper;
import au.com.polonious.integration.utils.PoloniusFeignClient;
import au.com.polonious.integration.utils.PoloniusUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
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
    PoloniusFeignClient poloniusFeignClient;
    @Autowired
    Mapper mapper;
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
        PoloniusCreateCaseDto poloniusCreateCaseDto = mapper.createEcosDto(payload.getBody().getReferralRequest());

        ObjectMapper mapper = new ObjectMapper();

        String dtoAsString = mapper.writeValueAsString(poloniusCreateCaseDto);


        //  Use the above created payload to create a case in Polonius system
        FrissResponseCreateCase createCaseResponse = poloniusFeignClient.createEcosCase(poloniusCreateCaseDto);

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
            ContactInfo contactInfo = mapper.createContactInfo(personObject);

            contactInfoList.add(contactInfo);
        }
        return contactInfoList;
    }


}
