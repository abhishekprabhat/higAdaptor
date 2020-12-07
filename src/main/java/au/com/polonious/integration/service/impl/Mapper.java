package au.com.polonious.integration.service.impl;

import au.com.polonious.integration.dtos.ecosDto.EcosAddress;
import au.com.polonious.integration.dtos.ecosDto.PoloniusCreateCaseDto;
import au.com.polonious.integration.dtos.ecosDto.EcosPerson;
import au.com.polonious.integration.dtos.ecosDto.ReferralRequest;
import au.com.polonious.integration.dtos.frsDto.ContactInfo;
import au.com.polonious.integration.dtos.frsDto.PrimaryAddress;
import au.com.polonious.integration.dtos.referralDto.poloniusDto.AssetDto;
import au.com.polonious.integration.dtos.referralDto.poloniusDto.CreateCaseDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Mapper {
    public PoloniusCreateCaseDto createEcosDto(ReferralRequest referralRequest) {
//        ReferralRequest referralRequest = payload.getBody().getReferralRequest();
        PoloniusCreateCaseDto poloniusCreateCaseDto = new PoloniusCreateCaseDto();
        poloniusCreateCaseDto.setDescription("");
        poloniusCreateCaseDto.setLossState2("California");
        poloniusCreateCaseDto.setLob(referralRequest.getClaimInfo().getLob());
        poloniusCreateCaseDto.setBusinessTypeDesc(referralRequest.getClaimInfo().getBusinessTypeDesc());

        poloniusCreateCaseDto.setLossDate(referralRequest.getClaimInfo().getLossDate());
        poloniusCreateCaseDto.setLossLocAddrLine1(referralRequest.getClaimInfo().getLossLocation().getAddressline1());
        poloniusCreateCaseDto.setLossLocCity(referralRequest.getClaimInfo().getLossLocation().getCity());
        poloniusCreateCaseDto.setLossLocState(referralRequest.getClaimInfo().getLossLocation().getState());
        poloniusCreateCaseDto.setLossLocCountry(referralRequest.getClaimInfo().getLossLocation().getCountry());
        poloniusCreateCaseDto.setLossLocZip(referralRequest.getClaimInfo().getLossLocation().getZip());

        poloniusCreateCaseDto.setBusinessTypeCode(referralRequest.getClaimInfo().getBusinessTypeCode());
        poloniusCreateCaseDto.setClaim_sym(referralRequest.getClaimInfo().getClaim_sym());
        poloniusCreateCaseDto.setClaimDesc(referralRequest.getClaimInfo().getClaimDesc());
        poloniusCreateCaseDto.setClaimNumber(referralRequest.getClaimInfo().getClaimNumber());
        poloniusCreateCaseDto.setClaimOffice(referralRequest.getClaimInfo().getClaimOffice());
        poloniusCreateCaseDto.setClaimPlusIndicator(referralRequest.getClaimInfo().getClaimPlusIndicator());
        poloniusCreateCaseDto.setClaimStatusAtReferral(referralRequest.getClaimInfo().getClaimStatusAtReferral());
        poloniusCreateCaseDto.setEventNum(referralRequest.getClaimInfo().getEventNum());
        poloniusCreateCaseDto.setExposureId(referralRequest.getClaimInfo().getExposureId());
        if (referralRequest.getClaimInfo().getFinancialTotalReserves() != null)
            poloniusCreateCaseDto.setFinancialTotalReserves(referralRequest.getClaimInfo().getFinancialTotalReserves().getTotalReserves());
        poloniusCreateCaseDto.setHubOffice(referralRequest.getClaimInfo().getHubOffice());
        poloniusCreateCaseDto.setImportReason(referralRequest.getImportReason());
        poloniusCreateCaseDto.setLargeLoss(referralRequest.getClaimInfo().getLargeLoss());
        poloniusCreateCaseDto.setLossDesc(referralRequest.getClaimInfo().getLossDesc());

        poloniusCreateCaseDto.setPolicyState(referralRequest.getPolicyInfo().getPolicyState());
        poloniusCreateCaseDto.setPolicyEffectiveDate(referralRequest.getPolicyInfo().getPolicyEffectiveDate());
        poloniusCreateCaseDto.setPolicyExpiryDate(referralRequest.getPolicyInfo().getPolicyExpiryDate());
        poloniusCreateCaseDto.setPolicyNum(referralRequest.getPolicyInfo().getPolicyNum());
        poloniusCreateCaseDto.setPolicyOrigEffectiveDate(referralRequest.getPolicyInfo().getPolicyOrigEffectiveDate());
        poloniusCreateCaseDto.setPolicyState2("California");

        poloniusCreateCaseDto.setReferralId(referralRequest.getReferralId());
        poloniusCreateCaseDto.setReferralSource(referralRequest.getReferralSource());
        if(referralRequest.getReferralNote() != null)
            poloniusCreateCaseDto.setReferralNote(referralRequest.getReferralNote().replace("|", "\n"));
        return poloniusCreateCaseDto;
    }

    public ContactInfo createContactInfo(EcosPerson personObject) {
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
    public PrimaryAddress createAddress(EcosAddress addressObject){
        if (addressObject == null) return null;
        PrimaryAddress primaryAddress = new PrimaryAddress();

        if (addressObject.getAddressline1() != null) primaryAddress.setAddressLine1(addressObject.getAddressline1());
        if (addressObject.getCity() != null) primaryAddress.setCity(addressObject.getCity());
        if (addressObject.getZip() != null) primaryAddress.setZipCode(addressObject.getZip());
        if (addressObject.getCountry() != null) primaryAddress.setCountry(addressObject.getCountry());
        return primaryAddress;
    }

    public CreateCaseDto getPoloniusCreateCaseDto(ReferralRequest referralRequest){
        CreateCaseDto createCaseDto = CreateCaseDto.builder()
                .description("")
                .reportedDate(referralRequest.getClaimInfo().getReportedDate())
//                .claimStatus(referralRequest.getClaimInfo().getClaimStatusAtReferral())
                .claimDesc(referralRequest.getClaimInfo().getClaimDesc())
                .lossDate(referralRequest.getClaimInfo().getLossDate())
                .lossDesc(referralRequest.getClaimInfo().getLossDesc())
                .addressLine1(referralRequest.getClaimInfo().getLossLocation().getAddressline1())
                .city(referralRequest.getClaimInfo().getLossLocation().getCity())
                .state(referralRequest.getClaimInfo().getLossLocation().getState())
                .zip(referralRequest.getClaimInfo().getLossLocation().getZip())
                .country(referralRequest.getClaimInfo().getLossLocation().getCountry())
                .lob(referralRequest.getClaimInfo().getLob())
                .claimOffice(referralRequest.getClaimInfo().getClaimOffice())
                .hubOffice(referralRequest.getClaimInfo().getHubOffice())
                .eventNum(referralRequest.getClaimInfo().getEventNum())
                .policyNum(referralRequest.getPolicyInfo().getPolicyNum())
                .policyEffectiveDate(referralRequest.getPolicyInfo().getPolicyEffectiveDate())
                .policyExpiryDate(referralRequest.getPolicyInfo().getPolicyExpiryDate())
                .policyState(referralRequest.getPolicyInfo().getPolicyState())
                .importReason(referralRequest.getImportReason())
                .referralSource(referralRequest.getReferralSource())
                .referralId(referralRequest.getReferralId())
                .build();

        String[] referralNotes = referralRequest.getReferralNote().split("[|]");
        createCaseDto.setCategory(getField(referralNotes, "Catg:"));
        createCaseDto.setIndicatorPercent(getField(referralNotes, "Indicator%:"));
        createCaseDto.setIndicators(getField(referralNotes, "Indicators:"));
        createCaseDto.setInvToDateYes(getField(referralNotes, "Yes-"));
        createCaseDto.setInvToDateNo(getField(referralNotes, "No-"));
        createCaseDto.setReferralDate(getField(referralNotes, "Referral Date:"));
        return createCaseDto;
    }

    private String getField(String[] values, String starting){
        Pattern pattern = Pattern.compile(String.format("^%s(.+)", starting));
        return Arrays.asList(values).stream().map(c -> {
            Matcher matcher = pattern.matcher(c);
            if (matcher.find()){
                return matcher.group(1);
            }else return null;
        }).filter(c -> c != null).findFirst().orElse("");
    }

    public AssetDto extractAsset(ReferralRequest referralRequest) {
        String[] referralNotes = referralRequest.getReferralNote().split("[|]");

        AssetDto assetDto = AssetDto.builder()
                .assetTypeDescription("Exposure")
                .exposureId(referralRequest.getClaimInfo().getExposureId())
                .description(referralRequest.getClaimInfo().getExposureId())
                .claimNum(referralRequest.getClaimInfo().getClaimNumber())
                .claimSym(referralRequest.getClaimInfo().getClaim_sym())
                .businessTypeCode(referralRequest.getClaimInfo().getBusinessTypeCode())
                .businessTypeDesc(referralRequest.getClaimInfo().getBusinessTypeDesc())
                .largeLoss(referralRequest.getClaimInfo().getLargeLoss())
                .claimPlusIndicator(referralRequest.getClaimInfo().getClaimPlusIndicator())
                .totalReserves(referralRequest.getClaimInfo().getFinancialTotalReserves().getTotalReserves())
                .exposure(getField(referralNotes, "Exposure:"))
//                .catDesc(referralRequest.getClaimInfo())
//                .injuryCodes(referralRequest.getClaimInfo())
//                .bodyPart()
//                .totalIndemnityPayments(referralRequest.getClaimInfo().getFinancialTotalReserves().)
//                .totalExpensePayments()
//                .totalRecoveries()
                .build();
        return assetDto;
    }
}
