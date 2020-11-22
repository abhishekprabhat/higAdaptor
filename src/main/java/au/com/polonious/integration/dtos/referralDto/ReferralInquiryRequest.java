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
@JacksonXmlRootElement(namespace = "urn:stackify:jacksonxml", localName = "ReferralInquiryRq")
public class ReferralInquiryRequest {
    @JacksonXmlProperty
    @JsonProperty("Credential")
    private Credential credential;
    @JsonProperty("ReferralInquiry")

//    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ReferralInquiry> arrayList;
    @JacksonXmlProperty
    private String request_id;

    @Data
    public static class Credential{
        String username;
    }
    @Data
    @JacksonXmlRootElement(localName = "ReferralInquiry")
    public static class ReferralInquiry{
        String exposure_number;
        String special_program;
    }
}
