package au.com.polonious.integration.service.impl;

import au.com.polonious.integration.dtos.ecosDto.PoloniusCreateCaseDto;
import au.com.polonious.integration.dtos.ecosDto.ReferralRequest;
import au.com.polonious.integration.dtos.frissDto.FrissResponseCreateCase;
import au.com.polonious.integration.dtos.referralDto.CreateCaseResponseXml;
import au.com.polonious.integration.dtos.referralDto.ReferralInquiry;
import au.com.polonious.integration.dtos.referralDto.ReferralInquiryRequest;
import au.com.polonious.integration.service.ReferralService;
import au.com.polonious.integration.utils.EcosXmlClient;
import au.com.polonious.integration.utils.PoloniusFeignClient;
import au.com.polonious.integration.utils.PoloniusUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class ReferralServiceImpl implements ReferralService{
    @Autowired
    CsvHelper csvHelper;
    @Autowired
    EcosXmlClient ecosXmlClient;
    @Autowired
    Mapper mapper;
    @Autowired
    PoloniusFeignClient poloniusFeignClient;

    @Override
    public Object prepareReferrals(MultipartFile file) {
        log.info(String.format("Has csv format? %s", CsvHelper.hasCSVFormat(file)));
        try{
            /*
             *  Read Csv to a list of Referral Records
             */
            List<ReferralRecord> rawTables = csvHelper.csvToTable(file.getInputStream());
            rawTables = rawTables.stream().filter(c -> c.getScore() >= 70).collect(Collectors.toList());

            /**
             * From the Referral Records, create the Pojo pertaining to Ecos Xml
             */
            ReferralInquiryRequest referralInquiryRequest = new ReferralInquiryRequest();
            ReferralInquiryRequest.Credential credential = new ReferralInquiryRequest.Credential();
            credential.setUsername("CFMIntegUser");

            referralInquiryRequest.setCredential(credential);

            referralInquiryRequest.setArrayList(rawTables.stream().map(c -> mapToReferralRequest(c)).collect(Collectors.toList()));

            referralInquiryRequest.setRequest_id("3280");

            return ecosXmlClient.referralXmlTest2(referralInquiryRequest);
//            return rawTables;

        }catch (IOException e){
            log.info(String.format("Error Reading file: %s\n%s", file.getName(), e.getMessage()));
        }

        return null;
    }

    @Override
    public CreateCaseResponseXml caseReferralSave(ReferralRequest payload) {
        //  Get Token
//        String token = PoloniusUtil.getToken();
//
//        PoloniusCreateCaseDto poloniusCreateCaseDto = mapper.createEcosDto(payload);
//        FrissResponseCreateCase frissResponseCreateCase = poloniusFeignClient.createEcosCase(poloniusCreateCaseDto);

        CreateCaseResponseXml createCaseResponseXml = CreateCaseResponseXml.builder().body(
                CreateCaseResponseXml.Body.builder().referralMessage(
                        CreateCaseResponseXml.Body.ReferralMessage.builder().status("success").build()
                ).build()
        ).build();
        return createCaseResponseXml;
    }

    private ReferralInquiry mapToReferralRequest(ReferralRecord referralRecord) {
        return ReferralInquiry.builder().exposure_number(referralRecord.getExposireId())
                .special_program(referralRecord.getDetectionMethod())
                .build();
    }
}
