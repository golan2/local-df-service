package com.golan.hello.spring.boot.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golan.hello.spring.registry.Meta;
import com.golan.hello.spring.orchestration.environments.Environment;
import com.golan.hello.spring.orchestration.environments.EnvironmentsResponse;
import com.golan.hello.spring.orchestration.organizations.Organization;
import com.golan.hello.spring.orchestration.organizations.OrganizationsResponse;
import com.golan.hello.spring.orchestration.projects.Project;
import com.golan.hello.spring.orchestration.projects.ProjectsResponse;
import com.golan.hello.spring.orchestration.spec.ProjectSpec;
import com.golan.hello.spring.registry.ObjectCount;
import com.golan.hello.spring.registry.ObjectsResponse;
import com.golan.hello.spring.registry.RegObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
@RequestMapping("/v1")
@RestController
@Slf4j
public class LocalController {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


    private static final String ID_ORG_PROJ = "892daa98-e8dd-11e8-9f32-f2801f1b9fd1";
    private static final String PS_ORG_PROJ = "{\"apiVersion\":0,\"classes\":{\"vehicle\":{\"name\":\"Vehicle\",\"streams\":{\"packetId\":{\"name\":\"PacketID\",\"type\":\"number\"},\"location\":{\"name\":\"Location\",\"type\":\"geopoint\"},\"deviceId\":{\"name\":\"DeviceID\",\"type\":\"integer\"},\"vin\":{\"name\":\"VIN\",\"type\":\"text\"},\"unixTime\":{\"name\":\"Unix Time\",\"type\":\"number\"},\"reasonCode\":{\"name\":\"Reason Code\",\"type\":\"number\"},\"heading\":{\"name\":\"Heading\",\"type\":\"number\"},\"hdop\":{\"name\":\"Hdop\",\"type\":\"number\"},\"numSats\":{\"name\":\"NumStats\",\"type\":\"number\"},\"vehicleBattVolts\":{\"name\":\"Vehicle Battery Volts\",\"type\":\"number\"},\"gpsLifetimeOdom\":{\"name\":\"GPS Lifetime Odom\",\"type\":\"number\"},\"packetSerialNumber\":{\"name\":\"Packet Serial Number\",\"type\":\"number\"},\"speed\":{\"name\":\"Speed\",\"type\":\"number\"},\"imei\":{\"name\":\"IMEI\",\"type\":\"number\"},\"dtcSize\":{\"name\":\"DTCSize\",\"type\":\"number\"},\"dtcArray\":{\"name\":\"DTCArray\",\"type\":\"number\"},\"acceleration\":{\"name\":\"Acceleration\",\"type\":\"number\"},\"deceleration\":{\"name\":\"Deceleration\",\"type\":\"number\"},\"crashPacketSize\":{\"name\":\"Crash Packet Size\",\"type\":\"number\"},\"crashPacket\":{\"name\":\"Crash Packet\",\"type\":\"number\"},\"idleTime\":{\"name\":\"Idle Time\",\"type\":\"number\"},\"rpm\":{\"name\":\"RPM\",\"type\":\"number\"},\"obdVehicleSpeed\":{\"name\":\"OBD Vehicle Speed\",\"type\":\"number\"},\"rawMessage\":{\"name\":\"Raw hex data from device\",\"type\":\"text\"},\"gpsSpeed\":{\"name\":\"GPS Speed\",\"type\":\"integer\"},\"wakeReason\":{\"name\":\"Wake Reason\",\"type\":\"number\"},\"mil\":{\"name\":\"MIL\",\"type\":\"integer\"},\"ecuCount\":{\"name\":\"ECU Count\",\"type\":\"integer\"},\"ecuID\":{\"name\":\"ECU ID\",\"type\":\"text\"},\"dtcCount\":{\"name\":\"DTC Count\",\"type\":\"integer\"},\"dtcCodes\":{\"name\":\"DTC Codes Object Array\",\"type\":\"text\"}},\"adapter\":{\"template\":\"custom-adapter\",\"publishTo\":\"telemetry\",\"count\":2}},\"error\":{\"name\":\"Error\",\"streams\":{\"message\":{\"name\":\"Message\",\"type\":\"text\"},\"code\":{\"name\":\"Code\",\"type\":\"text\"}}}},\"architecture\":{\"telemetry\":{\"type\":\"input\",\"name\":\"Telemetry\"},\"storage\":{\"type\":\"storage\",\"from\":[\"telemetry\",\"errors\"],\"name\":\"Data Storage\"},\"cartasiteEndpoint\":{\"type\":\"custom\",\"from\":[\"telemetry\"],\"config\":{\"count\":2,\"template\":\"custom-adapter\",\"properties\":{\"cartasiteUrl\":\"${CARTASITE_URL}\"}}},\"rawDatatoXirgo\":{\"type\":\"custom\",\"from\":[\"RawXirgoDataOnOff\"],\"comment\":\"This output is just the raw data so that Xirgo can troubleshoot several issuestwo.\",\"config\":{\"count\":2,\"template\":\"custom-adapter\"}},\"RawXirgoDataOnOff\":{\"type\":\"condition\",\"from\":[\"telemetry\"],\"config\":{\"conditions\":[{\"allOf\":[{\"data.unixTime\":[{\"eq\":\"0\"}]}]}]}},\"errors\":{\"type\":\"input\",\"name\":\"Errors\"}},\"services\":{\"importRegDevices\":{\"template\":\"custom-service\",\"count\":2}},\"env_uuid\":\"" + ID_ORG_PROJ + "\"}";
    private static final String ID_NO_CLASSES = "43c81429-605c-43c9-a98e-0afc18f89bd0";
    private static final String PS_NO_CLASSES = "{\"apiVersion\":0,\"classes\":{},\"env_uuid\":\"" + ID_NO_CLASSES + "\"}";
    private static final String ID_NES_COWS = "fab3866e-39f9-4c24-b7d3-11ee2b56bc3b";
    private static final String PS_NES_COWS = "{\"apiVersion\":0,\"organization\":\"nes\",\"identifier\":\"mc-expo\",\"env\":\"dev\",\"env_uuid\":\"" + ID_NES_COWS + "\",\"uuid\":\"" + ID_NES_COWS + "\",\"classes\":{\"cow\":{\"streams\":{\"location\":{\"name\":\"location\",\"comment\":\"GPS coordinates\",\"type\":\"geopoint\",\"uuid\":\"e026300a-43c4-41f9-a501-076e5637af16\"},\"x_axis\":{\"name\":\"x axis\",\"comment\":\"X orientation\",\"type\":\"number\",\"uuid\":\"da22d46d-f105-4be4-bcb1-8ca75ee2a825\"},\"speed\":{\"name\":\"speed\",\"comment\":\"X orientation\",\"type\":\"number\",\"uuid\":\"da22d46d-f105-4be4-bcb1-8ca75ee2a765\"},\"birthday\":{\"name\":\"birthday\",\"comment\":\"\",\"type\":\"timestamp\",\"uuid\":\"05920ccb-94d2-4781-a8df-ab3f4399b46e\"},\"name\":{\"name\":\"name\",\"comment\":\"cow name\",\"type\":\"text\",\"uuid\":\"c87b1406-75e3-409e-bbcb-3943c5d92ff8\"},\"health\":{\"name\":\"Health\",\"type\":\"integer\",\"comment\":\"\",\"uuid\":\"f1edbac5-3ddb-41a2-85d1-f1477b792ea1\"},\"escaped\":{\"name\":\"Escaped the fence\",\"type\":\"boolean\",\"comment\":\"\",\"uuid\":\"12c69520-efa2-4de5-b78d-9c1d44f82ec3\"}},\"name\":\"\",\"comment\":\"\",\"has\":{},\"hasMany\":{},\"objectProperties\":{},\"attributes\":{},\"events\":{},\"commands\":{},\"onCreate\":[],\"uuid\":\"afdda0d9-8494-4a71-8801-a1e831ca4879\"},\"farm\":{\"streams\":{\"weather\":{\"name\":\"Weather\",\"type\":\"text\",\"comment\":\"\",\"uuid\":\"bcdc4942-b9cc-4940-8858-3b6a657afaed\"}},\"name\":\"\",\"comment\":\"\",\"has\":{},\"hasMany\":{},\"objectProperties\":{},\"attributes\":{},\"events\":{},\"commands\":{},\"onCreate\":[],\"uuid\":\"b8c3d9f1-a364-46f5-aff2-2bab00eb2532\"},\"package\":{\"streams\":{\"contents\":\"text\",\"weight\":\"number\",\"volume\":\"number\"}},\"entity-adapter\":{\"adapter\":{\"publishTo\":\"input\",\"template\":\"custom-adapter\",\"properties\":{\"demo\":\"hell\"},\"count\":3,\"uuid\":\"3ccb5d26-33d2-417f-a9b7-e2058aaa62ca\",\"_changes\":{\"template\":false,\"count\":false,\"properties\":false}},\"name\":\"\",\"comment\":\"\",\"has\":{},\"hasMany\":{},\"objectProperties\":{},\"attributes\":{},\"streams\":{},\"events\":{},\"commands\":{},\"onCreate\":[],\"uuid\":\"5836c242-4889-4a7b-a09c-d49c8c997f15\"}},\"architecture\":{\"input\":{\"type\":\"input\"},\"storage\":{\"from\":\"input\"},\"geofence-violation\":{\"type\":\"condition\",\"from\":\"input\",\"config\":{\"conditions\":[{\"data.escaped\":2},{\"noneOf\":[{\"class\":\"enclosure\"}]},{\"anyOf\":[{\"data.z_axis\":[{\"lte\":-408}]},{\"data.z_axis\":[{\"gte\":-395}]},{\"data.x_axis\":[{\"lte\":-265}]},{\"data.x_axis\":[{\"gte\":-245}]}]}]}},\"geoSlackAlert\":{\"type\":\"slackOutput\",\"from\":\"geofence-violation\",\"config\":{\"webhookUrl\":\"https://hooks.slack.com/services/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\",\"text\":\":cow2: @namit @olga FYI - An animal has escaped a geofence: {{object}}\",\"channel\":\"#p1-hello-world\",\"iconEmoji\":\":cow2:\"}},\"geoWSNotification\":{\"type\":\"custom\",\"from\":\"geofence-violation\",\"config\":{\"template\":\"custom-component\"}},\"fireNotification\":{\"type\":\"custom\",\"from\":\"temperature-violation\",\"config\":{\"template\":\"custom-component\"}}},\"services\":{\"registry-service\":{\"template\":\"custom-adapter\",\"count\":2,\"properties\":{},\"uuid\":\"89d70c75-aed1-4e0d-900e-f0316827d008\",\"_changes\":{\"template\":false,\"count\":false,\"properties\":false}},\"geoWSNotification.component\":{\"template\":\"custom-component\",\"count\":2,\"properties\":{},\"comment\":\"\",\"uuid\":\"95ce1693-e87b-47f9-8fb6-8f970e9a2779\",\"_changes\":{\"template\":false,\"count\":false,\"properties\":false}},\"geoWSRestNotif.component\":{\"template\":\"custom-component\",\"count\":2,\"properties\":{},\"comment\":\"\",\"uuid\":\"4f74bc18-9e61-4524-be6f-fa10842b4304\",\"_changes\":{\"template\":false,\"count\":false,\"properties\":false}},\"fireNotification.component\":{\"template\":\"custom-component\",\"count\":2,\"properties\":{},\"comment\":\"\",\"uuid\":\"3fe112c0-2329-4d4b-9250-4622ac7ef2f1\",\"_changes\":{\"template\":false,\"count\":false,\"properties\":false}}},\"actions\":{},\"events\":{},\"revision\":\"412d0aaf5b1d31f72234b27a3b2093614fd90952\",\"deletions\":[]}";

    private static final String CLASS_VEHICLE = "vehicle";
    private static final String CLASS_ERROR = "error";
    private static final String CLASS_COW = "cow";

    private static final Map<UUID, Map<String, ObjectCount>> DFQL_OBJECT_COUNT_PER_CLASS = initDfqlObjectCountPerClass();
    private static final int WHITE_RAVEN_ORG_COUNT = 1;
    private static final int WHITE_RAVEN_PROJ_PER_ORG = 2;
    private static final List<Organization> WHITE_RAVEN_ORGANIZATIONS = initWhiteRavenOrganizations();
    private static final List<Project> WHITE_RAVEN_PROJECTS = initWhiteRavenProjects();
    private static final List<Env> WHITE_RAVEN_ENVS = initWhiteRavenEnvironments();


    private static Map<UUID, Map<String, ObjectCount>> initDfqlObjectCountPerClass() {

        Map<UUID, Map<String, ObjectCount>> result = new HashMap<>();

        final HashMap<String, ObjectCount> orgProj = new HashMap<>();
        orgProj.put(CLASS_VEHICLE, new ObjectCount(CLASS_VEHICLE, 12, null));
        orgProj.put(CLASS_ERROR, new ObjectCount(CLASS_ERROR, 9, null));
        result.put(UUID.fromString(ID_ORG_PROJ), orgProj);

        result.put(UUID.fromString(ID_NES_COWS), Collections.singletonMap(CLASS_COW, new ObjectCount(CLASS_COW, 7, null)));

        result.put(UUID.fromString(ID_NO_CLASSES), Collections.emptyMap());

        return result;
    }

    private static List<Organization> initWhiteRavenOrganizations() {
        final ArrayList<Organization> res = new ArrayList<>();
        for (int i = 1; i <= WHITE_RAVEN_ORG_COUNT; i++) {
            res.add(new Organization(""+i, "wr_org"+i, "member", true, null));
        }
        return res;
    }


    private static List<Project> initWhiteRavenProjects() {
        final ArrayList<Project> res = new ArrayList<>();
        for (Organization org : WHITE_RAVEN_ORGANIZATIONS) {
            for (int i = 1; i <= WHITE_RAVEN_PROJ_PER_ORG; i++) {
                res.add(new Project(org.getIdentifier(), ""+i, "proj_" + i, "", null));
            }
        }
        return res;
    }

    private static List<Env> initWhiteRavenEnvironments() {
        final Stream<Project> a = WHITE_RAVEN_PROJECTS
                .stream();
        final Stream<Env> b = a
                .flatMap( project ->
                        Arrays.stream(new Env[]{
                                new Env(project.getOrganization(), project.getIdentifier(), "dev", UuidStore.next()),
                                new Env(project.getOrganization(), project.getIdentifier(), "prod", UuidStore.next())
                        }));
        return b.collect(Collectors.toList());
    }


    @SuppressWarnings("unused")
    @GetMapping(value = "/projects/{org}/{project}/spec/revisions/latest/source.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLatestProjectSpec(@PathVariable("org") String org, @PathVariable("project") String project, @RequestParam("with_env_uuid") String with_env_uuid) throws Exception {
        log.debug("[getLatestProjectSpec] org={} prog={} withEnv={}", org, project, with_env_uuid);

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

        final Env envObj = findEnvForWhiteRaven(org, proj, env);

        if (envObj != null ) {
            return projectSpecForWhiteRaven(envObj);
        }
        else {
            return projectSpecForDfql(org, project);
        }
    }

    private static Env findEnvForWhiteRaven(String org, String proj, String env) {
        return WHITE_RAVEN_ENVS
                .stream()
                .filter( e -> e.getOrg().equals(org) && e.getProj().equals(proj) && e.getEnv().equals(env) )
                .findAny()
                .orElse(null);
    }

    private String projectSpecForWhiteRaven(Env env) throws JsonProcessingException {
        Map<String, ProjectSpec.Class> classes = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            final String className = "class_" + env.getOrg() + "_" + env.getProj() + "_" + env.getEnv() + "_" + i;
            classes.put(className, new ProjectSpec.Class(className, false, null));
        }
        return new ObjectMapper()
                .writeValueAsString(
                        new ProjectSpec(env.getUuid().toString(), null, classes)
                );
    }

    private String projectSpecForDfql(@PathVariable("org") String org, @PathVariable("project") String project) {
        final String env = org + "_" + project;
        switch (env) {
            case "org_proj":
                return PS_ORG_PROJ;
            case "nes_cows":
                return PS_NES_COWS;
            case "no_classes":
                return PS_NO_CLASSES;
            default:
                throw new IllegalArgumentException("No such env: " + env);
        }
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

    @GetMapping(value = "/objects/{org}/{proj}~{env}/{clazz}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectsResponse getObjectsOfClass(@PathVariable("org") String org,
                                             @PathVariable("proj") String proj,
                                             @PathVariable("env") String env,
                                             @PathVariable("clazz") String clazz,
                                             @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
                                             @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor) {
        final ObjectsResponse objectsResponse = new ObjectsResponse();
        int objectCount = whiteRavenObjectCountPerClass(clazz);
        final ArrayList<RegObject> objects = new ArrayList<>(limit);

        int beginIndex = 0;
        if (cursor.trim().length()>0) beginIndex = Integer.parseInt(cursor);

        while (beginIndex < objectCount && objects.size()<limit) {
            objects.add(new RegObject("obj_"+beginIndex++, false, null, SIMPLE_DATE_FORMAT.format(new Date()), "", clazz, null));
        }
        if (beginIndex < objectCount) {
            objectsResponse.setMeta(new Meta(cursor, ""+beginIndex, limit, null));
        }
        else {
            objectsResponse.setMeta(new Meta(cursor, null, limit, null));
        }
        objectsResponse.setObjects(objects);
        return objectsResponse;
    }

    private static int whiteRavenObjectCountPerClass(String clazz) {
        switch (clazz) {
            case "class_1_1_dev_0":
                return 26;
            case "class_1_1_dev_1":
                return 7;
            case "class_1_1_dev_2":
                return 8;
            default:
                return 0;
        }
    }

    @GetMapping(value = "/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationsResponse getOrganizations() {
        final OrganizationsResponse organizationsResponse = new OrganizationsResponse();
        organizationsResponse.setMeta(new Meta());
        organizationsResponse.setOrganizations(WHITE_RAVEN_ORGANIZATIONS);
        return organizationsResponse;
    }

    @GetMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectsResponse getProjects(
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor) throws IOException {

        final ProjectsResponse projectsResponse = new ProjectsResponse();
        final Meta meta = new Meta();
        meta.setCursor(cursor);
        meta.setLimit(limit);

        int index;
        if (cursor.isEmpty()) {
            index = 0;
        }
        else {
            index = Integer.parseInt(cursor);
        }

        List<Project> projects = new ArrayList<>();
        while (index< WHITE_RAVEN_PROJECTS.size() && projects.size()<limit) {
            projects.add(WHITE_RAVEN_PROJECTS.get(index++));
        }

        projectsResponse.setProjects(projects);

        if (index>= WHITE_RAVEN_PROJECTS.size()) {
            meta.setNextCursor(null);
        }
        else {
            meta.setNextCursor(""+index);
        }

        projectsResponse.setMeta(meta);
        return projectsResponse;
    }

    @GetMapping(value = "/environments/{org}/{project}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EnvironmentsResponse getProjects(@PathVariable("org") String org,
                                            @PathVariable("project") String project) {
        final EnvironmentsResponse environmentsResponse = new EnvironmentsResponse();
        environmentsResponse.setDefault("prod");
        final ArrayList<Environment> environments = new ArrayList<>(2);
        environments.add(new Environment("dev", "Development", "An environment for development of new features.", "prod", null, null));
        environments.add(new Environment("prod", "Production", "An environment for live production data.", null, null, null));
        environmentsResponse.setEnvironments(environments);
        return environmentsResponse;
    }

    @ToString
    @AllArgsConstructor
    @Data
    private static class Env {
        private final String org;
        private final String proj;
        private final String env;
        private final UUID uuid;

    }

    private static class UuidStore {
        private static int nextIndex = 0;
        private static List<UUID> inventar = init();

        private static List<UUID> init() {
            final ArrayList<UUID> res = new ArrayList<>(100);
            for (int i = 1; i <= 100; i++) {
                res.add( UUID.fromString("8fab5a7d-72e7-40ce-8d02-01fce3"+String.format("%06d", i)) );
            }
            return res;
        }

        public static UUID next() {
            return inventar.get(nextIndex++);
        }
    }
}
