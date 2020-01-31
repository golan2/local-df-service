package com.golan.hello.spring.boot.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.golan.hello.spring.iam.organizations.OrganizationsResponse;
import com.golan.hello.spring.orchestration.environments.Environment;
import com.golan.hello.spring.orchestration.environments.EnvironmentsResponse;
import com.golan.hello.spring.orchestration.projects.Project;
import com.golan.hello.spring.orchestration.projects.ProjectsResponse;
import com.golan.hello.spring.orchestration.spec.UuidResponse;
import com.golan.hello.spring.registry.Meta;
import com.golan.hello.spring.registry.ObjectCount;
import com.golan.hello.spring.registry.ObjectsResponse;
import com.golan.hello.spring.registry.RegObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
@RequestMapping("/v1")
@RestController
@Slf4j
public class LocalController {

    private static final String CLASS_VEHICLE = "vehicle";
    private static final String CLASS_ERROR = "error";
    private static final String CLASS_COW = "cow";

    private static final Map<UUID, Map<String, ObjectCount>> DFQL_OBJECT_COUNT_PER_CLASS = initDfqlObjectCountPerClass();

    private static Map<UUID, Map<String, ObjectCount>> initDfqlObjectCountPerClass() {

        Map<UUID, Map<String, ObjectCount>> result = new HashMap<>();

        final HashMap<String, ObjectCount> orgProj = new HashMap<>();
        orgProj.put(CLASS_VEHICLE, new ObjectCount(CLASS_VEHICLE, 12, null));
        orgProj.put(CLASS_ERROR, new ObjectCount(CLASS_ERROR, 9, null));
        result.put(UUID.fromString(Dfql.ID_ORG_PROJ), orgProj);

        result.put(UUID.fromString(Dfql.ID_NES_COWS), Collections.singletonMap(CLASS_COW, new ObjectCount(CLASS_COW, 7, null)));

        result.put(UUID.fromString(Dfql.ID_NO_CLASSES), Collections.emptyMap());

        return result;
    }


    @SuppressWarnings("unused")
    @GetMapping(value = "/projects/{org}/{project}/spec/revisions/latest/source.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLatestProjectSpec(@PathVariable("org") String organization,
                                       @PathVariable("project") String project,
                                       @RequestParam("with_env_uuid") String with_env_uuid,
                                       @RequestHeader(name="X-Internal-Token", defaultValue="", required=false) String internalToken) throws Exception {
        log.debug("[getLatestProjectSpec] organization={} project={} withEnv={}", organization, project, with_env_uuid);
        log.debug("X-Internal-Token: {}", internalToken);

        final String proj;
        final String env;
        if (project.contains("~")) {
            final String[] split = project.split("~");
            proj = split[0];
            env = split[1];
        }
        else {
            proj = project;
            env = "prod";
        }

        final Env envObj = WhiteRaven.findEnvironment(organization, proj, env);

        if (envObj != null ) {
            return WhiteRaven.projectSpecForWhiteRaven(envObj);
        }
        else {
            return projectSpecForDfql(organization, project);
        }
    }

    private String projectSpecForDfql(@PathVariable("org") String org, @PathVariable("project") String project) {
        final String env = org + "_" + project;
        switch (env) {
            case "org_proj":
                return Dfql.PS_ORG_PROJ;
            case "nes_cows":
                return Dfql.PS_NES_COWS;
            case "no_classes":
                return Dfql.PS_NO_CLASSES;
            default:
                throw new IllegalArgumentException("No such env: " + env);
        }
    }

    @GetMapping(value = "/projects/{org}/{project}/spec/revisions/latest/classes_structure", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getClassesStructure( @PathVariable("org") String organization,
                                       @PathVariable("project") String project,
                                       @RequestHeader(name="X-Internal-Token", defaultValue="", required=false) String internalToken) throws Exception {

        log.debug("[getClassesStructure] organization={} project={}", organization, project);
        log.debug("X-Internal-Token: {}", internalToken);

        return WhiteRaven.classesStructure();

    }

    @GetMapping(value = "/environments/{org}/{project}/uuid", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEnvUuid( @PathVariable("org") String organization,
                              @PathVariable("project") String project,
                              @RequestHeader(name="X-Internal-Token", defaultValue="", required=false) String internalToken) throws Exception {

        log.debug("[getEnvUuid] organization={} project={} withEnv={}", organization, project);
        log.debug("X-Internal-Token: {}", internalToken);

        final String proj;
        final String env;
        if (project.contains("~")) {
            final String[] split = project.split("~");
            proj = split[0];
            env = split[1];
        }
        else {
            proj = project;
            env = "prod";
        }

        final Env envObj = WhiteRaven.findEnvironment(organization, proj, env);

        return new ObjectMapper()
                .writeValueAsString(new UuidResponse(envObj.getUuid()));
    }


        @SuppressWarnings("unused")
    @GetMapping(value = "/objects/_/~{unv_uuid}/_count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ObjectCount> countObjects(@PathVariable("unv_uuid") String envUuid) {
        log.debug("[countObjects] envUuid={}", envUuid);
        final Map<String, ObjectCount> objectCountPerClass = DFQL_OBJECT_COUNT_PER_CLASS.get(UUID.fromString(envUuid));
        if (objectCountPerClass == null) {
            throw new IllegalArgumentException("No such env: " + envUuid);
        } else if (objectCountPerClass.isEmpty()) {
            return Collections.emptyList();
        } else {
            return objectCountPerClass.values();
        }
    }

    @SuppressWarnings("unused")
    @GetMapping(value = "/objects/_/~{unv_uuid}/{class}/_count", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ObjectCount> countObjects(@PathVariable("unv_uuid") String envUuid, @PathVariable("class") String className) {
        log.debug("[countObjects] envUuid={}", envUuid);
        final Map<String, ObjectCount> objectCountPerClass = DFQL_OBJECT_COUNT_PER_CLASS.get(UUID.fromString(envUuid));
        if (objectCountPerClass == null) {
            throw new IllegalArgumentException("No such env: " + envUuid);
        } else if (objectCountPerClass.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(objectCountPerClass.get(className));
        }
    }

    @GetMapping(value = "/organizations")
    public OrganizationsResponse getOrganizations() {
        return new OrganizationsResponse(new Meta(), WhiteRaven.getAllOrganizations());
    }

    @GetMapping(value = "/objects/{org}/{proj}~{env}/{clazz}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectsResponse getObjectsOfClass(
            @PathVariable("org") String org,
            @PathVariable("proj") String proj,
            @PathVariable("env") String env,
            @PathVariable("clazz") String clazz,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor) {

        final OrgProj orgProj = new OrgProj(org, proj);
        final List<RegObject> allObjects;
        if (WhiteRaven.match(orgProj, env)) {
            allObjects = WhiteRaven.getObjectsOfClass(orgProj, env, clazz);
        }
        else {
            allObjects = Collections.emptyList();
        }
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

    @GetMapping(value = "/projects/{org}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectsResponse getProjectsForOrg(
            @PathVariable("org") String org,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor,
            @RequestHeader(name="X-Internal-Token", defaultValue="", required=false) String internalToken) {

        log.debug("[getProjectsForOrg] org={} limit={} cursor={}", org, limit, cursor);
        log.debug("X-Internal-Token: {}", internalToken);

        if (WhiteRaven.invalidOrganization(org)) throw new IllegalArgumentException("Unrecognized organization: " + org);

        final ProjectsResponse projectsResponse = new ProjectsResponse();


        List<Project> allProjects;
        if (WhiteRaven.matchOrganization(org)) {
            allProjects = WhiteRaven.getProjectsForOrg(org);
        }
        else {
            allProjects = Collections.emptyList();
        }

        final int startIndex = Paging.startIndex(cursor);
        final int lastIndex = Paging.lastIndex(startIndex, limit, allProjects.size());
        final List<Project> thisBulk = Paging.thisBulk(allProjects, startIndex, lastIndex);
        final Meta meta = Paging.meta(limit, cursor, allProjects.size(), lastIndex);
        projectsResponse.setProjects(thisBulk);
        projectsResponse.setMeta(meta);
        return projectsResponse;
    }

    @GetMapping(value = "/environments/{org}/{project}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EnvironmentsResponse getEnvironmentsForProject(
            @PathVariable("org") String org,
            @PathVariable("project") String project,
            @RequestHeader(name="X-Internal-Token", defaultValue="", required=false) String internalToken) {

        log.debug("[getProjects] org={} project={}", org, project);
        log.debug("X-Internal-Token: {}", internalToken);

        if (WhiteRaven.invalidOrganization(org)) throw new IllegalArgumentException("Unrecognized organization: " + org);
        if (WhiteRaven.invalidProject(project)) throw new IllegalArgumentException("Unrecognized project: " + project);


        final EnvironmentsResponse environmentsResponse = new EnvironmentsResponse();
        environmentsResponse.setDefault("prod");
        final ArrayList<Environment> environments = new ArrayList<>(2);
        environments.add(new Environment("dev", "Development", "An environment for development of new features.", "prod", null, null));
        environments.add(new Environment("prod", "Production", "An environment for live production data.", null, null, null));
        environmentsResponse.setEnvironments(environments);
        return environmentsResponse;
    }

    private static class Paging {
        private static Meta meta(int limit, String cursor, int totalSize, int lastIndex) {
            return new Meta(cursor, nextCursor(lastIndex, totalSize), limit, null);
        }

        private static int lastIndex(int startIndex, int bulkSize, int totalSize) {
            return Math.min(startIndex+bulkSize-1, totalSize -1);
        }

        private static int startIndex(String cursor) {
            final int startIndex;
            if (cursor.isEmpty()) {
                startIndex = 0;
            }
            else {
                startIndex = Integer.parseInt(cursor);
            }
            return startIndex;
        }

        private static <T> List<T> thisBulk(List<T> items, int startIndex, int lastIndex) {
            final ArrayList<T> res = new ArrayList<>(lastIndex - startIndex + 1);
            for (int i = startIndex; i <= lastIndex; i++) {
                res.add(items.get(i));
            }
            return res;
        }

        private static String nextCursor(int lastIndexProvided, int totalSize) {
            final String nextCursor;
            if (lastIndexProvided+1 < totalSize) {
                nextCursor = "" + (lastIndexProvided + 1);
            }
            else {
                nextCursor = null;
            }
            return nextCursor;
        }
    }
}
