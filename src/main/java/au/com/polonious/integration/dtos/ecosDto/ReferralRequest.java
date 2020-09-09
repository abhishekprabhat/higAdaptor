package au.com.polonious.integration.dtos.ecosDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

@Data
public class ReferralRequest {
    @JsonProperty("claim_info")
    EcosClaimInfo claimInfo;
    @JsonProperty("policy_info")
    EcosPolicyInfo policyInfo;

    @JsonProperty("import_reason")
    String importReason;
    @JsonProperty("referral_src")
    String referralSource;
    @JsonProperty("referral_id")
    String referralId;
    @JsonProperty("referral_note")
    String referralNote;

    @JsonProperty("person_info")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<EcosPerson> personInfo;

}
