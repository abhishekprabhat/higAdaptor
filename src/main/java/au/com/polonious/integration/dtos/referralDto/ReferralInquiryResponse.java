package au.com.polonious.integration.dtos.referralDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown=true)
@JacksonXmlRootElement(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRs", localName = "ReferralInquiryRs")
public class ReferralInquiryResponse {
    @JacksonXmlProperty(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRs")
    private String request_id;

    @JacksonXmlProperty(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRs")
    private String status;
}
