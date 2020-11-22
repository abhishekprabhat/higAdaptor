package au.com.polonious.integration.service;

import org.springframework.web.multipart.MultipartFile;

public interface ReferralService {
    Object prepareReferrals(MultipartFile file);
}
