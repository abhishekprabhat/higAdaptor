package au.com.polonious.integration.dtos.frissDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE,
        setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class FrissCreateCaseDto {
    String description;
    String lineOfBusiness;
    String lossState;
    String insuredOffice;
    String claimOffice;
    @JsonProperty("Branch")
    String Branch;
    @JsonProperty("Product")
    String Product;
    @JsonProperty("DetectionDate")
    String DetectionDate;
    @JsonProperty("Score")
    String Score;
    @JsonProperty("Argument")
    String Argument;
    @JsonProperty("ClaimId")
    String ClaimId;
    @JsonProperty("DateOccurred")
    String DateOccurred;
    @JsonProperty("Amount")
    Long Amount;
    @JsonProperty("Cause")
    String Cause;
    @JsonProperty("AccidentType")
    String AccidentType;
    @JsonProperty("DamageType")
    String DamageType;
    @JsonProperty("ClaimStatement")
    String ClaimStatement;
    @JsonProperty("PolicyId")
    String PolicyId;
    @JsonProperty("DateReported")
    String DateReported;
    @JsonProperty("ClaimAddressLine1")
    String ClaimAddressLine1;
    @JsonProperty("ClaimPostalCode")
    String ClaimPostalCode;
    @JsonProperty("ClaimCity")
    String ClaimCity;
    @JsonProperty("ClaimState")
    String ClaimState;
    @JsonProperty("ClaimCountry")
    String ClaimCountry;
//    @JsonProperty("License")
//    String LicensePlate;
//    @JsonProperty("VINNumber")
//    String Vin;
//    @JsonProperty("Year")
//    Integer Year;
//    @JsonProperty("Make")
//    String Make;
//    @JsonProperty("Model")
//    String Model;
}
