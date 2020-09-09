package au.com.polonious.integration.dtos.frsDto;

public class PolicyInfo {

    String policyType; 				//": "AUTO",
    String policyNumber; 			//": "3028713513",
	String policyPeriod; 			//": "12/12/2018 - 06/12/2019",
	String policyEffectiveDate; 	//": "Fri Mar 01 01:00:00 EST 2019",
	String insured;                 // "cc:4016"
    String policyResidenceState; 	//": "Maryland",
    String policyZipCode; 			//": "21201",
	String ratedState; 				//" : "Maryland"
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
	public String getInsured() {
		return insured;
	}
	public void setInsured(String insured) {
		this.insured = insured;
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

}
	
