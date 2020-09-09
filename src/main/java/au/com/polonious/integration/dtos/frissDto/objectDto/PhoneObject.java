package au.com.polonious.integration.dtos.frissDto.objectDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE,
        setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class PhoneObject extends GenericNodeObject{
    String Value;
    String TypeName;
}
