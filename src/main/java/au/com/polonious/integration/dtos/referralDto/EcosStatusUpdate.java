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
@JsonPropertyOrder({ "credential", "claim_Info", "referral_source", "referral_id", "referralDecision", "referralStatus"})
@JacksonXmlRootElement(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12", localName = "ReferralRequest")
public class EcosStatusUpdate {
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
//    @JsonProperty("Credential")
    private Credential credential;

    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    @JsonProperty("claim_info")
    private ClaimInfo claim_Info;

    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    String referral_source;
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    String referral_id;

    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    ReferralDecision referralDecision;
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    ReferralStatus referralStatus;

    @Data
    @Builder
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
