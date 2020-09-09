package au.com.polonious.integration.utils;

import au.com.polonious.integration.dtos.frissDto.objectDto.*;
import au.com.polonious.integration.dtos.frissDto.FrsPayloadDTO;
import au.com.polonious.integration.dtos.frissDto.Node;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FrissUtil {
    private static final int OBJECT_TYPE_CLAIM = 9;
    private static final int OBJECT_TYPE_ADDRESS = 1;

    private static ObjectMapper mapper;

    static{
        mapper = new ObjectMapper();
    }

    /**
     * Returns list of OBJECT_TYPE=9 (claim) object in the payload tree
     * @param payloadDTO
     * @return
     */
    public static List<ClaimObject> getClaimObjects(FrsPayloadDTO payloadDTO){
        List<Object> frissClaimObjects = new ArrayList<>();
        payloadDTO.getRelatedObject().getNodes().stream().
                forEach(c -> frissClaimObjects.addAll(findObjectsWithType(c, ObjectType.Claim, null)));
        return frissClaimObjects.stream().map(c -> (ClaimObject)c).collect(Collectors.toList());
    }
    public static List<AddressObject> getAddressObjects(FrsPayloadDTO payloadDTO){
        List<Object> addressObjects = new ArrayList<>();
        payloadDTO.getRelatedObject().getNodes().stream().
                forEach(c -> addressObjects.addAll(findObjectsWithType(c,ObjectType.Address, null)));
        return addressObjects.stream().map(c -> (AddressObject)c).collect(Collectors.toList());
    }
    public static List<PersonObject> getPersonObjects(FrsPayloadDTO payloadDTO) {
        List<Object> addressObjects = new ArrayList<>();
        payloadDTO.getRelatedObject().getNodes().stream().
                forEach(c -> addressObjects.addAll(findObjectsWithType(c,ObjectType.NaturalPerson, null)));
        return addressObjects.stream().map(c -> (PersonObject)c).collect(Collectors.toList());
    }
    public static List<PolicyObject> getPolicyObjects(FrsPayloadDTO payload) {
        List<Object> policyObjects = new ArrayList<>();
        payload.getRelatedObject().getNodes().stream().
                forEach(c -> policyObjects.addAll(findObjectsWithType(c,ObjectType.Policy, null)));
        return policyObjects.stream().map(c -> (PolicyObject)c).collect(Collectors.toList());
    }
    public static List<PhoneObject> getPhoneObjects(FrsPayloadDTO payload) {
        List<Object> phoneObjects = new ArrayList<>();
        payload.getRelatedObject().getNodes().stream().
                forEach(c -> phoneObjects.addAll(findObjectsWithType(c,ObjectType.Contact, null)));
        return phoneObjects.stream().map(c -> (PhoneObject)c).collect(Collectors.toList());
    }
    public static List<VehicleObject> getVehicleObjects(FrsPayloadDTO payload) {
        List<Object> vehicleObjects = new ArrayList<>();
        payload.getRelatedObject().getNodes().stream().
                forEach(c -> vehicleObjects.addAll(findObjectsWithType(c,ObjectType.Vehicle, null)));
        return vehicleObjects.stream().map(c -> (VehicleObject)c).collect(Collectors.toList());
    }
    public static List<LicenseIdObject> getLicenseObjects(FrsPayloadDTO payload) {
        List<Object> licenseObjects = new ArrayList<>();
        payload.getRelatedObject().getNodes().stream().
                forEach(c -> licenseObjects.addAll(findObjectsWithType(c,ObjectType.IdentityCard, null)));
        return licenseObjects.stream().map(c -> (LicenseIdObject)c).collect(Collectors.toList());
    }

    /**
     * Find all objects of given type by traversing recursively. If not specified, it returns the list of all objects
     * converted to Pojo, by their ObjectType
     * @param node
     * @param restrictToType
     * @param returnList
     * @return
     */
    public static List<Object> findObjectsWithType(Node node, ObjectType restrictToType, List<Object> returnList){
        List<Object> effectivelyFinalList = (returnList == null)? new ArrayList<>(): returnList;

        GenericNodeObject nodeObject = FrissUtil.getObjectWithType(node.getObject(), restrictToType);
        if (nodeObject != null){
            nodeObject.parent = node;
            effectivelyFinalList.add(nodeObject);
        }

        node.getNodes().stream().forEach(c -> findObjectsWithType(c, restrictToType, effectivelyFinalList));
        return effectivelyFinalList;
    }
    /**
     * Given a linked hashmap object (pojo version of json) the function returns the corresponding
     * pojo as recognized by ObjectType. If restrectToType flag is specified, then returns value only
     * if belonging to the specified type.
     * @param object
     * @param retrictToType
     * @return
     */
    public static GenericNodeObject getObjectWithType(Object object, ObjectType retrictToType){
        if (object instanceof LinkedHashMap){
            LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) object;
            if (linkedHashMap.containsKey("ObjectType")){
                int objectTypeId = (int) linkedHashMap.get("ObjectType");
                ObjectType objectType = ObjectType.fromInteger(objectTypeId);

                // If restrictToType flag is specified, then return only if belonging to that type
                if (retrictToType != null && !retrictToType.equals(objectType)) return null;

                switch (objectType){
                    case Claim: {
                        return getCustomObject(linkedHashMap, ClaimObject.class);
                    }
                    case Address: {
                        return getCustomObject(linkedHashMap, AddressObject.class);
                    }
                    case Policy: {
                        return getCustomObject(linkedHashMap, PolicyObject.class);
                    }
                    case Contact: {
                        return getCustomObject(linkedHashMap, PhoneObject.class);
                    }
                    case NaturalPerson: {
                        return getCustomObject(linkedHashMap, PersonObject.class);
                    }
                    case Vehicle: {
                        return getCustomObject(linkedHashMap, VehicleObject.class);
                    }
                    case IdentityCard: {
                        return getCustomObject(linkedHashMap, LicenseIdObject.class);
                    }
                }
            }
        }
        return null;
    }

    public static <T extends GenericNodeObject> T getCustomObject(LinkedHashMap<String, Object> linkedHashMap, Class<T> clazz) {
        T frissClaimObject = mapper.convertValue(linkedHashMap, clazz);
        frissClaimObject.setEnum();
        return frissClaimObject;
    }


}
