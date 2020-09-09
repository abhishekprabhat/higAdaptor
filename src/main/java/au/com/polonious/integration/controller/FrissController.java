package au.com.polonious.integration.controller;

import au.com.polonious.integration.dtos.frissDto.objectDto.*;
import au.com.polonious.integration.dtos.frissDto.FrissCreateCaseDto;
import au.com.polonious.integration.dtos.frissDto.FrissResponseCreateCase;
import au.com.polonious.integration.dtos.frissDto.FrsPayloadDTO;
import au.com.polonious.integration.dtos.frsDto.ContactInfo;
import au.com.polonious.integration.dtos.frsDto.IncidentInfo;
import au.com.polonious.integration.dtos.frsDto.PrimaryAddress;
import au.com.polonious.integration.utils.FrissUtil;
import au.com.polonious.integration.utils.PoloniusUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("friss")
public class FrissController {
    @PostMapping("createCase")
    public ResponseEntity<FrissResponseCreateCase> createCase(@RequestBody FrsPayloadDTO payload) throws Exception {

        //  Get Token
        String token = PoloniusUtil.getToken();

        //  Map Hierarchical payload to FrissCreateCaseDto as required by Polonius
        FrissCreateCaseDto frissCreateCaseDto = createFrissDto(payload);

        //  Use the above created payload to create a case in Polonius system
        ResponseEntity<FrissResponseCreateCase> createCaseResponse = PoloniusUtil.frissCreateCase(token, frissCreateCaseDto);
        if (createCaseResponse == null){
            throw new RuntimeException("Unable to create case");
        }

        //  Create ContactInfo from the Friss Payload and then (1) find/create contact, (2) Link with task
        List<ContactInfo> allContacts = getAllContacts(payload);
        for (int i = 0; i < allContacts.size(); i++) {
            ContactInfo contactInfo = allContacts.get(i);
            Long personId = PoloniusUtil.findOrCreatePerson(token, "Claimant", contactInfo);

            if (personId != null){
                contactInfo.getRoles().stream().forEach(c -> {
                    PoloniusUtil.createTaskPersonLink(token, createCaseResponse.getBody().getTaskId(), c, personId);
                });
            }
        }

        List<IncidentInfo> incidentList = getAllIncidents(payload);
        for (int i = 0; i < incidentList.size(); i++) {
            IncidentInfo incidentInfo = incidentList.get(i);
            Long assetId = PoloniusUtil.findOrCreateAsset(token, incidentInfo);

            if (assetId != null && assetId > 0){
                PoloniusUtil.createTaskAssetLink(token, createCaseResponse.getBody().getTaskId(), "Car Involved", assetId);
            }else{
                System.out.printf("Asset not created for %s", incidentInfo.getDescription());
            }
        }

        return createCaseResponse;
    }

    /**
     * Get the list of all incident info
     * @param payload
     * @return
     */
    private List<IncidentInfo> getAllIncidents(FrsPayloadDTO payload) {
        List<VehicleObject> vehicleObjects = FrissUtil.getVehicleObjects(payload);
        return vehicleObjects.stream().map(c -> createIncidentInfo(c)).collect(Collectors.toList());
    }

    private List<ContactInfo> getAllContacts(FrsPayloadDTO payload){
        List<ContactInfo> contactInfoList = new ArrayList<>();
        List<PersonObject> personObjects = FrissUtil.getPersonObjects(payload);
        System.out.printf("Found %s contacts\n", personObjects.size());

        for (int i = 0; i < personObjects.size(); i++) {
            PersonObject personObject = personObjects.get(i);
            PhoneObject phoneObject = FrissUtil.findObjectsWithType(personObject.parent, ObjectType.Contact, null).stream()
                    .map(c -> (PhoneObject)c).findFirst().orElse(null);
            AddressObject addressObject = FrissUtil.findObjectsWithType(personObject.parent, ObjectType.Address, null).stream()
                    .map(c -> (AddressObject)c).findFirst().orElse(null);

            System.out.printf("%s. Contact(%s %s) Phone (%s)\tAddress (%s)\n", i, personObject.getFirstName(), personObject.getLastName(),
                    phoneObject!=null? phoneObject.getValue(): null,
                    addressObject != null? addressObject.getStreet(): null);
            contactInfoList.add(createContactInfo(personObject, phoneObject, addressObject));
        }
        return contactInfoList;
    }

    private ContactInfo createContactInfo(PersonObject personObject, PhoneObject phoneObject, AddressObject addressObject) {
        ContactInfo contactInfo = new ContactInfo();
        /**
         * Find all the Id objects within the personObjectNode, and add it to contact info map
         */
        addAllIdInfoBelow(contactInfo, personObject);

        if(personObject.parent != null){
            contactInfo.setContactPID(String.valueOf(personObject.parent.getEntityId()));
        }else {
            throw new RuntimeException("Cannot create person without ContactPID i.e. node -> entityId");
        }
        contactInfo.setFirstName(personObject.getFirstName());
        contactInfo.setMiddleName(personObject.getMiddleName());
        contactInfo.setLastName(personObject.getLastName());
        contactInfo.setDateOfBirth(personObject.getBirthDate());
        if (phoneObject != null){
            System.out.printf("Phone: %s %s\n", phoneObject.getValue(), phoneObject.getTypeName());
            if (phoneObject.getTypeName().equals("Private") || phoneObject.getTypeName().equals("Phone")){
                contactInfo.setPhoneNumber(phoneObject.getValue());
            }else if (phoneObject.getTypeName().equals("Mobile")){
                contactInfo.setMobileNumber(phoneObject.getValue());
            }else{
                throw new RuntimeException(String.format("Error: Unknown phone number type %s", phoneObject.getTypeName()));
            }
        }else{
            System.out.printf("Phone object not provided\n");
        }

        contactInfo.setPrimaryAddress(createAddress(addressObject));

        //  Add Role
        contactInfo.getRoles().add(transformPersonRole(personObject.parent.getLink().getLinkType().getValue()));

        return contactInfo;
    }
    private String transformPersonRole(String oldRole){
        switch (oldRole.toLowerCase()){
            case "driver": return "Driver";
            case "policyholder": return "Policy Holder";
            case "thirdparty": return "Third Party";
            default: return PoloniusUtil.toPascalCase(oldRole);
        }
    }

    /**
     * Find all Ids (ObjectType 4) within the person Node, and add as name value pair in the map
     * @param contactInfo
     * @param personObject
     */
    private void addAllIdInfoBelow(ContactInfo contactInfo, PersonObject personObject) {
        List<LicenseIdObject> licenseIdObjects = FrissUtil.findObjectsWithType(personObject.parent, ObjectType.IdentityCard, null)
                .stream().map(c -> (LicenseIdObject)c).collect(Collectors.toList());
        licenseIdObjects.stream().forEach(c -> contactInfo.getMap().put(transformIdName(c.getTypeName()), c.getNumber()));
    }
    private String transformIdName(String oldName){
        switch (oldName.toLowerCase()){
            case "driver license": return "DL";
            default: return PoloniusUtil.toPascalCase(oldName);
        }
    }

    private IncidentInfo createIncidentInfo(VehicleObject vehicleObject) {
        //  Find id card info under the vehicle subtree, if any.
        LicenseIdObject identityCard = FrissUtil.findObjectsWithType(vehicleObject.parent, ObjectType.IdentityCard, null)
                .stream().map(c -> (LicenseIdObject)c).findFirst().orElse(null);

        IncidentInfo incidentInfo = new IncidentInfo();
        incidentInfo.setIncidentType("Car");    //  Hardcoded for now
        incidentInfo.setIncidentPID(String.valueOf(vehicleObject.parent.getEntityId()));
        incidentInfo.setMake(vehicleObject.getMake());
        incidentInfo.setModel(vehicleObject.getModel());
        incidentInfo.setVehicleVIN(vehicleObject.getVINNumber());
        incidentInfo.setYear(vehicleObject.getYear().toString());

        if (identityCard != null) {
            //  Using the same PID for both Owner and Driver
            incidentInfo.setDriverPID(identityCard.getNumber());
            incidentInfo.setOwnerPID(identityCard.getNumber());
        }
        return incidentInfo;
    }

    private PrimaryAddress createAddress(AddressObject addressObject){
        PrimaryAddress primaryAddress = new PrimaryAddress();
        primaryAddress.setAddressLine1(String.format("%s %s %s", addressObject.getHouseNumberAddition(),
                addressObject.getHouseNumber(), addressObject.getStreet()));
        primaryAddress.setCity(addressObject.getCity());
        primaryAddress.setZipCode(addressObject.getPostalCode());
        primaryAddress.setCountry(addressObject.getCountry().getValue());
        return primaryAddress;
    }

    private FrissCreateCaseDto createFrissDto(FrsPayloadDTO payload) {
        FrissCreateCaseDto dto = new FrissCreateCaseDto();

        /**
         * TODO: Hard coded for brevity. Not to be picked from the payload.
         * To be shifted to properties file later.
         */
        dto.setDescription("");
        dto.setLineOfBusiness("Automobile");
        dto.setLossState("California");
        dto.setInsuredOffice("California");
        dto.setClaimOffice("San Antonio");

//        dto.setDescription(payload.getDescription());
//        dto.setLineOfBusiness(payload.getLineOfBusiness());
//        dto.setLossState(payload.getLossState());
//        dto.setInsuredOffice(payload.getInsuredOffice());
//        dto.setClaimOffice(payload.getClaimOffice());

        dto.setBranch(payload.getScreeningDetails().getBranch());
        dto.setProduct(payload.getScreeningDetails().getProduct());
        dto.setDetectionDate(payload.getScreeningDetails().getDetectionDate());
        dto.setScore(payload.getScreeningDetails().getScore());
        dto.setArgument(payload.getScreeningDetails().getArgument());

        //Populate using first OBJECT_TYPE=9 (claim) object in the payload tree
        ClaimObject frissClaimObject = FrissUtil.getClaimObjects(payload).stream().findFirst().orElse(null);
        if (frissClaimObject != null){
            dto.setAccidentType(frissClaimObject.getAccidentType());
            dto.setAmount(frissClaimObject.getAmount());
            dto.setCause(frissClaimObject.getCause());
            dto.setClaimId(frissClaimObject.getClaimId());
            dto.setClaimStatement(frissClaimObject.getClaimStatement());
            dto.setDamageType(frissClaimObject.getDamageType());
            dto.setDateOccurred(frissClaimObject.getDateOccurred());
            dto.setDateReported(frissClaimObject.getDateReported());
            dto.setPolicyId(frissClaimObject.getPolicyId());
        }

        AddressObject addressObject = FrissUtil.getAddressObjects(payload).stream().findFirst().orElse(null);
        if (addressObject != null){
            dto.setClaimAddressLine1(addressObject.getStreet());
            dto.setClaimCity(addressObject.getCity());
            dto.setClaimCountry(addressObject.getCountry().Value);
            dto.setClaimPostalCode(addressObject.getPostalCode());
        }

        PolicyObject policyObject = FrissUtil.getPolicyObjects(payload).stream().findFirst().orElse(null);
        if (policyObject != null){
            policyObject.getEndDate();
            policyObject.getInceptionDate();
            policyObject.getInsuredAmount();
            dto.setPolicyId(policyObject.getPolicyId());
            dto.setProduct(policyObject.getProduct());
        }

        /**
         * Vehicle object to be saved separately as 'asset' and to be linked to the task.
         */
//        VehicleObject vehicleObject = FrissUtil.getVehicleObjects(payload).stream().findFirst().orElse(null);
//        if (vehicleObject != null){
//            dto.setLicensePlate(vehicleObject.getLicense());
//            dto.setMake(vehicleObject.getMake());
//            dto.setModel(vehicleObject.getModel());
//            dto.setVin(vehicleObject.getVINNumber());
//            dto.setYear(vehicleObject.getYear());
//        }
        return dto;
//        return this.createDummyFrissCreateCaseDto();
    }

    /**
     * Temporary function, used for testing purposes only.
     * @return
     */
    @GetMapping("dto")
    public FrissCreateCaseDto createDummyFrissCreateCaseDto(){
        FrissCreateCaseDto dto = new FrissCreateCaseDto();
        dto.setDescription("");
        dto.setLineOfBusiness("Automobile");
        dto.setLossState("California");
        dto.setInsuredOffice("California");
        dto.setClaimOffice("Other");
        dto.setBranch("NonLife");
        dto.setProduct("Commercial");
        dto.setDetectionDate("2017-06-28T14:34:51.327Z");
        dto.setScore("140");
        dto.setArgument("RED");
        dto.setClaimId("yw4964593264wr");
        dto.setDateOccurred("2013-10-02T00:00:00");
        dto.setAmount(2035l);
        dto.setCause("Collision");
        dto.setAccidentType("Reversing");
        dto.setDamageType("Third Party");
        dto.setClaimStatement("test");
        dto.setPolicyId("qkcj29887");
        dto.setDateReported("2013-10-02T00:00:00");
        dto.setClaimAddressLine1("123 Main Street");
        dto.setClaimPostalCode("33312");
        dto.setClaimCity("Fort Lauderdale");
        dto.setClaimState("Florida");
        dto.setClaimCountry("USA");
//        dto.setLicensePlate("vq224qq");
//        dto.setVin("170738");
//        dto.setYear(2004);
//        dto.setMake("Honda");
//        dto.setModel("FR-V");

        return dto;
    }
}
