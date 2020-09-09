package au.com.polonious.integration.dtos.frissDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FrsPayloadDTO {
    String description;
    String LineOfBusiness;
    String lossState;
    String insuredOffice;
    String claimOffice;
    @JsonProperty("ScreeningDetails")
    ScreeningDetail screeningDetails;
    @JsonProperty("RelatedObject")
    RelatedObject relatedObject;
}
