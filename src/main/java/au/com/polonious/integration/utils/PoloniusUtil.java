package au.com.polonious.integration.utils;

import au.com.polonious.integration.dtos.ecosDto.PoloniusCreateCaseDto;
import au.com.polonious.integration.dtos.frissDto.FrissCreateCaseDto;
import au.com.polonious.integration.dtos.frissDto.FrissResponseCreateCase;
import au.com.polonious.integration.dtos.frsDto.ContactInfo;
import au.com.polonious.integration.dtos.frsDto.IncidentInfo;
import au.com.polonious.integration.polonious.dtos.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;

@Service
public class PoloniusUtil {

    private static String pimsUrl;
    private static String oAuthUser;
    private static String oAuthUserSecret;
    private static String pimsEcosUrl;

    public static PoloniusFeignClient poloniusFeignClient;

    @Value( "${pimsUrl}" )
    public void setPimsUrl(String url){
        PoloniusUtil.pimsUrl = url;
    }
    @Value( "${pimsEcosUrl}" )
    public void setPimsEcosUrl(String url){
        PoloniusUtil.pimsEcosUrl = url;
    }
    @Value( "${oAuthUser}" )
    public void setoAuthUser(String oAuthUser){
        PoloniusUtil.oAuthUser = oAuthUser;
    }
    @Value( "${oAuthUserSecret}" )
    public void setoAuthUserSecret(String oAuthUserSecret){
        PoloniusUtil.oAuthUserSecret = oAuthUserSecret;
    }

    @Autowired
    public PoloniusUtil (PoloniusFeignClient poloniusFeignClient){
        PoloniusUtil.poloniusFeignClient = poloniusFeignClient;
    }
    public static String getToken(){

        String fullUrl=PoloniusUtil.pimsEcosUrl
                +"/pcmsrest/oauth/token?"
                +"client_secret=" + PoloniusUtil.oAuthUserSecret
                +"&client_id=" + PoloniusUtil.oAuthUser
                +"&grant_type=client_credentials";

        System.out.println("About to send OAuth:"+fullUrl);


        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<OAuthResponse> responseOAuth = restTemplate.
                    postForEntity(
                            fullUrl,
                            null,
                            OAuthResponse.class);
            System.out.println("Response:"+responseOAuth);
            return responseOAuth.getBody().getAccess_token();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Exception: "+e);
        }
        return null;
    }

    public static ResponseEntity<FrissResponseCreateCase> frissCreateCase(String token, FrissCreateCaseDto createCaseDto){
        try{
            System.out.println("Token:"+token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer "+token);

            HttpEntity<FrissCreateCaseDto> createCaseRequest = new HttpEntity<FrissCreateCaseDto>(createCaseDto,headers);

            RestTemplate postCaseRestTemplate = new RestTemplate();

            ResponseEntity<FrissResponseCreateCase> response = postCaseRestTemplate
                    .exchange(PoloniusUtil.pimsUrl+"/public/oauth/task/v1/mapping/FRISSCreateCase",
                            HttpMethod.POST,
                            createCaseRequest,
                            FrissResponseCreateCase.class);
            return response;
        }catch (HttpClientErrorException e){
            System.out.println(e.getCause());
        }
        return null;
    }

    /**
     * Takes primarily contactInfo, and finds or creates person as required.
     * @param token
     * @param personTypeDescription
     * @param contactInfo
     * @return
     * @throws Exception
     */
    public static Long findOrCreatePerson(
            String token,
            String personTypeDescription,
            ContactInfo contactInfo
    ) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        System.out.println("Token: " + token);
        return PoloniusUtil.findOrCreatePerson(headers, personTypeDescription, contactInfo);
    }
    public static Long findOrCreatePerson(
            MultiValueMap<String, String> header,
            String personTypeDescription,
            ContactInfo contactInfo
    ) throws Exception{

        Long id=null;

        try {
            RestTemplate findPersonRestTemplate = new RestTemplate();

            HttpEntity<?> entity = new HttpEntity<>(header);

            System.out.println("contactInfo.getContactPID(): "+contactInfo.getContactPID());

            String url=PoloniusUtil.pimsEcosUrl + "/public/oauth/person/v2/firstId";


            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("originalSystemId", contactInfo.getContactPID());

            System.out.printf("Find Person Url: %s\n", builder.toUriString());
            ResponseEntity<Long> personResponse =
                    findPersonRestTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            entity,
                            Long.class);

            System.out.println("\tResponse: Person Search: personResponse.getBody()"+personResponse.getBody()
                    +" StatusCode: "+personResponse.getStatusCodeValue()
                    +" has Body:"+personResponse.hasBody());

            if (personResponse.getBody() != null && personResponse.getBody() != -1) {
                System.out.println("Person FOUND " + personResponse.getBody());
                return personResponse.getBody();
            }
            else {
                System.out.println("Person NOT FOUND. Trying to create " + personResponse.getBody());
                // Create a person as none found.
                HashMap<String,String> personCreate= new HashMap<String,String>();
                personCreate.put("personTypeDescription", personTypeDescription);
                personCreate.put("originalSystemId", contactInfo.getContactPID());
                personCreate.put("firstName", contactInfo.getFirstName());
                personCreate.put("middleNames", contactInfo.getMiddleName());
                personCreate.put("lastName", contactInfo.getLastName());
                personCreate.put("phoneNumber", contactInfo.getPhoneNumber());
                personCreate.put("email", contactInfo.getEmailAddress());
                personCreate.put("dob", contactInfo.getDateOfBirth());

                /**
                 * Add other properties as retrieved from map
                 */
                contactInfo.getMap().entrySet().stream().forEach(c -> personCreate.put(c.getKey(), c.getValue()));

                if (contactInfo.getPrimaryAddress() != null) {
                    personCreate.put("addressLine1", contactInfo.getPrimaryAddress().getAddressLine1());
                    personCreate.put("city", contactInfo.getPrimaryAddress().getCity());
                    personCreate.put("state", contactInfo.getPrimaryAddress().getState());
                    personCreate.put("zipCode", contactInfo.getPrimaryAddress().getZipCode());
                    personCreate.put("country", contactInfo.getPrimaryAddress().getCountry());
                }
                String personId = poloniusFeignClient.createEcosPerson(personCreate);
//                HttpEntity<HashMap<String,String>> createPersonRequest = new HttpEntity<HashMap<String,String>>(personCreate,header);
//                System.out.println("Request body " + createPersonRequest.getBody());
//                RestTemplate postPersonRestTemplate = new RestTemplate();
//                ResponseEntity<String> response = postPersonRestTemplate
//                        .exchange(PoloniusUtil.pimsEcosUrl + "/public/oauth/person/v3",
//                                HttpMethod.POST,
//                                createPersonRequest,
//                                String.class);
//                //System.out.println(">>Response: "+response);
//                String body=response.getBody();
                id=Long.parseLong(personId);
                System.out.println("CREATED PersonId: "+personId);
                return id;
            }
        }
        catch (HttpClientErrorException e) {
            System.out.println(e.getRawStatusCode()+" FAILED PERSON with: "+e.getResponseBodyAsString());
            return new Long(-1);
        }
        catch (HttpServerErrorException e) {
            System.out.println(e.getRawStatusCode()+" FAILED PERSON with: "+e.getResponseBodyAsString());
            return new Long(-1);
        }
        catch (Exception e) {
//            e.printStackTrace();
            System.out.println("FAILED PERSON. Non-Http exception. Returning -1.\n\t------"+e.toString());
            return new Long(-1);
        }

    }

    /**
     * Takes primarily two parameters - taskId (from createCase) and personId (from findOrCreatePerson) and
     * links the two in Polonius system.
     * @param token
     * @param taskId
     * @param roleDescription
     * @param personId
     */
    public static void createTaskPersonLink (
            String token,
            Long taskId,
            String roleDescription,
            Long personId	) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        createTaskPersonLink(headers, taskId, roleDescription, personId);
    }
    public static void createTaskPersonLink (
            MultiValueMap<String, String> header,
            Long taskId,
            String roleDescription,
            Long personId	) {

        RestTemplate postTaskPersonRoleRestTemplate = new RestTemplate();
        System.out.println("Adding TPR Role: " + roleDescription+" taskId:"+taskId+" personId:"+personId);
        //RestTemplate restTemplate = new RestTemplate();
        HashMap<String,String> tprCreate= new HashMap<String,String>();
        tprCreate.put("taskId", taskId.toString());
        tprCreate.put("roleDescription", roleDescription);
        tprCreate.put("personId", personId.toString());

        HttpEntity<HashMap<String,String>> createTprRequest = new HttpEntity<HashMap<String,String>>(tprCreate, header);

        try {
            String taskPersonLinkResult = poloniusFeignClient.createEcosPersonTaskLink(tprCreate);
            System.out.println("\t Link Creation = " + taskPersonLinkResult);
//            ResponseEntity<String> response = postTaskPersonRoleRestTemplate
//                    .exchange(PoloniusUtil.pimsEcosUrl + "/public/oauth/task/v1/taskPersonRole",
//                            HttpMethod.POST,
//                            createTprRequest,
//                            String.class);
        }
        catch (HttpClientErrorException e) {
            System.out.println(e.getRawStatusCode()+" Create Link Task-Person FAILED \t"+e.getResponseBodyAsString());
        }
        catch (HttpServerErrorException e) {
            System.out.println(e.getRawStatusCode()+" Create Link Task-Person FAILED \t"+e.getResponseBodyAsString());
        }
        catch (Exception e) {
            System.out.println(" Create Link Task-Person FAILED. Non-http error. \t"+e.toString());
        }
    }

    /*
  driverPID 	: cc:4016
  OwnerPID 		: cc:4016
  areaOfDamages ..
    area : Hood
    area : Grille
    area : FrontBumper
  vehiclePID 	: cc:3211
  make		 	: FORD
  year          : 2007
  model         : EDGE
  vehicleStyle 	: auto
  vehicleVIN 	: 1FMBK46CX71061493
  incidentPID 	: cc:3511
  incidentType 	: VehicleIncident
  exposureInfo ..
    exposureType : COLLISION
      reserveLines..
        Line : COLLISION
        Line : PAYOUT
 */
    public static Long findOrCreateAsset(
            String token,
            IncidentInfo incidentInfo
    ) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        return PoloniusUtil.findOrCreateAsset(headers, incidentInfo);
    }
    public static Long findOrCreateAsset(
            MultiValueMap<String, String> header,
            IncidentInfo incidentInfo
    ) throws Exception{

        Long id=null;

        try {
            RestTemplate getAssetRestTemplate = new RestTemplate();

            HttpEntity<?> entity = new HttpEntity<>(header);

            System.out.println("Incident pid: "+incidentInfo.getIncidentPID());

            String url=pimsUrl+"/public/oauth/asset/v2";

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("originalSystemId", incidentInfo.getIncidentPID());

            ResponseEntity<HashMap[]> assetResponse =
                    getAssetRestTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            entity,
                            HashMap[].class);

            System.out.println("assetResponse.getBody(): "+assetResponse.getBody());

            if (assetResponse.getBody() !=null
                    && assetResponse.getBody().length > 0) {
                for (HashMap asset : assetResponse.getBody()) {
                    Integer intid=(Integer)asset.get("id");
                    System.out.println("found AssetId: "+intid);
                    return new Long(intid);
                }
            }
            else {
                // Create an asset as none found.
                HashMap<String,String> assetCreate= new HashMap<String,String>();
                assetCreate.put("assetTypeDescription", incidentInfo.getIncidentType());
                assetCreate.put("description", incidentInfo.getDescription());
                assetCreate.put("originalSystemId", incidentInfo.getIncidentPID());
                assetCreate.put("make", incidentInfo.getMake());
                assetCreate.put("model", incidentInfo.getModel());
                assetCreate.put("ownerPID", incidentInfo.getOwnerPID());
                assetCreate.put("vehiclePID", incidentInfo.getVehiclePID());
                assetCreate.put("vehicleStyle", incidentInfo.getVehicleStyle());
                assetCreate.put("VIN", incidentInfo.getVehicleVIN());
                assetCreate.put("year", incidentInfo.getYear());
                if (incidentInfo.getAreaOfDamages() != null)
                    assetCreate.put("areaOfDamages", incidentInfo.getAreaOfDamages().toString());

                HttpEntity<HashMap<String,String>> createAssetRequest = new HttpEntity<HashMap<String,String>>(assetCreate,header);
                RestTemplate postAssetRestTemplate = new RestTemplate();
                ResponseEntity<String> response = postAssetRestTemplate
                        .exchange(pimsUrl+"/public/oauth/asset/v3",
                                HttpMethod.POST,
                                createAssetRequest,
                                String.class);

                System.out.println(">>Asset Response: "+response.getBody());
                String body=response.getBody();
                id=Long.parseLong(body);
                System.out.println("Created AssetId: "+id);
                return id;
            }
        }
        catch (HttpClientErrorException e) {
            System.out.println(e.getRawStatusCode()+"- HTTP Status Error with Asset REST Call- failed with: "+e.getResponseBodyAsString());
            return new Long(-1);
        }
        catch (HttpServerErrorException e) {
            System.out.println(e.getRawStatusCode()+"- HTTP Status Error with Asset REST Call- failed with: "+e.getResponseBodyAsString());
            return new Long(-1);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem with Asset REST calls - failed with an exception: "+e.toString());
            return new Long(-1);
        }
        return id;
    }

    public static void createTaskAssetLink (
            String token,
            Long taskId,
            String roleDescription,
            Long assetId	) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        PoloniusUtil.createTaskAssetLink(headers, taskId, roleDescription, assetId);
    }

    public static void createTaskAssetLink (
            MultiValueMap<String, String> header,
            Long taskId,
            String roleDescription,
            Long assetId	) {

        RestTemplate postAssetPersonRoleRestTemplate = new RestTemplate();

        System.out.println("Adding TAR Role: " + roleDescription+" taskid:"+taskId+" assetId:"+assetId);

        //RestTemplate restTemplate = new RestTemplate();
        HashMap<String,String> tarCreate= new HashMap<String,String>();
        tarCreate.put("taskId", taskId.toString());
        tarCreate.put("roleDescription", roleDescription);
        tarCreate.put("assetId", assetId.toString());

        HttpEntity<HashMap<String,String>> createTarRequest = new HttpEntity<HashMap<String,String>>(tarCreate, header);

        try {
            ResponseEntity<String> response = postAssetPersonRoleRestTemplate
                    .exchange(pimsUrl+"/public/oauth/task/v1/taskAssetRole",
                            HttpMethod.POST,
                            createTarRequest,
                            String.class);
        }
        catch (HttpClientErrorException e) {
            System.out.println(e.getRawStatusCode()+"- HTTP Status Error with TAR REST Call- failed with: "+e.getResponseBodyAsString());
        }
        catch (HttpServerErrorException e) {
            System.out.println(e.getRawStatusCode()+"- HTTP Status Error with TAR REST Call- failed with: "+e.getResponseBodyAsString());
        }
        catch (Exception e) {
            System.out.println("Problem with TAR REST calls - failed with an exception: "+e.toString());
        }

    }

    /**
     * Converts a given text to Pascal case, e.d. 'driver license' -> 'Driver License'
     * @param text
     * @return
     */
    public static String toPascalCase(String text){
        String[] words = text.split("\\s");
        StringBuffer stringBuffer = new StringBuffer();
        //  If multiple words then append all Pascal case
        if (words.length > 1){
            Arrays.stream(words).forEach(c -> stringBuffer.append(PoloniusUtil.toPascalCase(c.toString()) + " "));
        }else{
            stringBuffer.append(words[0].substring(0, 1).toUpperCase()).append(words[0].substring(1).toLowerCase());
        }
        return stringBuffer.toString().trim();
    }

    public static ResponseEntity<FrissResponseCreateCase> ecosCreateCase(String token, PoloniusCreateCaseDto poloniusCreateCaseDto, String dtoAsString) {
        try{
            System.out.println("Token:"+token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer "+token);
            headers.setContentType(MediaType.APPLICATION_JSON);

//            HttpEntity<EcosCreateCaseDto> createCaseRequest = new HttpEntity<EcosCreateCaseDto>(ecosCreateCaseDto,headers);
            HttpEntity<String> createCaseRequest = new HttpEntity<String>(dtoAsString, headers);

            RestTemplate postCaseRestTemplate = new RestTemplate();
            ResponseEntity<FrissResponseCreateCase> response1 = postCaseRestTemplate.postForEntity(PoloniusUtil.pimsEcosUrl+"public/oauth/task/v1/mapping/hcreatecase",
                    createCaseRequest, FrissResponseCreateCase.class);

            ResponseEntity<FrissResponseCreateCase> response = postCaseRestTemplate
                    .exchange(PoloniusUtil.pimsEcosUrl+"public/oauth/task/v1/mapping/hcreatecase",
                            HttpMethod.POST,
                            createCaseRequest,
                            FrissResponseCreateCase.class);
            return response;
        }catch (HttpClientErrorException e){
            System.out.println(e.getCause());
        }
        return null;
    }
}
