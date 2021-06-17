package com.golan.local.dataflow.controllers;


import com.golan.local.dataflow.data.DateGenerator;
import com.golan.local.dataflow.data.Dfql;
import com.golan.local.dataflow.data.Env;
import com.golan.local.dataflow.data.Fleet;
import com.golan.local.dataflow.data.OrgProj;
import com.golan.local.dataflow.data.OrgProjEnv;
import com.golan.local.dataflow.data.WhiteRaven;
import com.golan.local.dataflow.json.Meta;
import com.golan.local.dataflow.json.Paging;
import com.golan.local.dataflow.json.iam.organizations.Organization;
import com.golan.local.dataflow.json.orchestration.projects.Project;
import com.golan.local.dataflow.json.orchestration.spec.ProjectSpec;
import com.golan.local.dataflow.json.registry.Attribute;
import com.golan.local.dataflow.json.registry.CustomDateSerializer;
import com.golan.local.dataflow.json.registry.DataType;
import com.golan.local.dataflow.json.registry.MultiRelationshipResponse;
import com.golan.local.dataflow.json.registry.ObjectCount;
import com.golan.local.dataflow.json.registry.ObjectDetails;
import com.golan.local.dataflow.json.registry.ObjectUuidResponse;
import com.golan.local.dataflow.json.registry.ObjectsResponse;
import com.golan.local.dataflow.json.registry.RegObject;
import com.golan.local.dataflow.json.registry.RegRelation;
import com.golan.local.dataflow.json.registry.RelationshipDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
@RequestMapping("/v1/objects")
@RestController
@Slf4j
public class RegistryController {
    private static final String CLASS_VEHICLE = "vehicle";
    private static final String CLASS_ERROR = "error";
    private static final String CLASS_COW = "cow";
    private static final String ENV_UUID = "envUuid";
    private static final String CLASS_NAME = "className";
    private static final String OBJECT_ID = "objectId";
    private static final String RELATIONSHIP = "relationshipName";
    private static final String HAS_MANY = "hasMany";

    private static final DateGenerator ATTR_DATE = new DateGenerator(1262304000000L);                   // 2010-01-01 00:00:00
    private static final DateGenerator ATTR_TIMESTAMP = new DateGenerator(1293840000000L);              // 2011-01-01 00:00:00
    private static final ProjectSpec.ClassSpec FLEET_TRACKER_SPEC = Fleet.getAllClasses(Fleet.OPE_DEV).get(Fleet.C_TRACKER);

    private final OrchestrationController orchestrationController;
    private final OrcDataGenerator orcData;

    private final Map<UUID, Map<String, ObjectCount>> objectCountPerClassPerEnvUuid;        //env_uuid  =>   class_name  ==>  object_count
    private final Map<String, List<RegObject>> registryObjectsPerEnvironment;               //env_uuid  =>   registry_object

    public RegistryController(OrchestrationController orchestrationController, OrcDataGenerator orcData) {
        this.orchestrationController = orchestrationController;
        this.orcData = orcData;
        this.objectCountPerClassPerEnvUuid = initDfqlObjectCountPerClass();
        this.registryObjectsPerEnvironment = createObjectsForAllPossibleEnvironments();
    }

    private Map<String, List<RegObject>> createObjectsForAllPossibleEnvironments() {
        assert orchestrationController != null;
        final List<Organization> organizations = orcData.getOrganizationsList();
        final List<Project> projects = flatProjects(organizations).collect(Collectors.toList());
        final List<OrgProjEnv> opes = flatEnvironments(projects).collect(Collectors.toList());
        final List<String> envUuids = opes.stream().map(this::convertToEnvUuid).collect(Collectors.toList());
        return envUuids.stream().collect(Collectors.toMap(Function.identity(), this::generateObjectsList));
    }

    private Stream<Project> flatProjects(List<Organization> organizations) {
        return organizations.stream().flatMap(organization -> orcData.getProjectsForOrg(organization.getIdentifier()).stream());
    }

    private Stream<OrgProjEnv> flatEnvironments(List<Project> projects) {
        return projects.stream().flatMap(project ->
                orcData
                        .getEnvironmentsForProject()
                        .stream()
                        .map(e -> new OrgProjEnv(project.getOrganization(), project.getIdentifier(), e.getIdentifier())));
    }

    private String convertToEnvUuid(OrgProjEnv ope) {
        return orcData.convertToEnvUUID(ope.getOrg(), ope.getProject() + "~" + ope.getEnv()).toString();
    }

    private static Map<UUID, Map<String, ObjectCount>> initDfqlObjectCountPerClass() {
        final Map<UUID, Map<String, ObjectCount>> result = new HashMap<>();
        final HashMap<String, ObjectCount> orgProj = new HashMap<>();
        orgProj.put(CLASS_VEHICLE, new ObjectCount(CLASS_VEHICLE, 12, null));
        orgProj.put(CLASS_ERROR, new ObjectCount(CLASS_ERROR, 9, null));
        result.put(UUID.fromString(Dfql.ID_ORG_PROJ), orgProj);
        result.put(UUID.fromString(Dfql.ID_NES_COWS), Collections.singletonMap(CLASS_COW, new ObjectCount(CLASS_COW, 7, null)));
        result.put(UUID.fromString(Dfql.ID_NO_CLASSES), Collections.emptyMap());

        return result;
    }

    @SuppressWarnings({"unused"})
    @GetMapping(value = "/_/~{" + ENV_UUID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectsResponse getObjectsOfEnvUuid(
            @PathVariable(ENV_UUID) String envUuid,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor) {

        final List<RegObject> allObjects = registryObjectsPerEnvironment.get(envUuid);
        return resolveBulk(allObjects, limit, cursor);
    }

    @SuppressWarnings("unused")
    @GetMapping(value = "/{org}/{proj}~{env}/{" + CLASS_NAME + "}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectsResponse getObjectsOfClass(
            @PathVariable("org") String org,
            @PathVariable("proj") String proj,
            @PathVariable("env") String env,
            @PathVariable(CLASS_NAME) String className,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor) throws ProjectDoesNotExistsException {

        final OrgProj orgProj = new OrgProj(org, proj);
        final List<RegObject> allObjects;
        if (WhiteRaven.match(orgProj, env)) {
            allObjects = WhiteRaven.getObjectsOfClass(orgProj, env, className);
        }
        else if (Fleet.match(orgProj, env)) {
            allObjects = Fleet.getObjectsOfClass(orgProj, env, className);
        }
        else {
            throw new ProjectDoesNotExistsException(new OrgProjEnv(orgProj));
        }
        return resolveBulk(allObjects, limit, cursor);
    }

    @GetMapping(value = "/{org}/{proj}~{env}/{" + CLASS_NAME + "}/{" + OBJECT_ID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectDetails getObjectDetails(
            @PathVariable("org") String org,
            @PathVariable("proj") String proj,
            @PathVariable("env") String env,
            @PathVariable(CLASS_NAME) String className,
            @PathVariable(OBJECT_ID) String objectId) throws RejectException, ParseException {
        final String envUuid = convertToEnvUuid(new OrgProjEnv(org, proj, env));
        return getObjectDetails(envUuid, className, objectId);
    }

    @GetMapping(value = "/_/~{" + ENV_UUID + "}/{" + CLASS_NAME + "}/{" + OBJECT_ID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectDetails getObjectDetails(
            @PathVariable(ENV_UUID) String envUuid,
            @PathVariable(CLASS_NAME) String className,
            @PathVariable(OBJECT_ID) String objectId) throws RejectException, ParseException {

        final RegObject regObject = findGeneratedObject(envUuid, className, objectId);
        return convertToObjectDetails(regObject);
    }

    @GetMapping(value = "/_/~{" + ENV_UUID + "}/{" + CLASS_NAME + "}/{" + OBJECT_ID + "}/_uuid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectUuidResponse getObjectUuid(@PathVariable(ENV_UUID) String envUuid,
                                            @PathVariable(CLASS_NAME) String className,
                                            @PathVariable(OBJECT_ID) String objectId) throws RejectException, IOException {
        final RegObject regObject = findGeneratedObject(envUuid, className, objectId);
        final Env env = orcData.findEnvByUuid(envUuid);
        final ProjectSpec projectSpec = orcData.getLatestCompiledSpec(env);
        final ProjectSpec.ClassSpec classSpec = projectSpec.getClasses().get(className);
        if (classSpec == null) {
            throw new RejectException(HttpStatus.NOT_FOUND, "Invalid class for project.");
        }
        return new ObjectUuidResponse(classSpec.getClassUuid().toString(), regObject.getObjectUuid().toString());
    }

    @SuppressWarnings("unused")
    @GetMapping(value = "/_/~{" + ENV_UUID + "}/_count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ObjectCount> countObjects(@PathVariable(ENV_UUID) String envUuid) {
        log.debug("[countObjects] " + ENV_UUID + "={}", envUuid);
        final Map<String, ObjectCount> objectCountPerClass = this.objectCountPerClassPerEnvUuid.get(UUID.fromString(envUuid));
        if (objectCountPerClass == null) {
            throw new IllegalArgumentException("No such env: " + envUuid);
        } else if (objectCountPerClass.isEmpty()) {
            return Collections.emptyList();
        } else {
            return objectCountPerClass.values();
        }
    }

    @SuppressWarnings("unused")
    @GetMapping(value = "/_/~{" + ENV_UUID + "}/{" + CLASS_NAME + "}/_count", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ObjectCount> countObjects(@PathVariable(ENV_UUID) String envUuid, @PathVariable("class") String className) {
        log.debug("[countObjects] " + ENV_UUID + "={}", envUuid);
        final Map<String, ObjectCount> objectCountPerClass = this.objectCountPerClassPerEnvUuid.get(UUID.fromString(envUuid));
        if (objectCountPerClass == null) {
            throw new IllegalArgumentException("No such env: " + envUuid);
        } else if (objectCountPerClass.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(objectCountPerClass.get(className));
        }
    }

    @GetMapping(value = "/_/~{" + ENV_UUID + "}/{" + CLASS_NAME + "}/{" + OBJECT_ID + "}/r/{targetClassName}/{targetObjectId}")
    public RelationshipDetails getRelationship(@PathVariable(ENV_UUID) String envUuid,
                                                     @PathVariable(CLASS_NAME) String sourceClassName,
                                                     @PathVariable(OBJECT_ID) String sourceObjectId,
                                                     @PathVariable("targetClassName") String targetClassName,
                                                     @PathVariable("targetObjectId") String targetObjectId) throws RejectException {
        final MultiRelationshipResponse related = getObjectsRelatedToObject(envUuid, sourceClassName, sourceObjectId, targetClassName);
        final List<RelationshipDetails> objects = related.getObjects();
        final Optional<RelationshipDetails> existing = objects
                .stream()
                .filter(o -> o.getObjectId().equals(targetObjectId))
                .findFirst();
        if (existing.isPresent()) {
            return existing.get();
        }
        else {
            throw new RejectException(HttpStatus.NOT_FOUND, "Invalid referent object identifier.");
        }
    }

    @GetMapping(value = "/_/~{" + ENV_UUID + "}/{" + CLASS_NAME + "}/{" + OBJECT_ID + "}/r/{"+ RELATIONSHIP+"}")
    public MultiRelationshipResponse getObjectsRelatedToObject(@PathVariable(ENV_UUID) String envUuid,
                                                               @PathVariable(CLASS_NAME) String sourceClassName,
                                                               @PathVariable(OBJECT_ID) String sourceObjectId,
                                                               @PathVariable(RELATIONSHIP) String relationshipName) throws RejectException {
        if (log.isTraceEnabled()) log.trace("get-relationship envUuid=["+envUuid+"] sourceClassName=["+sourceClassName+"] sourceObjectId=["+sourceObjectId+"] relationshipName=["+relationshipName+"]");
        final Env fleet = Fleet.findEnvironment(UUID.fromString(envUuid));
        if (fleet != null) {
            final List<RegRelation> relationships = Fleet.getRelationsOfObject(fleet.getEnv(), sourceClassName, sourceObjectId);
            final List<RelationshipDetails> objects = relationships
                    .stream()
                    .map(RegRelation::getDestination)
                    .map(RelationshipDetails::new)
                    .collect(Collectors.toList());
            if (objects.isEmpty()) {
                //for some dumb reason this is how RegV2 works ...
                throw new RejectException(HttpStatus.NOT_FOUND, "Relationship does not exist.");
            }
            return new MultiRelationshipResponse(new Meta("", "", 100, null), objects, HAS_MANY, relationshipName);
        }
        else {
            throw new ProjectDoesNotExistsException(new OrgProjEnv(envUuid));
        }
    }

    private ObjectDetails convertToObjectDetails(RegObject regObject) throws ParseException {
        return new ObjectDetails()
                .setObjectId(regObject.getIdentifier())
                .setClassName(regObject.getClassName())
                .setCreatedAt(regObject.getCreatedAtAsDate())
                .setAttributes(regObject.getAttributes())
                .setDescription(regObject.getDescription());
    }

    private List<RegObject> generateObjectsList(@PathVariable(ENV_UUID) String envUuid) {
        final UUID envUUID = UUID.fromString(envUuid);
        final Env whiteRaven = WhiteRaven.findEnvironment(envUUID);
        final Env fleet = Fleet.findEnvironment(envUUID);

        if (whiteRaven != null) {
            return WhiteRaven
                    .getAllClasses(whiteRaven)
                    .values()
                    .stream()
                    .flatMap(clazz -> WhiteRaven.getObjectsOfClass(whiteRaven, clazz.getName()).stream())
                    .collect(Collectors.toList());
        }

        if (fleet != null) {
            return Fleet
                    .getAllClasses(fleet)
                    .values()
                    .stream()
                    .flatMap(clazz -> Fleet.getObjectsOfClass(fleet, clazz.getName()).stream())
                    .map(this::enrichWithFleetAttributes)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private RegObject enrichWithFleetAttributes(RegObject regObject) {
        if ("tracker".equals(regObject.getClassName())) {
            regObject.setAttributes(createTrackerAttributes(regObject));
        }
        else {
            regObject.setAttributes(Collections.emptyMap());
        }
        return regObject;
    }

    private Map<String, Attribute> createTrackerAttributes(RegObject regObject) {
        return Map.of(
                "izi", new Attribute().setType(DataType.TEXT),
                "geotabid", createAttribute("geotabid", regObject.getIdentifier()),
                "oldGeotabId", createEmptyAttribute("oldGeotabId"),
                "tech_data", createAttribute("tech_data", regObject.getClassName() + "|" + regObject.getCreatedAt()),
                "vin_data", createEmptyAttribute("vin_data"),
                "random", createAttribute("random", regObject.hashCode()),
                "retire_date", createAttribute("retire_date", new CustomDateSerializer().format(ATTR_TIMESTAMP.next())),
                "oldVid", createAttribute("oldVid", null),
                "surplus_date", createEmptyAttribute("surplus_date"),
                "depot_date", createEmptyAttribute("depot_date")
        );
    }

    private RegObject findGeneratedObject(String envUuid, String className, String objectId) throws RejectException {
        return registryObjectsPerEnvironment.get(envUuid)
                .stream()
                .filter(o -> o.getClassName().equals(className) && o.getIdentifier().equals(objectId))
                .findFirst()
                .orElseThrow(() -> new RejectException(HttpStatus.NOT_FOUND, "DataflowObject does not exist."));
    }

    private Attribute createEmptyAttribute(String name) {
        try {
            final Attribute spec = FLEET_TRACKER_SPEC.getAttributes().get(name);
            return new Attribute()
                    .setName(spec.getName())
                    .setUuid(spec.getUuid())
                    .setType(spec.getType())
                    .setComment(spec.getComment());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create attribute: " + name, e);
        }
    }

    private Attribute createAttribute(String name, final Object value) {
        final Date d = ATTR_DATE.next();
        return createEmptyAttribute(name)
                .setValue(value)
                .setCreatedAt(d)
                .setUpdatedAt(plusOneDay(d));
    }

    private Date plusOneDay(Date d) {
        return new Date(d.getTime() + 1000 * 60 * 60 * 24);
    }

    private ObjectsResponse resolveBulk(List<RegObject> allObjects, int limit, String cursor) {
        final int startIndex = Paging.startIndex(cursor);
        final int lastIndex = Paging.lastIndex(startIndex, limit, allObjects.size());
        final List<RegObject> thisBulk = Paging.thisBulk(allObjects, startIndex, lastIndex);
        final Meta meta = Paging.meta(limit, cursor, allObjects.size(), lastIndex);

        final ObjectsResponse objectsResponse = new ObjectsResponse();
        objectsResponse.setMeta(meta);
        objectsResponse.setObjects(thisBulk);
        return objectsResponse;
    }
}
