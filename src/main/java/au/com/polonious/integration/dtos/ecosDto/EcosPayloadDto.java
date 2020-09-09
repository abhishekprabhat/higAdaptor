package au.com.polonious.integration.dtos.ecosDto;

import au.com.polonious.integration.dtos.frissDto.ScreeningDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcosPayloadDto {
    String claimOffice;
    @JsonProperty("Header")
    EcosHeader header;
    @JsonProperty("Body")
    EcosBody body;

}
