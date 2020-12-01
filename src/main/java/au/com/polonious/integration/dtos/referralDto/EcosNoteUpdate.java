package au.com.polonious.integration.dtos.referralDto;

import au.com.polonious.integration.dtos.referralDto.ecosUpdate.Credential;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({ "credential", "claim_info", "referralSource", "referral_id", "referralDecision", "referralStatus"})
@JacksonXmlRootElement(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "ReferralRequest")
public class EcosNoteUpdate {
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    private Credential credential;

}
