package au.com.polonious.integration.utils;


import au.com.polonious.integration.dtos.ecosDto.PoloniusCreateCaseDto;
import au.com.polonious.integration.dtos.frissDto.FrissResponseCreateCase;
import au.com.polonious.integration.dtos.frissDto.EcosResponseCreateCase;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;


@FeignClient(name = "abc", url = "https://hartford.poloniouslive.com/", configuration = FeignConfiguration.class)
public interface PoloniusFeignClient {
    @RequestMapping(value = "thehartford/public/oauth/task/v1/mapping/higmapping1", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EcosResponseCreateCase createEcosCase(@RequestBody PoloniusCreateCaseDto poloniusCreateCaseDto);

    @RequestMapping(value = "hartford/public/oauth/task/v1/mapping/hcreatecase", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String getEcosPerson(@RequestParam("originalSystemId") String originalSystemId);

    @RequestMapping(value = "hartford/public/oauth/person/v3", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String createEcosPerson(@RequestBody HashMap<String,String> createPersonRequest);

    @RequestMapping(value = "hartford/public/oauth/task/v1/taskPersonRole", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String createEcosPersonTaskLink(@RequestBody HashMap<String,String> personTaskLink);
}
