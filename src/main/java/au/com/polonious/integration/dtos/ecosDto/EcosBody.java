package au.com.polonious.integration.dtos.ecosDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EcosBody {
    @JsonProperty("ReferralRequest")
    ReferralRequest referralRequest;
}
