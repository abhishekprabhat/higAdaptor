package au.com.polonious.integration.utils;


import au.com.polonious.integration.dtos.ecosDto.PoloniusCreateCaseDto;
import au.com.polonious.integration.dtos.frissDto.FrissResponseCreateCase;
import au.com.polonious.integration.dtos.frissDto.EcosResponseCreateCase;
import au.com.polonious.integration.dtos.referralDto.poloniusDto.AssetDto;
import au.com.polonious.integration.dtos.referralDto.poloniusDto.CreateCaseDto;
import au.com.polonious.integration.dtos.referralDto.poloniusDto.PoloniusTaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


@FeignClient(name = "abc", url = "https://hartford.poloniouslive.com/", configuration = FeignConfiguration.class)
public interface PoloniusFeignClient {
    @RequestMapping(value = "thehartford/public/oauth/task/v1/mapping/higmapping1", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EcosResponseCreateCase createEcosCase(@RequestBody PoloniusCreateCaseDto poloniusCreateCaseDto);

    @RequestMapping(value = "thehartford/public/oauth/task/v1/mapping/higmapping1", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EcosResponseCreateCase createCaseTask(@RequestBody CreateCaseDto poloniusCreateCaseDto);

    @RequestMapping(value = "thehartford/public/oauth/task/v2.1", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<PoloniusTaskDto> getPoloniusTask(@RequestParam ("eventNumber") String eventNumber);

    @RequestMapping(value = "hartford/public/oauth/task/v1/mapping/hcreatecase", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String getEcosPerson(@RequestParam("originalSystemId") String originalSystemId);

    @RequestMapping(value = "hartford/public/oauth/person/v3", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String createEcosPerson(@RequestBody HashMap<String,String> createPersonRequest);

    @RequestMapping(value = "hartford/public/oauth/task/v1/taskPersonRole", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String createEcosPersonTaskLink(@RequestBody HashMap<String,String> personTaskLink);

    @RequestMapping(value = "thehartford/public/oauth/asset/v3", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Long createAsset(@RequestBody AssetDto assetDto);
}
