package au.com.polonious.integration.service.impl;

import au.com.polonious.integration.dtos.ecosDto.PoloniusCreateCaseDto;
import au.com.polonious.integration.dtos.ecosDto.ReferralRequest;
import au.com.polonious.integration.dtos.frissDto.EcosResponseCreateCase;
import au.com.polonious.integration.dtos.frissDto.FrissResponseCreateCase;
import au.com.polonious.integration.dtos.referralDto.CreateCaseResponseXml;
import au.com.polonious.integration.dtos.referralDto.ReferralInquiry;
import au.com.polonious.integration.dtos.referralDto.ReferralInquiryRequest;
import au.com.polonious.integration.dtos.referralDto.poloniusDto.AssetDto;
import au.com.polonious.integration.dtos.referralDto.poloniusDto.CreateCaseDto;
import au.com.polonious.integration.dtos.referralDto.poloniusDto.PoloniusTaskDto;
import au.com.polonious.integration.service.ReferralService;
import au.com.polonious.integration.utils.EcosXmlClient;
import au.com.polonious.integration.utils.PoloniusFeignClient;
import au.com.polonious.integration.utils.PoloniusUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
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
    @Autowired
    ObjectMapper objectMapper;

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
        String token = PoloniusUtil.getToken();

        CreateCaseDto poloniusCreateCaseDto = mapper.getPoloniusCreateCaseDto(payload);
        //  Create one asset from the payload
        AssetDto poloniusAssetDto = mapper.extractAsset(payload);
        boolean createAsset = true;
        try {
            Long taskId = -1L;  //  Task to use used for mapping with asset

            List<PoloniusTaskDto> tasks = poloniusFeignClient.getPoloniusTask(poloniusCreateCaseDto.getEventNum());
            if (tasks.size() > 1){
                throw new RuntimeException(String.format("Error: More than one (%s) cases found!", tasks.size()));
            }else if (tasks.size() == 1){
                //  Exactly one task found. Use it instead of creating new
                taskId = (tasks.get(0).getId());
                log.info(String.format("Found matching task id (%s) for eventNumber %s. Not creating new.", taskId, poloniusCreateCaseDto.getEventNum()));

                //  Check if the asset already exists; if yes, do not create
                if (tasks.get(0).getTaskAssetRoles().stream().filter(c -> c.getDescription().equals(poloniusAssetDto.getExposureId())).count() > 0){
                    createAsset = false;
                    log.info(String.format("Found asset (%s) in taskAssetRoles", poloniusAssetDto.getExposureId()));
                }else{
                    log.info(String.format("Asset (%s) not found in taskAssetRoles. Creating new ...", poloniusAssetDto.getExposureId()));
                }
            }else{
                log.info(String.format("No existing task found for event number (%s). Creating new ...", poloniusCreateCaseDto.getEventNum()));
                log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(poloniusCreateCaseDto));
                EcosResponseCreateCase frissResponseCreateCase = poloniusFeignClient.createCaseTask(poloniusCreateCaseDto);
                log.info(frissResponseCreateCase.toString());
            }

            if (createAsset){
//                log.info("Creating asset ...");
                log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(poloniusAssetDto));
                Long assetId = poloniusFeignClient.createAsset(poloniusAssetDto);
                log.info(String.format("Asset Created (%s)", assetId));

                //  If asset successfully created, then link the asset and task
                PoloniusUtil.createTaskAssetLink(token, taskId, "Exposure", assetId);

            }
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }

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
