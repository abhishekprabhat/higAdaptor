package au.com.polonious.integration.dtos.frissDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Link {
    @JsonProperty("LinkType")
    LinkType linkType;
    @JsonProperty("ValidFrom")
    LocalDateTime validFrom;
    @JsonProperty("ValidUntill")
    LocalDateTime validUntill;
    @JsonProperty("PublicationDate")
    LocalDateTime publicationDate;
}
