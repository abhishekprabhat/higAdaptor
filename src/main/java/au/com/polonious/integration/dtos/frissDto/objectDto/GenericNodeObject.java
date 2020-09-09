package au.com.polonious.integration.dtos.frissDto.objectDto;

import au.com.polonious.integration.dtos.frissDto.Node;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GenericNodeObject {
    @JsonProperty("ObjectType")
    public int objectType;  //  To be caps
    public ObjectType objectTypeEnum;
    public Node parent;

    public void setEnum(){
        this.objectTypeEnum = ObjectType.fromInteger(this.objectType);
    }
}
