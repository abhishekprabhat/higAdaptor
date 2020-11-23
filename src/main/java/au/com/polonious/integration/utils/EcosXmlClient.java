package au.com.polonious.integration.utils;

import au.com.polonious.integration.dtos.referralDto.ReferralInquiryRequest;
import au.com.polonious.integration.dtos.referralDto.ReferralInquiryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ecos", url = "localhost:8080", configuration = FeignConfiguration.class)
public interface EcosXmlClient {
    @PostMapping(value = "test/outbound/referral2",
            consumes = { MediaType.APPLICATION_XML_VALUE}, produces = { MediaType.APPLICATION_XML_VALUE})
    ReferralInquiryResponse referralXmlTest2(@RequestBody ReferralInquiryRequest payload);
}
