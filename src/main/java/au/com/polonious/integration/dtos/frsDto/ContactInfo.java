package au.com.polonious.integration.dtos.frsDto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ContactInfo {

	String contactPID;		//": "cc:4016",
	String firstName;		//": "Firstname",
	String middleName;		//": "MiddleName",	
	String lastName; 		//": "Uibttsuashxsyrv",
	String dateOfBirth;		//": "06/06/66",  MM/dd/yy
	String phoneNumber; 	//" : "240-332-8769",
	String mobileNumber;
    String racFid;			//" : "23452",
    String emailAddress;	//" : sdon@aol.com,
	List<String> roles = new ArrayList<>();
	Map<String, String> map = new HashMap<>();
	IncidentInfo incidentInfo;
	PrimaryAddress primaryAddress;

	String personTypeDescription;
	public String getContactPID() {
		return contactPID;
	}
	public void setContactPID(String contactPID) {
		this.contactPID = contactPID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public IncidentInfo getIncidentInfo() {
		return incidentInfo;
	}
	public void setIncidentInfo(IncidentInfo incidentInfo) {
		this.incidentInfo = incidentInfo;
	}
	public PrimaryAddress getPrimaryAddress() {
		return primaryAddress;
	}
	public void setPrimaryAddress(PrimaryAddress primaryAddress) {
		this.primaryAddress = primaryAddress;
	}
	public String getRacFid() {
		return racFid;
	}
	public void setRacFid(String racFid) {
		this.racFid = racFid;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}


}
