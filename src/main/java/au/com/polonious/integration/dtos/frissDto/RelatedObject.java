package au.com.polonious.integration.dtos.frissDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RelatedObject {
    @JsonProperty("Nodes")
    List<Node> nodes;
}
