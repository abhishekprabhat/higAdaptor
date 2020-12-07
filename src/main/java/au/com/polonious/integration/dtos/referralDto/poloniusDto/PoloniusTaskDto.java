package au.com.polonious.integration.dtos.referralDto.poloniusDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class PoloniusTaskDto {
    Long id;
    List<TaskAssetRole> taskAssetRoles;
}
