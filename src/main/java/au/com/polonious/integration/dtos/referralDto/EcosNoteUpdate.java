package au.com.polonious.integration.dtos.referralDto;

import au.com.polonious.integration.dtos.referralDto.ecosUpdate.ClaimInfo;
import au.com.polonious.integration.dtos.referralDto.ecosUpdate.Credential;
import au.com.polonious.integration.dtos.referralDto.ecosUpdate.ReferralDecision;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({ "credential", "claim_info", "referralSource", "referralDecision", "referral_id", "investigation_note", "referralStatus"})
@JacksonXmlRootElement(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "ReferralRequest")
public class EcosNoteUpdate {
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    private Credential credential;

    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "claim_info")
    private ClaimInfo claimInfo;

    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "referral_src")
    String referralSource;

    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "referral_decision")
    ReferralDecision referralDecision;

    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    InvestigationNote investigation_note;

    @Data
    public static class InvestigationNote{
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String note_type;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String note_text;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String note_date;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String operator;
    }
}
