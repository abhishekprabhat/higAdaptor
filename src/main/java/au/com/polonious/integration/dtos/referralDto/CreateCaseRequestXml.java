package au.com.polonious.integration.dtos.referralDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({ "credential", "arrayList", "request_id"})
@JacksonXmlRootElement(namespace = "http://www.thehartford.com/Claims/SIU/ReferralInquiryRq", localName = "ReferralInquiryRq")
public class CreateCaseRequestXml {
}
