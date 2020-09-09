package au.com.polonious.integration.dtos.ecosDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EcosPerson {
    @JsonProperty("person_id")
    String personId;

    @JsonProperty("person_type")
    String personType;

    @JsonProperty("person_uid")
    String personUid;

    EcosName name;
    EcosAddress address;

    @JsonProperty("birth_date")
    String personDob;

    @JsonProperty("agency_code")
    String agencyCode;
    @JsonProperty("agency_master_code")
    String agencyMasterCode;
    @JsonProperty("phone")
    String phone;
    @JsonProperty("JTC_affiliation")
    String JTC_affiliation;
    @JsonProperty("VIP_agent_indicator")
    String VIP_agent_indicator;
}
