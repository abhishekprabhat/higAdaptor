package au.com.polonious.integration.dtos.ecosDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EcosClaimInfo {
    @JsonProperty("exposure_id")
    String exposureId;
    @JsonProperty("claim_num")
    String claimNumber;
    @JsonProperty("reported_date")
    String reportedDate;
    @JsonProperty("claim_status")
    String claimStatusAtReferral;
    @JsonProperty("claim_sym")
    String claim_sym;
    @JsonProperty("loss_date")
    String lossDate;
    @JsonProperty("loss_desc")
    String lossDesc;
    @JsonProperty("loss_loc")
    EcosAddress lossLocation;
    @JsonProperty("business_type_code")
    String businessTypeCode;
    @JsonProperty("business_type_desc")
    String businessTypeDesc;
    @JsonProperty("lob")
    String lob;
    @JsonProperty("claim_office")
    String claimOffice;
    @JsonProperty("hub_office")
    String hubOffice;
    @JsonProperty("event_num")
    String eventNum;
    @JsonProperty("vehicle")
    Object vehicle;
    @JsonProperty("claim_desc")
    String claimDesc;
    @JsonProperty("large_loss")
    String largeLoss;
    @JsonProperty("claimPlusIndicator")
    String claimPlusIndicator;
    @JsonProperty("financials")
    Financial financialTotalReserves;

    @Data
    public static class Financial{
        @JsonProperty("total_reserves")
        String totalReserves;
    }
}
