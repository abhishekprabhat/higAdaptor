package au.com.polonious.integration.dtos.frsDto;

import java.util.List;

public class ExposureInfoLine {
	String exposureType; //": "COLLISION",
    List<String> exposureReserveLines; //": ["COLLISION", "PAYOUT"]
	public String getExposureType() {
		return exposureType;
	}
	public void setExposureType(String exposureType) {
		this.exposureType = exposureType;
	}
	public List<String> getExposureReserveLines() {
		return exposureReserveLines;
	}
	public void setExposureReserveLines(List<String> exposureReserveLines) {
		this.exposureReserveLines = exposureReserveLines;
	}
}
