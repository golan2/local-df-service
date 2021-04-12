package com.golan.local.dataflow.controllers;


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
import com.golan.local.dataflow.json.registry.ObjectCount;
import com.golan.local.dataflow.json.registry.ObjectDetails;
import com.golan.local.dataflow.json.registry.ObjectUuidResponse;
import com.golan.local.dataflow.json.registry.ObjectsResponse;
import com.golan.local.dataflow.json.registry.RegObject;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final OrchestrationController orchestrationController;
    private final OrcDataGenerator orcData;

    private final Map<UUID, Map<String, ObjectCount>> objectCountPerClass;      //env_uuid  =>   class_name  ==>  object_count
    private final Map<String, List<RegObject>> registryObjectsPerEnvironment;   //env_uuid  =>   registry_object

    public RegistryController(OrchestrationController orchestrationController, OrcDataGenerator orcData) {
        this.orchestrationController = orchestrationController;
        this.orcData = orcData;
        this.objectCountPerClass = initDfqlObjectCountPerClass();
        this.registryObjectsPerEnvironment = createObjectsForAllPossibleEnvironments();
    }

    private Map<String, List<RegObject>> createObjectsForAllPossibleEnvironments() {
        final HashMap<UUID, List<RegObject>> map = new HashMap<>();
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
        try {
            return orcData.convertToEnvUUID(ope.getOrg(), ope.getProject() + "~" + ope.getEnv()).toString();
        } catch (RejectException e) {
            throw new IllegalArgumentException("Failed to convert ope to EnvUuid.   ope: " + ope, e);
        }
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
    @GetMapping(value = "/_/~{envUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectsResponse getObjectsOfEnvUuid(
            @PathVariable("envUuid") String envUuid,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor) {

        final List<RegObject> allObjects = registryObjectsPerEnvironment.get(envUuid);
        return resolveBulk(allObjects, limit, cursor);
    }

    @SuppressWarnings("unused")
    @GetMapping(value = "/{org}/{proj}~{env}/{className}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectsResponse getObjectsOfClass(
            @PathVariable("org") String org,
            @PathVariable("proj") String proj,
            @PathVariable("env") String env,
            @PathVariable("className") String className,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor) {

        final OrgProj orgProj = new OrgProj(org, proj);
        final List<RegObject> allObjects;
        if (WhiteRaven.match(orgProj, env)) {
            allObjects = WhiteRaven.getObjectsOfClass(orgProj, env, className);
        }
        else {
            allObjects = Collections.emptyList();
        }
        return resolveBulk(allObjects, limit, cursor);
    }

    @GetMapping(value = "/_/~{envUuid}/{className}/{objectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectDetails getObjectDetails(
            @PathVariable("envUuid") String envUuid,
            @PathVariable("className") String className,
            @PathVariable("objectId") String objectId) throws RejectException, ParseException {

        final RegObject regObject = findGeneratedObject(envUuid, className, objectId);
        return convertToObjectDetails(regObject);
    }

    @GetMapping(value = "/_/~{envUuid}/{className}/{objectId}/_uuid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectUuidResponse getObjectUuid(@PathVariable("envUuid") String envUuid,
                                            @PathVariable("className") String className,
                                            @PathVariable("objectId") String objectId) throws RejectException, IOException {
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
    @GetMapping(value = "/_/~{envUuid}/_count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ObjectCount> countObjects(@PathVariable("envUuid") String envUuid) {
        log.debug("[countObjects] envUuid={}", envUuid);
        final Map<String, ObjectCount> objectCountPerClass = this.objectCountPerClass.get(UUID.fromString(envUuid));
        if (objectCountPerClass == null) {
            throw new IllegalArgumentException("No such env: " + envUuid);
        } else if (objectCountPerClass.isEmpty()) {
            return Collections.emptyList();
        } else {
            return objectCountPerClass.values();
        }
    }

    @SuppressWarnings("unused")
    @GetMapping(value = "/_/~{envUuid}/{class}/_count", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ObjectCount> countObjects(@PathVariable("envUuid") String envUuid, @PathVariable("class") String className) {
        log.debug("[countObjects] envUuid={}", envUuid);
        final Map<String, ObjectCount> objectCountPerClass = this.objectCountPerClass.get(UUID.fromString(envUuid));
        if (objectCountPerClass == null) {
            throw new IllegalArgumentException("No such env: " + envUuid);
        } else if (objectCountPerClass.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(objectCountPerClass.get(className));
        }
    }





    private ObjectDetails convertToObjectDetails(RegObject regObject) throws ParseException {
        return new ObjectDetails()
                .setObjectId(regObject.getIdentifier())
                .setClassName(regObject.getClassName())
                .setCreatedAt(regObject.getCreatedAtAsDate())
                .setAttributes(createAttributes(regObject))
                .setDescription(regObject.getDescription());
    }

    private List<RegObject> generateObjectsList(@PathVariable("envUuid") String envUuid) {
        final UUID envUUID = UUID.fromString(envUuid);
        final Env whiteReaven = WhiteRaven.findEnvironment(envUUID);
        final Env fleet = Fleet.findEnvironment(envUUID);

        if (whiteReaven != null) {
            return WhiteRaven
                    .getAllClasses(whiteReaven)
                    .values()
                    .stream()
                    .flatMap(clazz -> WhiteRaven.getObjectsOfClass(whiteReaven, clazz.getName()).stream())
                    .collect(Collectors.toList());
        }

        if (fleet != null) {
            return Fleet
                    .getAllClasses(fleet)
                    .values()
                    .stream()
                    .flatMap(clazz -> Fleet.getObjectsOfClass(fleet, clazz.getName()).stream())
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private RegObject findGeneratedObject(@PathVariable("envUuid") String envUuid, @PathVariable("className") String className, @PathVariable("objectId") String objectId) throws RejectException {
        return registryObjectsPerEnvironment.get(envUuid)
                .stream()
                .filter(o -> o.getClassName().equals(className) && o.getIdentifier().equals(objectId))
                .findFirst()
                .orElseThrow(() -> new RejectException(HttpStatus.NOT_FOUND, "DataflowObject does not exist."));
    }

    private Map<String, Attribute> createAttributes(RegObject regObject) {
        return Map.of(
                "simulateEmptyAttribute", createAttribute("", null),
                "name", createAttribute("name", regObject.getIdentifier()),
                "stam", createAttribute("stam", regObject.getClassName() + "|" + regObject.getCreatedAt()),
                "hashCode", createAttribute("hashCode", regObject.hashCode())
        );
    }

    private Attribute createAttribute(String name, final Object value) {
        return new Attribute().setName(name).setValue(value).setCreatedAt(new Date());
    }

    private ObjectsResponse resolveBulk(List<RegObject> allObjects, int limit, String cursor) {
        final ArrayList<RegObject> objects = new ArrayList<>(limit);

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
