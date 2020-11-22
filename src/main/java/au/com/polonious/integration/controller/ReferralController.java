package au.com.polonious.integration.controller;

import au.com.polonious.integration.dtos.referralDto.ReferralInquiryRequest;
import au.com.polonious.integration.service.ReferralService;
import au.com.polonious.integration.service.impl.ReferralRecord;
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
public class ReferralController {
    @Autowired
    ReferralService referralService;

    @PostMapping(value = "/loadReferrals", produces = { MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<ReferralRecord>> uploadFile(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(referralService.prepareReferrals(file));
    }

    @PostMapping(value = "test/outbound/referral",
            consumes = { MediaType.APPLICATION_XML_VALUE})
    public List<ReferralRecord> referralXmlTest(@RequestBody List<ReferralRecord> payload){
        return payload;
    }

    @PostMapping(value = "test/outbound/referral2",
            consumes = { MediaType.APPLICATION_XML_VALUE})
    public List<ReferralInquiryRequest.ReferralInquiry> referralXmlTest2(@RequestBody List<ReferralInquiryRequest.ReferralInquiry> payload){
        return payload;
    }
}
