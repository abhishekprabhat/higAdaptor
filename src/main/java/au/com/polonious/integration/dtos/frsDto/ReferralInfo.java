package au.com.polonious.integration.dtos.frsDto;

public class ReferralInfo {

	 String siuReferralPublicID;  	// ": "cc:9999",
     String referralStatus;       	// ": "Sent",
     String referralNumber;			// ": "S4004935020101031-1",
     String referralReason;			// ": "ClaimIssue",
     String referralType;			// ": "SuspectedClaimFraud",
     String referralSubject;		// ": "Test",
     String referredBy;				// ": "cc:2456",
     String fileOwner;				//": "cc:5367"
     
     
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
	public String getReferredBy() {
		return referredBy;
	}
	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}
	public String getFileOwner() {
		return fileOwner;
	}
	public void setFileOwner(String fileOwner) {
		this.fileOwner = fileOwner;
	}
}
