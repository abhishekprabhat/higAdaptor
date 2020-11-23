package au.com.polonious.integration.service;

import au.com.polonious.integration.service.impl.ReferralRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReferralService {
    Object prepareReferrals(MultipartFile file);
}
