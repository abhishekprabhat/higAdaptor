package au.com.polonious.integration.dtos.frissDto.objectDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE,
        setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class AddressObject extends GenericNodeObject{
    String Street;
    String HouseNumber;
    String HouseNumberAddition;
    String PostalCode;
    String City;
    @JsonProperty("Country")
    CountrySubObject country;

}
