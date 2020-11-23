package au.com.polonious.integration.dtos.referralDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JacksonXmlRootElement(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRq", localName = "ReferralInquiryRq")
public class ReferralInquiryRequest {
    @JacksonXmlProperty(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRq")
    @JsonProperty("Credential")
    private Credential credential;

//    @JsonProperty("ReferralInquiry")
    @JacksonXmlProperty(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRq", localName = "ReferralInquiry")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ReferralInquiry> arrayList;

    @JacksonXmlProperty(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRq")
    private String request_id;

    @Data
    public static class Credential{
        @JacksonXmlProperty(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRq")
        String username;
    }
}
