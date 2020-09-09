package au.com.polonious.integration.dtos.ecosDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EcosPolicyInfo {
    @JsonProperty("policy_num")
    String policyNum;
    @JsonProperty("policy_orig_eff_date")
    String policyOrigEffectiveDate;
    @JsonProperty("policy_eff_date")
    String policyEffectiveDate;
    @JsonProperty("policy_exp_date")
    String policyExpiryDate;
    @JsonProperty("policy_state")
    String policyState;

}
