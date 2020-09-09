package au.com.polonious.integration.dtos.frsDto;

public class CreateCaseDto {
	String claimNumber;
	String description; 
	String managersSectionCode;
	String lossDateTime;        // yyyy-mm-dd  mm/dd/yy ???
	String accidentType;          //   case attr
	String lossLocationState;     // Org Level 3   and location
	String claimPID;              // case attr
	String fcc;         		  // Org Level fcc Code
	String policyType;			  //  Org Level 4
	String policyNumber;        
	String policyPeriod;		  // case attr
	String policyEffectiveDate;	  // ?? Date works?
	//String insured;				  //
	String policyResidenceState;   // case attr
	String policyZipCode;		   // case attr
	String ratedState;	           // case attr
	String siuReferralPublicID;	   // case attr
	String referralStatus;         // case attr
	String referralNumber;         // ref #
	String referralReason;		   // case attr
	String referralType;		   // case attr
	String referralSubject;		   // case attr	
	String region;     // org level 2
	
	String exposureType1;
	String exposureReserveLines1;
	String exposureType2;
	String exposureReserveLines2;
	String exposureType3;
	String exposureReserveLines3;
	String damageNotConsistentWithLossDescription;
	String possibleIntentionalAct;
	String noPriorInsurance;
	String unrelatedClaimantsOrInsuredsUseSameMedicalProvider;
	String noReceiptForRepair;
	
	

	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public String getLossDateTime() {
		return lossDateTime;
	}
	public void setLossDateTime(String lossDateTime) {
		this.lossDateTime = lossDateTime;
	}
	public String getAccidentType() {
		return accidentType;
	}
	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}
	public String getLossLocationState() {
		return lossLocationState;
	}
	public void setLossLocationState(String lossLocationState) {
		this.lossLocationState = lossLocationState;
	}
	public String getClaimPID() {
		return claimPID;
	}
	public void setClaimPID(String claimPID) {
		this.claimPID = claimPID;
	}
	public String getFcc() {
		return fcc;
	}
	public void setFcc(String fcc) {
		this.fcc = fcc;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getPolicyPeriod() {
		return policyPeriod;
	}
	public void setPolicyPeriod(String policyPeriod) {
		this.policyPeriod = policyPeriod;
	}
	public String getPolicyEffectiveDate() {
		return policyEffectiveDate;
	}
	public void setPolicyEffectiveDate(String policyEffectiveDate) {
		this.policyEffectiveDate = policyEffectiveDate;
	}
	public String getPolicyResidenceState() {
		return policyResidenceState;
	}
	public void setPolicyResidenceState(String policyResidenceState) {
		this.policyResidenceState = policyResidenceState;
	}
	public String getPolicyZipCode() {
		return policyZipCode;
	}
	public void setPolicyZipCode(String policyZipCode) {
		this.policyZipCode = policyZipCode;
	}
	public String getRatedState() {
		return ratedState;
	}
	public void setRatedState(String ratedState) {
		this.ratedState = ratedState;
	}
	public String getSiuReferralPublicID() {
		return siuReferralPublicID;
	}
	public void setSiuReferralPublicID(String siuReferralPublicID) {
		this.siuReferralPublicID = siuReferralPublicID;
	}
	public String getReferralStatus() {
		return referralStatus;
	}
	public void setReferralStatus(String referralStatus) {
		this.referralStatus = referralStatus;
	}
	public String getReferralNumber() {
		return referralNumber;
	}
	public void setReferralNumber(String referralNumber) {
		this.referralNumber = referralNumber;
	}
	public String getReferralReason() {
		return referralReason;
	}
	public void setReferralReason(String referralReason) {
		this.referralReason = referralReason;
	}
	public String getReferralType() {
		return referralType;
	}
	public void setReferralType(String referralType) {
		this.referralType = referralType;
	}
	public String getReferralSubject() {
		return referralSubject;
	}
	public void setReferralSubject(String referralSubject) {
		this.referralSubject = referralSubject;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getExposureType1() {
		return exposureType1;
	}
	public void setExposureType1(String exposureType1) {
		this.exposureType1 = exposureType1;
	}
	public String getExposureReserveLines1() {
		return exposureReserveLines1;
	}
	public void setExposureReserveLines1(String exposureReserveLines1) {
		this.exposureReserveLines1 = exposureReserveLines1;
	}
	public String getExposureType2() {
		return exposureType2;
	}
	public void setExposureType2(String exposureType2) {
		this.exposureType2 = exposureType2;
	}
	public String getExposureReserveLines2() {
		return exposureReserveLines2;
	}
	public void setExposureReserveLines2(String exposureReserveLines2) {
		this.exposureReserveLines2 = exposureReserveLines2;
	}
	public String getExposureType3() {
		return exposureType3;
	}
	public void setExposureType3(String exposureType3) {
		this.exposureType3 = exposureType3;
	}
	public String getExposureReserveLines3() {
		return exposureReserveLines3;
	}
	public void setExposureReserveLines3(String exposureReserveLines3) {
		this.exposureReserveLines3 = exposureReserveLines3;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getManagersSectionCode() {
		return managersSectionCode;
	}
	public void setManagersSectionCode(String managersSectionCode) {
		this.managersSectionCode = managersSectionCode;
	}
	public String getDamageNotConsistentWithLossDescription() {
		return damageNotConsistentWithLossDescription;
	}
	public void setDamageNotConsistentWithLossDescription(String damageNotConsistentWithLossDescription) {
		this.damageNotConsistentWithLossDescription = damageNotConsistentWithLossDescription;
	}
	public String getPossibleIntentionalAct() {
		return possibleIntentionalAct;
	}
	public void setPossibleIntentionalAct(String possibleIntentionalAct) {
		this.possibleIntentionalAct = possibleIntentionalAct;
	}
	public String getNoPriorInsurance() {
		return noPriorInsurance;
	}
	public void setNoPriorInsurance(String noPriorInsurance) {
		this.noPriorInsurance = noPriorInsurance;
	}
	public String getUnrelatedClaimantsOrInsuredsUseSameMedicalProvider() {
		return unrelatedClaimantsOrInsuredsUseSameMedicalProvider;
	}
	public void setUnrelatedClaimantsOrInsuredsUseSameMedicalProvider(
			String unrelatedClaimantsOrInsuredsUseSameMedicalProvider) {
		this.unrelatedClaimantsOrInsuredsUseSameMedicalProvider = unrelatedClaimantsOrInsuredsUseSameMedicalProvider;
	}
	public String getNoReceiptForRepair() {
		return noReceiptForRepair;
	}
	public void setNoReceiptForRepair(String noReceiptForRepair) {
		this.noReceiptForRepair = noReceiptForRepair;
	}
	
}
