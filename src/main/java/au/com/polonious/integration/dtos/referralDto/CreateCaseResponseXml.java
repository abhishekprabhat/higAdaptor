package au.com.polonious.integration.dtos.referralDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JacksonXmlRootElement(namespace = "http://schemas.xmlsoap.org/soap/envelope/", localName = "Envelope")
@Builder
public class CreateCaseResponseXml {
//    @JsonProperty("Envelope")
//    Envelope envelope;
//
//    @Data
//    @Builder
//    public static class Envelope{
        @JacksonXmlProperty(namespace = "http://schemas.xmlsoap.org/soap/envelope/")
        @JsonProperty("Body")
        Body body;

        @Data
        @Builder
        public static class Body{
            @JacksonXmlProperty(namespace = "http://cfm.thehartford.com/ws/types", localName = "ReferralMessage")
            ReferralMessage referralMessage;
            @Data
            @Builder
            public static class ReferralMessage{
                @JacksonXmlProperty(namespace = "http://cfm.thehartford.com/ws/types")
                String status;
            }
        }
//    }
}
