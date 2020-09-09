package au.com.polonious.integration.utils;


import au.com.polonious.integration.dtos.ecosDto.EcosCreateCaseDto;
import au.com.polonious.integration.dtos.frissDto.FrissResponseCreateCase;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;


@FeignClient(name = "abc", url = "https://pocusa.poloniouslive.com/", configuration = FeignConfiguration.class)
public interface EcosFeignClient {
    @RequestMapping(value = "newview/public/oauth/task/v1/mapping/hcreatecase", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    FrissResponseCreateCase createEcosCase(@RequestBody EcosCreateCaseDto ecosCreateCaseDto);

    @RequestMapping(value = "newview/public/oauth/task/v1/mapping/hcreatecase", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String getEcosPerson(@RequestParam("originalSystemId") String originalSystemId);

    @RequestMapping(value = "newview/public/oauth/person/v3", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String createEcosPerson(@RequestBody HashMap<String,String> createPersonRequest);

    @RequestMapping(value = "newview/public/oauth/task/v1/taskPersonRole", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String createEcosPersonTaskLink(@RequestBody HashMap<String,String> personTaskLink);
}
