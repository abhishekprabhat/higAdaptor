package au.com.polonious.integration.dtos.frsDto;

public class CreateCaseDtoTest {
	String claimNumber;
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
	//String referredBy;			   // tpr
	//String fileOwner;					// tpr
	
	String region;     // org level 2

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
}
