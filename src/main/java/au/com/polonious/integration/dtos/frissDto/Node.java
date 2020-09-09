package au.com.polonious.integration.dtos.frissDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Node {
    @JsonProperty("EntityId")
    long EntityId;
    @JsonProperty("Object")
    Object object;
    @JsonProperty("Link")
    Link link;
    @JsonProperty("Nodes")
    List<Node> nodes;
    @JsonProperty("IsCycle")
    boolean IsCycle;
    @JsonProperty("IsValid")
    boolean IsValid;
}
