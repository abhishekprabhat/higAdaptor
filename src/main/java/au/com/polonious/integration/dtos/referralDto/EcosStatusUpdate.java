package au.com.polonious.integration.dtos.referralDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({ "credential", "claim_info", "referralSource", "referral_id", "referralDecision", "referralStatus"})
@JacksonXmlRootElement(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "ReferralRequest")
public class EcosStatusUpdate {
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
//    @JsonProperty("CC")
    private Credential credential;

    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "claim_info")
//    @JsonProperty("claim_info")
    private ClaimInfo claimInfo;

    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "referral_src")
    String referralSource;
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    String referral_id;

    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "referral_decision")
    ReferralDecision referralDecision;
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "referral_status")
    ReferralStatus referralStatus;

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class ClaimInfo {
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String request_level;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String exposure_id;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String claim_num;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String claim_sym;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String claim_office;
    }
    @Data
    @Builder
    public static class ReferralDecision{
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String decision;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String decision_date;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String decision_operator;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String siu_case_number;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String investigator_assigned;
    }
    @Data
    @Builder
    public static class ReferralStatus{
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String new_status;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String old_status;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String change_date;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String operator;
    }
    @Data
    @Builder
    public static class Credential{
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String username;
        @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
        String password;
    }

}
