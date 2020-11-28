package au.com.polonious.integration.service;

import au.com.polonious.integration.dtos.ecosDto.ReferralRequest;
import au.com.polonious.integration.dtos.referralDto.CreateCaseResponseXml;
import au.com.polonious.integration.service.impl.ReferralRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReferralService {
    Object prepareReferrals(MultipartFile file);

    CreateCaseResponseXml caseReferralSave(ReferralRequest payload);
}
