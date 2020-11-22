package au.com.polonious.integration.controller;

import au.com.polonious.integration.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ReferralController {
    @Autowired
    ReferralService referralService;

    @PostMapping("/loadReferrals")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(referralService.prepareReferrals(file));
    }

}
