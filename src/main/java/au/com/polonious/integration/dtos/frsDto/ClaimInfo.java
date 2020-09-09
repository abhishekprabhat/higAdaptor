package au.com.polonious.integration.dtos.frsDto;

import java.util.List;

public class ClaimInfo {

	String claimNumber;      		//": "4004935020101031",
    String lossDateTime; 			//": "Fri Mar 01 01:00:00 EST 2019",
    String accidentType; 			//"036",
    String lossLocationState; 		//": "Maryland",
    String claimPID; 				//": "cc:3108",
    String fcc;						//": "05",
    String claimStatus;    			//": "Open",
    PolicyInfo policyInfo;
    List<ContactInfo> contactInfo;
    ReferralInfo referralInfo;
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
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public PolicyInfo getPolicyInfo() {
		return policyInfo;
	}
	public void setPolicyInfo(PolicyInfo policyInfo) {
		this.policyInfo = policyInfo;
	}
	public List<ContactInfo> getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(List<ContactInfo> contactInfo) {
		this.contactInfo = contactInfo;
	}
	public ReferralInfo getReferralInfo() {
		return referralInfo;
	}
	public void setReferralInfo(ReferralInfo referralInfo) {
		this.referralInfo = referralInfo;
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
