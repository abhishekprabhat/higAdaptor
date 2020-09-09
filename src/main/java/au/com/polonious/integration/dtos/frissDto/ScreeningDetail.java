package au.com.polonious.integration.dtos.frissDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScreeningDetail {
    @JsonProperty("Branch")
    String Branch;
    @JsonProperty("Product")
    String Product;
    @JsonProperty("Process")
    String Process;
    @JsonProperty("Label")
    String Label;
    @JsonProperty("DetectionDate")
    String DetectionDate;
    @JsonProperty("Score")
    String Score;
    @JsonProperty("Argument")
    String Argument;
}
