package au.com.polonious.integration.dtos.ecosDto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.ALWAYS)
//@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE,
//        setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class EcosCreateCaseDto {
//    @JsonSetter(nulls=Nulls.SKIP)
    String description = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String eventNum = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String largeLoss = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String claimPlusIndicator = "";


    String policyOrigEffectiveDate = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String policyEffectiveDate = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String lob = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String businessTypeDesc = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String lossState2 = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String policyState2 = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String claimOffice = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String claimNumber = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String lossDate = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String lossLocAddrLine1 = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String lossLocCity = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String lossLocState = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String lossLocZip = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String lossLocCountry = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String claimDesc = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String lossDesc = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String financialTotalReserves = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String policyNum = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String reportedDate = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String exposureId = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String claimStatusAtReferral = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String claim_sym = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String policyState = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String businessTypeCode = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String hubOffice = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String policyExpiryDate = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String importReason = "";
    @JsonSetter(nulls=Nulls.SKIP)
    @JsonProperty("referral Source")
    String referralSource = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String referralId = "";
    @JsonSetter(nulls=Nulls.SKIP)
    String referralNote = "";

    public String getDescription() {
        if (description!= null) return description;
        else return "";
    }

    public String getEventNum() {
        if (eventNum!= null) return eventNum;
        else return "";
    }

    public String getLargeLoss() {
        if (largeLoss!= null) return largeLoss;
        else return "";
    }

    public String getClaimPlusIndicator() {
        if (claimPlusIndicator!= null) return claimPlusIndicator;
        else return "";
    }

    public String getPolicyOrigEffectiveDate() {
        if (policyOrigEffectiveDate!= null) return policyOrigEffectiveDate;
        else return "";
    }

    public String getPolicyEffectiveDate() {
        if (policyEffectiveDate!= null) return policyEffectiveDate;
        else return "";
    }

    public String getLob() {
        return "Personal Property";
//        if (lob!= null) return lob;
//        else return "";
    }

    public String getBusinessTypeDesc() {
        return "Personal Property";
//        if (businessTypeDesc!= null) return businessTypeDesc;
//        else return "";
    }

    public String getLossState2() {
        if (lossState2!= null) return lossState2;
        else return "";
    }

    public String getPolicyState2() {
        if (policyState2!= null) return policyState2;
        else return "";
    }

    public String getClaimOffice() {
        return "TBX";
//        if (claimOffice!= null) return claimOffice;
//        else return "";
    }

    public String getClaimNumber() {
        if (claimNumber!= null) return claimNumber;
        else return "";
    }

    public String getLossDate() {
        if (lossDate!= null) return lossDate;
        else return "";
    }

    public String getLossLocAddrLine1() {
        if (lossLocAddrLine1!= null) return lossLocAddrLine1;
        else return "";
    }

    public String getLossLocCity() {
        if (lossLocCity!= null) return lossLocCity;
        else return "";
    }

    public String getLossLocState() {
        if (lossLocState!= null) return lossLocState;
        else return "";
    }

    public String getLossLocZip() {
        if (lossLocZip!= null) return lossLocZip;
        else return "";
    }

    public String getLossLocCountry() {
        if (lossLocCountry!= null) return lossLocCountry;
        else return "";
    }

    public String getClaimDesc() {
        if (claimDesc!= null) return claimDesc;
        else return "";
    }

    public String getLossDesc() {
        if (lossDesc!= null) return lossDesc;
        else return "";
    }

    public String getFinancialTotalReserves() {
        if (financialTotalReserves!= null) return financialTotalReserves;
        else return "";
    }

    public String getPolicyNum() {
        if (policyNum!= null) return policyNum;
        else return "";
    }

    public String getReportedDate() {
        if (reportedDate!= null) return reportedDate;
        else return "";
    }

    public String getExposureId() {
        if (exposureId!= null) return exposureId;
        else return "";
    }

    public String getClaimStatusAtReferral() {
        if (claimStatusAtReferral!= null) return claimStatusAtReferral;
        else return "";
    }

    public String getClaim_sym() {
        if (claim_sym!= null) return claim_sym;
        else return "";
    }

    public String getPolicyState() {
        if (policyState!= null) return policyState;
        else return "";
    }

    public String getBusinessTypeCode() {
        if (businessTypeCode!= null) return businessTypeCode;
        else return "";
    }

    public String getHubOffice() {
        if (hubOffice!= null) return hubOffice;
        else return "";
    }

    public String getPolicyExpiryDate() {
        if (policyExpiryDate!= null) return policyExpiryDate;
        else return "";
    }

    public String getImportReason() {
        if (importReason!= null) return importReason;
        else return "";
    }

    public String getReferralSource() {
        if (referralSource!= null) return referralSource;
        else return "";
    }

    public String getReferralId() {
        if (referralId!= null) return referralId;
        else return "";
    }

    public String getReferralNote() {
        if (referralNote!= null) return referralNote;
        else return "";
    }

    public void setPolicyOrigEffectiveDate(String policyOrigEffectiveDate) {
        if (policyOrigEffectiveDate == null) policyOrigEffectiveDate = "";
        this.policyOrigEffectiveDate = policyOrigEffectiveDate;
    }

    public void setLossLocAddrLine1(String lossLocAddrLine1) {
        if (lossLocAddrLine1 == null) lossLocAddrLine1 = "";
        this.lossLocAddrLine1 = lossLocAddrLine1;
    }

    public void setLossLocZip(String lossLocZip) {
        if (lossLocZip == null) lossLocZip = "";
        this.lossLocZip = lossLocZip;
    }
}
