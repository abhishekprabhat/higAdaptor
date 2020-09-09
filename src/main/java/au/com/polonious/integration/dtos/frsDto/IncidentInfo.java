package au.com.polonious.integration.dtos.frsDto;

import java.util.List;

public class IncidentInfo {
	String description;
	String driverPID;				//": "cc:4016",
	String ownerPID;				//": "cc:4016",
	List<String> areaOfDamages;		//": ["Hood", "Grille", "FrontBumper"],
	String vehiclePID;				//": "cc:3211",
	String make;					//": "FORD",
	String year;					//": "2007",
	String model;					//": "EDGE",
	String vehicleStyle;			//": "auto",
	String vehicleVIN;				//": "1FMBK46CX71061493",
	String incidentPID;				//": "cc:3511",
	String incidentType;			//": "VehicleIncident"
	List<ExposureInfoLine> exposureInfo; 			//":[{
	
	//ReferralInfo referralInfo;
	
	public String getDriverPID() {
		return driverPID;
	}
	public void setDriverPID(String driverPID) {
		this.driverPID = driverPID;
	}
	public String getOwnerPID() {
		return ownerPID;
	}
	public void setOwnerPID(String ownerPID) {
		this.ownerPID = ownerPID;
	}
	public List<String> getAreaOfDamages() {
		return areaOfDamages;
	}
	public void setAreaOfDamages(List<String> areaOfDamages) {
		this.areaOfDamages = areaOfDamages;
	}
	public String getVehiclePID() {
		return vehiclePID;
	}
	public void setVehiclePID(String vehiclePID) {
		this.vehiclePID = vehiclePID;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVehicleStyle() {
		return vehicleStyle;
	}
	public void setVehicleStyle(String vehicleStyle) {
		this.vehicleStyle = vehicleStyle;
	}
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	public String getIncidentPID() {
		return incidentPID;
	}
	public void setIncidentPID(String incidentPID) {
		this.incidentPID = incidentPID;
	}
	public String getIncidentType() {
		return incidentType;
	}
	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}
	public List<ExposureInfoLine> getExposureInfo() {
		return exposureInfo;
	}
	public void setExposureInfo(List<ExposureInfoLine> exposureInfo) {
		this.exposureInfo = exposureInfo;
	}
	public String getDescription() {
		return String.format("%s %s %s", getYear(), getMake(), getModel());
	}


//	public ReferralInfo getReferralInfo() {
//		return referralInfo;
//	}
//	public void setReferralInfo(ReferralInfo referralInfo) {
//		this.referralInfo = referralInfo;
//	}

}
