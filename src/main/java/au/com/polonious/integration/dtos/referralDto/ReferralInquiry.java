package au.com.polonious.integration.dtos.referralDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JacksonXmlRootElement(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRq")
public class ReferralInquiry {
            @JacksonXmlProperty(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRq")
    String exposure_number;
            @JacksonXmlProperty(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRq")
    String special_program;
}
