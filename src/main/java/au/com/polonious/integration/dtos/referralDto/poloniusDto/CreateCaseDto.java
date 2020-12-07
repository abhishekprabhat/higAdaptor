package au.com.polonious.integration.dtos.referralDto.poloniusDto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class CreateCaseDto {
    @JsonProperty("Description")
    String description = "";
    @JsonSetter(nulls=Nulls.SKIP)
    @JsonProperty("reported_date")
    String reportedDate;
    @JsonProperty("loss_date")
    String lossDate;
    @JsonProperty("loss_desc")
    String lossDesc;
    @JsonProperty("addressline1")
    String addressLine1;
    @JsonProperty("city")
    String city;
    @JsonProperty("state")
    String state;
    @JsonProperty("zip")
    String zip = "";
    @JsonProperty("country")
    String country;
    @JsonProperty("lob")
    String lob;
    @JsonProperty("claim_office")
    String claimOffice;
    @JsonProperty("hub_office")
    String hubOffice;
    @JsonProperty("event_num")
    String eventNum;
    @JsonProperty("claim_desc")
    String claimDesc;
//    @JsonProperty("claim_status")
//    String claimStatus;
    @JsonProperty("policy_num")
    String policyNum;
    @JsonProperty("policy_eff_date")
    String policyEffectiveDate;
    @JsonProperty("policy_exp_date")
    String policyExpiryDate;
    @JsonProperty("policy_state")
    String policyState;
    @JsonProperty("import_reason")
    String importReason;
    @JsonProperty("referral_src")
    String referralSource;
    @JsonProperty("referral_id")
    String referralId;
    @JsonProperty("Catg")
    String category;
    @JsonProperty("Indicator%")
    String indicatorPercent;
    @JsonProperty("Indicators")
    String indicators;
    @JsonProperty("InvToDateYes")
    String invToDateYes;
    @JsonProperty("InvToDateNo")
    String invToDateNo;
    @JsonProperty("Referral Date")
    String referralDate;

    public String getDescription() {
        return Optional.ofNullable(description).orElse("");
    }

    public String getReportedDate() {
        return Optional.ofNullable(reportedDate).orElse("");
    }

    public String getLossDate() {
        return Optional.ofNullable(lossDate).orElse("");
    }

    public String getLossDesc() {
        return Optional.ofNullable(lossDesc).orElse("");
//        return lossDesc;
    }

    public String getAddressLine1() {
        return Optional.ofNullable(addressLine1).orElse("");
//        return addressLine1;
    }

    public String getCity() {
        return Optional.ofNullable(city).orElse("");
//        return city;
    }

    public String getState() {
        return Optional.ofNullable(state).orElse("");
//        return state;
    }

    public String getZip() {
        return Optional.ofNullable(zip).orElse("");
//        return zip;
    }

    public String getCountry() {
        return Optional.ofNullable(country).orElse("");
//        return country;
    }

    public String getLob() {
        return Optional.ofNullable(lob).orElse("");
//        return lob;
    }

    public String getClaimOffice() {
        return Optional.ofNullable(claimOffice).orElse("");
//        return claimOffice;
    }

    public String getHubOffice() {
        return Optional.ofNullable(hubOffice).orElse("");
//        return hubOffice;
    }

    public String getEventNum() {
        return Optional.ofNullable(eventNum).orElse("");
//        return eventNum;
    }

    public String getClaimDesc() {
        return Optional.ofNullable(claimDesc).orElse("");
//        return claimDesc;
    }

    public String getPolicyNum() {
        return Optional.ofNullable(policyNum).orElse("");
//        return policyNum;
    }

    public String getPolicyEffectiveDate() {
        return Optional.ofNullable(policyEffectiveDate).orElse("");
//        return policyEffectiveDate;
    }

    public String getPolicyExpiryDate() {
        return Optional.ofNullable(policyExpiryDate).orElse("");
//        return policyExpiryDate;
    }

    public String getPolicyState() {
        return Optional.ofNullable(policyState).orElse("");
//        return policyState;
    }

    public String getImportReason() {
        return Optional.ofNullable(importReason).orElse("");
//        return importReason;
    }

    public String getReferralSource() {
        return Optional.ofNullable(referralSource).orElse("");
//        return referralSource;
    }

    public String getReferralId() {
        return Optional.ofNullable(referralId).orElse("");
//        return referralId;
    }

    public String getCategory() {
        return Optional.ofNullable(category).orElse("");
//        return category;
    }

    public String getIndicatorPercent() {
        return Optional.ofNullable(indicatorPercent).orElse("");
//        return indicatorPercent;
    }

    public String getIndicators() {
        return Optional.ofNullable(indicators).orElse("");
//        return indicators;
    }

    public String getInvToDateYes() {
        return Optional.ofNullable(invToDateYes).orElse("");
//        return invToDateYes;
    }

    public String getInvToDateNo() {
        return Optional.ofNullable(invToDateNo).orElse("");
//        return invToDateNo;
    }

    public String getReferralDate() {
//        return referralDate;
        return Optional.ofNullable(referralDate).orElse("");
    }
}
