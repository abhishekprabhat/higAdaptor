package au.com.polonious.integration.controller;

import au.com.polonious.integration.dtos.ecosDto.ReferralRequest;
import au.com.polonious.integration.dtos.referralDto.*;
import au.com.polonious.integration.service.ReferralService;
import au.com.polonious.integration.service.impl.ReferralRecord;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Log
public class ReferralController {
    @Autowired
    ReferralService referralService;

    @PostMapping(value = "/loadReferrals", produces = { MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(referralService.prepareReferrals(file));
    }

    @PostMapping(value = "test/outbound/referral",
            consumes = { MediaType.APPLICATION_XML_VALUE})
    public List<ReferralRecord> referralXmlTest(@RequestBody List<ReferralRecord> payload){
        return payload;
    }

    @PostMapping(value = "test/outbound/referral2",
            consumes = { MediaType.APPLICATION_XML_VALUE}, produces = { MediaType.APPLICATION_XML_VALUE})
    public ReferralInquiryRequest referralXmlTest2(@RequestBody ReferralInquiryRequest payload){
//        public ReferralInquiryResponse referralXmlTest2(@RequestBody ReferralInquiryRequest payload){
//        return ReferralInquiryResponse.builder().request_id("3280")
//                .status("success")
//                .build();
        return payload;
    }

    @PostMapping(value = "caseReferral",
            consumes = { MediaType.APPLICATION_XML_VALUE}, produces = { MediaType.APPLICATION_XML_VALUE})
    public CreateCaseResponseXml caseReferralXml(@RequestBody ReferralRequest payload){
        return referralService.caseReferralSave(payload);
    }

    @PostMapping(value = "caseStatusUpdate/xml",
            consumes = { MediaType.APPLICATION_XML_VALUE}, produces = { MediaType.APPLICATION_XML_VALUE})
    public EcosStatusUpdate referralXmlTest2(@RequestBody EcosStatusUpdate payload){
        return payload;
    }
    @PostMapping(value = "caseStatusUpdate",
            consumes = { MediaType.APPLICATION_JSON_VALUE}, produces = { MediaType.APPLICATION_XML_VALUE})
    public EcosStatusUpdate ecosStatusUpdate(@RequestBody EcosStatusUpdate payload){
        return payload;
    }
    @PostMapping(value = "caseNoteUpdate",
            consumes = { MediaType.APPLICATION_JSON_VALUE}, produces = { MediaType.APPLICATION_XML_VALUE})
    public EcosNoteUpdate ecosNoteUpdate(@RequestBody EcosNoteUpdate payload){
        return payload;
    }
}
