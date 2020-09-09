package au.com.polonious.integration.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.com.polonious.integration.utils.PoloniusUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.polonious.integration.dtos.frsDto.ClaimInfo;
import au.com.polonious.integration.dtos.frsDto.ContactInfo;
import au.com.polonious.integration.dtos.frsDto.CreateCaseDto;
import au.com.polonious.integration.dtos.frsDto.ExposureInfoLine;
import au.com.polonious.integration.dtos.frsDto.IncidentInfo;

@RestController
@RequestMapping("/frs")
public class FrsController {

	@Value( "${pimsUrl}" )
	private String pimsUrl;
	@Value( "${oAuthUser}" )
	private String oAuthUser;
	@Value( "${oAuthUserSecret}" )
	private String oAuthUserSecret;

	
	Boolean firstTime=true;
	
	/*
	 * This REST service takes FRS CreateCase.json and pushes several REST calls to Polonious.
	 * 
	 * 1) Standard Create Case with Mapping. Steve is mapping the fields we create.
	 * 2) 1 or many Find Person with originalSystemId calls.
	 * 3) 1 or many Create Person /v3 which allows attributes to also be updated.
	 * 4) 1 or many Find Asset with originalSystemId calls.
	 * 5) 1 or many Create Asset /v3 which allows attributes to also be updated.
	 * 6) 1 or many Create Task Person Role links.
	 * 7) 1 or many Create Task Asset Role links.
	 * 
	 * The bit currently missing is:
	 * 
	 * 8) Create Diary + Items for the Enclosure information.
	 * 
	 */
	@PostMapping("/createCase")
	public ResponseEntity<String> createCaseAndEtc(
			//@RequestHeader MultiValueMap<String, String> header,
			@RequestBody ClaimInfo claimInfo) {
		
		CreateCaseDto createCaseDto = new CreateCaseDto();
		ResponseEntity<String> response=null;
		createCaseDto.setClaimNumber(claimInfo.getClaimNumber());
		//createCaseDto.setLossDateTime(claimInfo.getLossDateTime());
		createCaseDto.setLossDateTime("2019-03-09");
		createCaseDto.setAccidentType(claimInfo.getAccidentType());
		createCaseDto.setLossLocationState(claimInfo.getLossLocationState());
		createCaseDto.setClaimPID(claimInfo.getClaimPID());
		createCaseDto.setFcc(claimInfo.getFcc());	
		// Calc Region from fcc as code.
		createCaseDto.setRegion(getRegionFromFcc(claimInfo.getFcc()));
		//
		createCaseDto.setDescription("description");
		createCaseDto.setManagersSectionCode("Please Select");

		System.out.println("policyInfo ..");
		if (claimInfo.getPolicyInfo()!=null) {
			String policyType=claimInfo.getPolicyInfo().getPolicyType();
			if (policyType != null && policyType.length()>1)	
				policyType=policyType.substring(0, 1).toUpperCase() + policyType.substring(1).toLowerCase();
			
			createCaseDto.setPolicyType(policyType);
			createCaseDto.setPolicyNumber(claimInfo.getPolicyInfo().getPolicyNumber());
			createCaseDto.setPolicyPeriod(claimInfo.getPolicyInfo().getPolicyPeriod());
			createCaseDto.setPolicyEffectiveDate(claimInfo.getPolicyInfo().getPolicyEffectiveDate());
			createCaseDto.setPolicyResidenceState(claimInfo.getPolicyInfo().getPolicyResidenceState());
			createCaseDto.setPolicyZipCode(claimInfo.getPolicyInfo().getPolicyZipCode());
			createCaseDto.setRatedState(claimInfo.getPolicyInfo().getRatedState());
			
			createCaseDto.setDamageNotConsistentWithLossDescription(claimInfo.getDamageNotConsistentWithLossDescription());
			createCaseDto.setPossibleIntentionalAct(claimInfo.getPossibleIntentionalAct());
			createCaseDto.setNoPriorInsurance(claimInfo.getNoPriorInsurance());
			createCaseDto.setUnrelatedClaimantsOrInsuredsUseSameMedicalProvider(claimInfo.getUnrelatedClaimantsOrInsuredsUseSameMedicalProvider());
			createCaseDto.setNoReceiptForRepair(claimInfo.getNoReceiptForRepair());
			
			System.out.println("  policyType : "+claimInfo.getPolicyInfo().getPolicyType());
			System.out.println("  policyNumber : "+claimInfo.getPolicyInfo().getPolicyNumber());
			System.out.println("  policyPeriod : "+claimInfo.getPolicyInfo().getPolicyPeriod());
			System.out.println("  policyEffectiveDate : "+claimInfo.getPolicyInfo().getPolicyEffectiveDate());
			System.out.println("  insured : "+claimInfo.getPolicyInfo().getInsured());
			System.out.println("  policyResidenceState : "+claimInfo.getPolicyInfo().getPolicyResidenceState());
			System.out.println("  policyZipCode : "+claimInfo.getPolicyInfo().getPolicyZipCode());
			System.out.println("  ratedState : "+claimInfo.getPolicyInfo().getRatedState());
		}
		
		System.out.println("contactInfo ..");
		if (claimInfo.getContactInfo()!=null) {
			for (ContactInfo contactInfo: claimInfo.getContactInfo()) {
				System.out.println("  ....................");
				System.out.println("  contactPID 	: "+contactInfo.getContactPID());
				System.out.println("  firstName 	: "+contactInfo.getFirstName());
				System.out.println("  middleName 	: "+contactInfo.getMiddleName());
				System.out.println("  lastName      : "+contactInfo.getLastName());
				System.out.println("  dateOfBirth 	: "+contactInfo.getDateOfBirth());
				System.out.println("  phoneNumber 	: "+contactInfo.getPhoneNumber());
				System.out.println("  racFid 		: "+contactInfo.getRacFid());
				System.out.println("  emailAddress 	: "+contactInfo.getEmailAddress());
				
				if (contactInfo.getRoles()!=null) {
					System.out.println("  Roles ..");
					for (String role : contactInfo.getRoles()) {
						System.out.println("    "+role);	
						// If 'insured' we add their LASTNAME, Firstname to the case description.
						if (role.equalsIgnoreCase("insured")) {
							createCaseDto.setDescription(contactInfo.getLastName().toUpperCase()+", "+contactInfo.getFirstName());
						}
					}
				}

				if (contactInfo.getIncidentInfo() != null) {
					System.out.println("    incidentInfo ..");
					System.out.println("      driverPID 	: "+contactInfo.getIncidentInfo().getDriverPID());
					System.out.println("      OwnerPID 		: "+contactInfo.getIncidentInfo().getOwnerPID());
					if (contactInfo.getIncidentInfo().getAreaOfDamages()!=null) {
						System.out.println("      areaOfDamages ..");
						for  ( String area : contactInfo.getIncidentInfo().getAreaOfDamages() ) {
							System.out.println("        area : "+area);
						}
					}
					
					System.out.println("      vehiclePID 	: "+contactInfo.getIncidentInfo().getVehiclePID());
					System.out.println("      make		 	: "+contactInfo.getIncidentInfo().getMake());
					System.out.println("      year          : "+contactInfo.getIncidentInfo().getYear());
					System.out.println("      model         : "+contactInfo.getIncidentInfo().getModel());
					System.out.println("      vehicleStyle 	: "+contactInfo.getIncidentInfo().getVehicleStyle());
					System.out.println("      vehicleVIN 	: "+contactInfo.getIncidentInfo().getVehicleVIN());
					System.out.println("      incidentPID 	: "+contactInfo.getIncidentInfo().getIncidentPID());
					System.out.println("      incidentType 	: "+contactInfo.getIncidentInfo().getIncidentType());
				
					int exposureCtr=0;
					if (contactInfo.getIncidentInfo().getExposureInfo() != null) {
						System.out.println("      exposureInfo .. ");
						for (ExposureInfoLine exposureInfoLine : contactInfo.getIncidentInfo().getExposureInfo()) {
							exposureCtr++;
							
							String exposureReserveLines ="";
							
							createCaseDto.setRatedState(claimInfo.getPolicyInfo().getRatedState());
							
							System.out.println("        exposureType : "+ exposureInfoLine.getExposureType());
							if (exposureInfoLine.getExposureReserveLines() != null) {
								System.out.println("          reserveLines.. ");
								for ( String reserveLine : exposureInfoLine.getExposureReserveLines() ) {
									
									if (exposureReserveLines.equalsIgnoreCase("")) {
										exposureReserveLines=reserveLine;
									}
									else {
										exposureReserveLines=exposureReserveLines+","+reserveLine;
									}
								}
							}
							String exposureType=exposureInfoLine.getExposureType();
							if (exposureType != null && exposureType.length()>1)	
								exposureType=exposureType.substring(0, 1).toUpperCase() + exposureType.substring(1).toLowerCase();
							System.out.println("        exposureType : "+ exposureType);
							System.out.println("            exposureReserveLines : "+exposureReserveLines);
							
							if (exposureCtr==1) {
								createCaseDto.setExposureType1(exposureType);
								createCaseDto.setExposureReserveLines1(exposureReserveLines);
							}
							else if (exposureCtr==2) {
								createCaseDto.setExposureType2(exposureType);
								createCaseDto.setExposureReserveLines2(exposureReserveLines);
							}
							else if (exposureCtr==3) {
								createCaseDto.setExposureType3(exposureType);
								createCaseDto.setExposureReserveLines3(exposureReserveLines);
							}
							
						}
					}
				}
				
				if (contactInfo.getPrimaryAddress() != null) {
					System.out.println("    primaryAddress.. ");
					System.out.println("        addressLine1 : "+contactInfo.getPrimaryAddress().getAddressLine1()); 
					System.out.println("        city         : "+contactInfo.getPrimaryAddress().getCity()); 
					System.out.println("        state        : "+contactInfo.getPrimaryAddress().getState()); 
					System.out.println("        zip code     : "+contactInfo.getPrimaryAddress().getZipCode()); 
					System.out.println("        country      : "+contactInfo.getPrimaryAddress().getCountry()); 
				}
			}
			
		}

		if (claimInfo.getReferralInfo() != null) {
			
			createCaseDto.setSiuReferralPublicID(claimInfo.getReferralInfo().getSiuReferralPublicID());
			createCaseDto.setReferralStatus(claimInfo.getReferralInfo().getReferralStatus());
			createCaseDto.setReferralNumber(claimInfo.getReferralInfo().getReferralNumber());
			createCaseDto.setReferralReason(claimInfo.getReferralInfo().getReferralReason());
			createCaseDto.setReferralType(claimInfo.getReferralInfo().getReferralType());
			createCaseDto.setReferralSubject(claimInfo.getReferralInfo().getReferralSubject());
						
			System.out.println("    referral Info.. ");
			System.out.println("      siuReferralPublicID : "+claimInfo.getReferralInfo().getSiuReferralPublicID());
			System.out.println("      referralStatus      : "+claimInfo.getReferralInfo().getReferralStatus());
			System.out.println("      referralNumber      : "+claimInfo.getReferralInfo().getReferralNumber());
			System.out.println("      referralReason      : "+claimInfo.getReferralInfo().getReferralReason());
			System.out.println("      referralType        : "+claimInfo.getReferralInfo().getReferralType());
			System.out.println("      referralSubject     : "+claimInfo.getReferralInfo().getReferralSubject());
			System.out.println("      referredBy          : "+claimInfo.getReferralInfo().getReferredBy());
			System.out.println("      fileOwner           : "+claimInfo.getReferralInfo().getFileOwner());

		}

		 
		// Defaults as JSON mapping always requests orglevels.
		// This may require rules from FRS.. Can hard code for now.
		
		// Store in a hashmap, the translations cc:xxxx to personid
		HashMap<String,Long> personIds = new HashMap<String,Long>();
		HashMap<String,Long> assetIds = new HashMap<String,Long>();
		
		
		try {
			RestTemplate postCaseRestTemplate = new RestTemplate();
			
			String token = PoloniusUtil.getToken();
			
			System.out.println("Token:"+token);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer "+token);
			
			HttpEntity<CreateCaseDto> createCaseRequest = new HttpEntity<CreateCaseDto>(createCaseDto,headers);

			response = postCaseRestTemplate
						.exchange(this.pimsUrl+"task/v1/mapping/FrsCreateCaseTest",
							  		HttpMethod.POST, 
							  		createCaseRequest, 
							  		String.class);
			Long taskId=null;
			
			if (!response.getStatusCode().equals(HttpStatus.CREATED)) {
				return new ResponseEntity<String>(response.getBody(),response.getStatusCode());
			}
			else if (response.getBody()==null){
				return new ResponseEntity<String>("No taskId attribute in POST response mapping - Data Import",HttpStatus.BAD_REQUEST);
			}
			else {
				System.out.println("about to extract taskId from the response body: "+response.getBody());
				HashMap<String,String> results=convertJsonToHashMap(response.getBody());
				taskId=Long.parseLong(results.get("taskId"));
				System.out.println("taskId: "+taskId);
				if (taskId == null) {
					return new ResponseEntity<String>("No taskId extracted in POST response mapping - Data Import",HttpStatus.BAD_REQUEST);
				}
			}
			
			// Now generate all people mentioned.
			for (ContactInfo contactInfo: claimInfo.getContactInfo()) {
				String personType="";
				if (contactInfo.getRoles() !=null) {
					personType="Subject";
				} 
				else {
					personType="Staff";
				}
				
				
				Long personId=PoloniusUtil.findOrCreatePerson(
						headers,
						personType,
						contactInfo);
				
				personIds.put(contactInfo.getContactPID(), personId);
			
				if (contactInfo.getIncidentInfo() != null) {
					Long assetId=PoloniusUtil.findOrCreateAsset(
							headers,
							contactInfo.getIncidentInfo());
					
					assetIds.put(contactInfo.getContactPID(), assetId);
					// If there is an incident, join it to the task as an asset.
					PoloniusUtil.createTaskAssetLink(
							headers,
							taskId,
							contactInfo.getIncidentInfo().getIncidentType(),
							assetId
							);
					
				}
			}
			// Now we have all assets, persons. We loop back through and link it all up.
			for (ContactInfo contactInfo: claimInfo.getContactInfo()) {
				Long personId=personIds.get(contactInfo.getContactPID());
				if (contactInfo.getRoles()!=null) {
					System.out.println("  Roles ..");
					for (String role : contactInfo.getRoles()) {
						if (role.equalsIgnoreCase("insured") || role.equalsIgnoreCase("passenger") 
								|| role.equalsIgnoreCase("driver")) 
							role=role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase();

						System.out.println("    "+role);	
						// Create a link to the task from the person for each role.
						
						PoloniusUtil.createTaskPersonLink(
								headers,
								taskId,
								role,
								personId
								);
						
					}
				}
			}
			// Also get
			if (claimInfo.getReferralInfo() != null) {
				Long personId=personIds.get(claimInfo.getReferralInfo().getReferredBy());
				PoloniusUtil.createTaskPersonLink(
						headers,
						taskId,
						"Referred By",
						personId
						);
				personId=personIds.get(claimInfo.getReferralInfo().getFileOwner());
				PoloniusUtil.createTaskPersonLink(
						headers,
						taskId,
						"File Owner",
						personId
						);
			}
		}
		catch (HttpClientErrorException e) {
			System.out.println(e.getRawStatusCode()+"- HTTP Status Error with Task REST Call- failed with: "+e.getResponseBodyAsString());
			new ResponseEntity<String>(e.getResponseBodyAsString(),  e.getStatusCode());
		}
		catch (HttpServerErrorException e) {
			System.out.println(e.getRawStatusCode()+"- HTTP Status Error with Task REST Call- failed with: "+e.getResponseBodyAsString());
			new ResponseEntity<String>(e.getResponseBodyAsString(), e.getStatusCode());
		}
		catch (Exception e) {
			System.out.println("The Create Case failed with an exception: "+e.toString());
			return new ResponseEntity<String>("The Create Case failed with a BAD REQUEST", HttpStatus.BAD_REQUEST);
		}
		
		// Return response from create case call.
		if(response != null && response.getBody( )!= null)
			System.out.println("response sending:"+response.getBody());
		return response;

	}
//	public Long findOrCreatePerson(
//			MultiValueMap<String, String> header,
//			String personTypeDescription,
//			ContactInfo contactInfo
//			) throws Exception{
//
//		Long id=null;
//
//		try {
//
//			//Long personId = getPersonForOriginalSystemId(header, contactInfo.getContactPID());
//
//			RestTemplate findPersonRestTemplate = new RestTemplate();
//
//			HttpEntity<?> entity = new HttpEntity<>(header);
//
//			System.out.println("contactInfo.getContactPID(): "+contactInfo.getContactPID());
//
//			String url=this.pimsUrl+"person/v2/firstId";
//
//			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
//			        .queryParam("originalSystemId", contactInfo.getContactPID());
//
//			ResponseEntity<Long> personResponse =
//					findPersonRestTemplate.exchange(
//			    		   builder.toUriString(),
//			    	       HttpMethod.GET,
//			    	       entity,
//			    		   Long.class);
//
//			System.out.println("Response: Person Search: personResponse.getBody()"+personResponse.getBody()
//							  +" StatusCode: "+personResponse.getStatusCodeValue()
//							  +" has Body:"+personResponse.hasBody());
//
//			if (personResponse.getBody() != null && personResponse.getBody() != -1) {
//				return personResponse.getBody();
//			}
//			else {
//				// Create a person as none found.
//				HashMap<String,String> personCreate= new HashMap<String,String>();
//				personCreate.put("personTypeDescription", personTypeDescription);
//				personCreate.put("originalSystemId", contactInfo.getContactPID());
//				personCreate.put("firstName", contactInfo.getFirstName());
//				personCreate.put("middleNames", contactInfo.getMiddleName());
//				personCreate.put("lastName", contactInfo.getLastName());
//				personCreate.put("phoneNumber", contactInfo.getPhoneNumber());
//				personCreate.put("email", contactInfo.getEmailAddress());
//				personCreate.put("dob", contactInfo.getDateOfBirth());
//
//				if (contactInfo.getPrimaryAddress() != null) {
//					personCreate.put("addressLine1", contactInfo.getPrimaryAddress().getAddressLine1());
//					personCreate.put("city", contactInfo.getPrimaryAddress().getCity());
//					personCreate.put("state", contactInfo.getPrimaryAddress().getState());
//					personCreate.put("zipCode", contactInfo.getPrimaryAddress().getZipCode());
//					personCreate.put("country", contactInfo.getPrimaryAddress().getCountry());
//				}
//				HttpEntity<HashMap<String,String>> createPersonRequest = new HttpEntity<HashMap<String,String>>(personCreate,header);
//				RestTemplate postPersonRestTemplate = new RestTemplate();
//				ResponseEntity<String> response = postPersonRestTemplate
//						.exchange(this.pimsUrl+"person/v3",
//							  		HttpMethod.POST,
//							  		createPersonRequest,
//							  		String.class);
//				//System.out.println(">>Response: "+response);
//				String body=response.getBody();
//				id=Long.parseLong(body);
//				System.out.println("created PersonId: "+id);
//				return id;
//			}
//		}
//		catch (HttpClientErrorException e) {
//			System.out.println(e.getRawStatusCode()+"- HTTP Status Error with Person REST Call- failed with: "+e.getResponseBodyAsString());
//			return new Long(-1);
//		}
//		catch (HttpServerErrorException e) {
//			System.out.println(e.getRawStatusCode()+"- HTTP Status Error with Person REST Call- failed with: "+e.getResponseBodyAsString());
//			return new Long(-1);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Problem with Person REST calls - failed with an exception: "+e.toString());
//			return new Long(-1);
//		}
//
//	}

//	public Long getPersonForOriginalSystemId (
//			MultiValueMap<String, String> header,
//			String osd) {
//
//		  try {
//			String url=this.pimsUrl+"person/v2/firstId";
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//			HttpGet getRequest = new HttpGet(url);
//			for (String key: header.keySet()) {
//				getRequest.addHeader(key, header.getFirst(key));
//			}
//			HttpResponse response = httpClient.execute(getRequest);
//
//			if (response.getStatusLine().getStatusCode() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//				   + response.getStatusLine().getStatusCode());
//			}
//
//			BufferedReader br = new BufferedReader(
//	                         new InputStreamReader((response.getEntity().getContent())));
//
//			String output="";
//			System.out.println("Output from Server .... \n");
//			while ((output = br.readLine()) != null) {
//				System.out.println(output);
//				
//			}
//			httpClient.getConnectionManager().shutdown();
//			return Long.parseLong(output);
//			
//		  } catch (ClientProtocolException e) {
//		
//			e.printStackTrace();
//
//		  } catch (IOException e) {
//		
//			e.printStackTrace();
//		  }
//
//		return new Long (-1);
//	}
	

	public String getRegionFromFcc (String fcc) {
		
		switch(fcc) 
        { 
            case "02": 
                return "Region 3";
            case "03": 
            	return "Region 4";
            case "04": 
                return "Region 2";
            case "05": 
            	return "Region 1";
            case "06": 
                return "Region 7";
            case "07": 
            	return "Region 4";
            case "08": 
                return "Region 6";
            case "09": 
            	return "Region 5";
            case "10": 
                return "Region 9";
            case "11": 
            	return "Region 10";
            case "12": 
                return "Region 8";
            case "13": 
            	return "Houston Claims Center";
            case "14": 
                return "Region 10";
            case "15": 
            	return "Indianapolis Claims Center";
            case "TBD": 
                return "Indianapolis Claims Center";            	
            default: 
                return "Region 1"; 
        }
	}
	public HashMap<String,String> convertJsonToHashMap (String json) {
		HashMap<String, String> map = new HashMap<String, String>();
        
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            //Convert Map to JSON
            map = (HashMap<String, String>) mapper.readValue(json, new TypeReference<Map<String, String>>(){});
            return map;
        }
        catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}

}
