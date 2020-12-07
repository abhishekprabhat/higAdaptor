package au.com.polonious.integration.dtos.referralDto.poloniusDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class AssetDto {
    @JsonProperty("assetTypeDescription")
    String assetTypeDescription;
    @JsonProperty("description")
    String description;
    @JsonProperty("exposure_id")
    String exposureId;
    @JsonProperty("claim_num")
    String claimNum;
    @JsonProperty("claim_sym")
    String claimSym;
    @JsonProperty("business_type_code")
    String businessTypeCode;
    @JsonProperty("business_type_desc")
    String businessTypeDesc;
    @JsonProperty("cat_desc")
    String catDesc;
    @JsonProperty("large_loss")
    String largeLoss;
    @JsonProperty("claimPlusIndicator")
    String claimPlusIndicator;
    @JsonProperty("total_reserves")
    String totalReserves;
    @JsonProperty("total_indemnity_payments")
    String totalIndemnityPayments;
    @JsonProperty("total_expense_payments")
    String totalExpensePayments;
    @JsonProperty("total_recoveries")
    String totalRecoveries;
    @JsonProperty("Exposure")
    String exposure;
    @JsonProperty("injury_codes")
    String injuryCodes;
    @JsonProperty("body_part")
    String bodyPart;
    @JsonProperty("uid")
    String uid;
    @JsonProperty("vin")
    String vin;
    @JsonProperty("make")
    String make;
    @JsonProperty("model")
    String model;
    @JsonProperty("year")
    String year;

    public String getAssetTypeDescription() {
        return Optional.ofNullable(assetTypeDescription).orElse("");
//        return assetTypeDescription;
    }

    public String getDescription() {
        return Optional.ofNullable(description).orElse("");
//        return description;
    }

    public String getExposureId() {
        return Optional.ofNullable(exposureId).orElse("");
//        return exposureId;
    }

    public String getClaimNum() {
        return Optional.ofNullable(claimNum).orElse("");
//        return claimNum;
    }

    public String getClaimSym() {
        return Optional.ofNullable(claimSym).orElse("");
//        return claimSym;
    }

    public String getBusinessTypeCode() {
        return Optional.ofNullable(businessTypeCode).orElse("");
//        return businessTypeCode;
    }

    public String getBusinessTypeDesc() {
        return Optional.ofNullable(businessTypeDesc).orElse("");
//        return businessTypeDesc;
    }

    public String getCatDesc() {
        return Optional.ofNullable(catDesc).orElse("");
//        return catDesc;
    }

    public String getLargeLoss() {
        return Optional.ofNullable(largeLoss).orElse("");
//        return largeLoss;
    }

    public String getClaimPlusIndicator() {
        return Optional.ofNullable(claimPlusIndicator).orElse("");
//        return claimPlusIndicator;
    }

    public String getTotalReserves() {
        return Optional.ofNullable(totalReserves).orElse("");
//        return totalReserves;
    }

    public String getTotalIndemnityPayments() {
        return Optional.ofNullable(totalIndemnityPayments).orElse("");
//        return totalIndemnityPayments;
    }

    public String getTotalExpensePayments() {
        return Optional.ofNullable(totalExpensePayments).orElse("");
//        return totalExpensePayments;
    }

    public String getTotalRecoveries() {
        return Optional.ofNullable(totalRecoveries).orElse("");
//        return totalRecoveries;
    }

    public String getExposure() {
        return Optional.ofNullable(exposure).orElse("");
//        return exposure;
    }

    public String getInjuryCodes() {
        return Optional.ofNullable(injuryCodes).orElse("");
//        return injuryCodes;
    }

    public String getBodyPart() {
        return Optional.ofNullable(bodyPart).orElse("");
//        return bodyPart;
    }

    public String getUid() {
        return Optional.ofNullable(uid).orElse("");
//        return uid;
    }

    public String getVin() {
        return Optional.ofNullable(vin).orElse("");
//        return vin;
    }

    public String getMake() {
        return Optional.ofNullable(make).orElse("");
//        return make;
    }

    public String getModel() {
        return Optional.ofNullable(model).orElse("");
//        return model;
    }

    public String getYear() {
        return Optional.ofNullable(year).orElse("");
//        return year;
    }
}
