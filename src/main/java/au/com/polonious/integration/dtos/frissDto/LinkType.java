package au.com.polonious.integration.dtos.frissDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LinkType {
    @JsonProperty("LinkTypeObjectToObjectId")
    int linkTypeObjectToObjectId;
    @JsonProperty("IsActive")
    boolean isActive;
    @JsonProperty("AuthorizationLock")
    Object authorizationLock;
    @JsonProperty("Value")
    String value;
}
