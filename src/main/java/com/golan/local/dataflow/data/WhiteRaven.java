package com.golan.local.dataflow.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golan.local.dataflow.json.iam.organizations.Organization;
import com.golan.local.dataflow.json.orchestration.projects.Project;
import com.golan.local.dataflow.json.orchestration.spec.ClassesStructureResponse;
import com.golan.local.dataflow.json.orchestration.spec.ProjectSpec;
import com.golan.local.dataflow.json.registry.RegObject;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class WhiteRaven {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private static final int ORG_COUNT = 2;
    private static final int PROJ_COUNT_PER_ORG = 2;
    private static final Map<OrgProj, List<Env>> ENVIRONMENTS = initEnvironments();
    private static final Map<UUID, Env> uuidToEnv = keyByEnvUuid(ENVIRONMENTS);

    private static final String ORG_PREFIX = "wr_org_";
    private static final String PROJECT_PREFIX = "wr_proj_";
    private static final String CLASS_PREFIX = "class_";
    private static final String OBJ_PREFIX = "obj_";
    private static final Map<OrgProjEnvClass, Integer> OBJECT_COUNT_PER_CLASS = initObjectCountPerClass();

    private static String organizationName(int index) {
        return String.format(ORG_PREFIX + "%03d", index+1);
    }

    private static String projectName(int index) {
        return String.format(PROJECT_PREFIX + "%03d", index+1);
    }

    private static String className(int index) {
        return String.format(CLASS_PREFIX + "%03d", index+1);
    }

    private static String objectName(int index) {
        return String.format(OBJ_PREFIX + "%03d", index+1);
    }

    public static boolean invalidOrganization(String organization) {
        if (!organization.startsWith(ORG_PREFIX)) return false;      //it is not White Raven so not me to decide if valid or not
        for (int i = 0; i < ORG_COUNT; i++) {
            if (organizationName(i).equals(organization)) return false;
        }
        return true;
    }

    public static boolean invalidProject(String project) {
        if (!project.startsWith(PROJECT_PREFIX)) return false;      //it is not White Raven so not me to decide if valid or not

        for (int i = 0; i < PROJ_COUNT_PER_ORG; i++) {
            if (projectName(i).equals(project)) return false;
        }
        return true;
    }

    private static Map<OrgProj, List<Env>> initEnvironments() {
        final HashMap<OrgProj, List<Env>> res = new HashMap<>();
        for (int o = 0; o < ORG_COUNT; o++) {
            for (int p = 0; p < PROJ_COUNT_PER_ORG; p++) {
                final String org = organizationName(o);
                final String proj = projectName(p);
                final OrgProj orgProj = new OrgProj(org, proj);
                final List<Env> envs = createEnvironmentsForProject(org, proj);
                res.put(orgProj, envs);
            }
        }
        return res;
    }

    @SuppressWarnings("SameParameterValue")
    private static Map<UUID, Env> keyByEnvUuid(Map<OrgProj, List<Env>> environments) {
        return environments
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect( Collectors.toMap(Env::getUuid, e -> e) );
    }

    private static List<Env> createEnvironmentsForProject(String org, String proj) {
        return  Arrays.stream(new Env[]{
                new Env(org, proj, "dev", UuidStore.next()),
                new Env(org, proj, "prod", UuidStore.next()),
        }).collect(Collectors.toList());
    }


    public static Env findEnvironment(UUID envUuid) {
        return uuidToEnv.get(envUuid);
    }


    public static Env findEnvironment(String organization, String project) {
        if (invalidOrganization(organization)) return null;

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

        if (invalidProject(proj)) return null;
        final List<Env> envList = ENVIRONMENTS.get(new OrgProj(organization, proj));
        if (envList==null) return null;
        return envList
                .stream()
                .filter(e -> e.getEnv().equals(env) )
                .findAny()
                .orElse(null);
    }

    private static Project createProject(String org, int index) {
        return new Project(org, projectName(index), projectName(index), "", null);
    }

    public static String getProjectSpec(Env env) throws JsonProcessingException {
        Map<String, ProjectSpec.Class> classes = getAllClasses(env);
        return new ObjectMapper()
                .writeValueAsString(
                        new ProjectSpec(env.getUuid().toString(), null, classes)
                );
    }

    @SuppressWarnings("unused")
    public static Map<String, ProjectSpec.Class> getAllClasses(Env env) {
        Map<String, ProjectSpec.Class> classes = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            final String className = className(i);
            classes.put(className, new ProjectSpec.Class(className, false, null));
        }
        return classes;
    }

    public static String classesStructure() throws JsonProcessingException {
        Map<String, ClassesStructureResponse.Class> classes = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            final String className = className(i);
            classes.put(className, new ClassesStructureResponse.Class(className));
        }
        return new ObjectMapper()
                .writeValueAsString(new ClassesStructureResponse(classes));


    }

    public static boolean match(OrgProj orgProj, String env) {
        return match(orgProj) && matchEnvironment(env);
    }

    private static boolean match(OrgProj orgProj) {
        return matchOrganization(orgProj.getOrg()) && matchProject(orgProj.getProj());
    }

    private static boolean matchEnvironment(String env) {
        return "dev".equals(env) || "prod".equals(env);
    }

    public static boolean matchOrganization(String organization) {
        for (int i = 0; i < ORG_COUNT; i++) {
            if (organizationName(i).equals(organization)) return true;
        }
        return false;
    }

    private static boolean matchProject(String project) {
        for (int i = 0; i < PROJ_COUNT_PER_ORG; i++) {
            if (projectName(i).equals(project)) return true;
        }
        return false;
    }

    public static List<Project> getProjectsForOrg(String org) {
        final List<Project> projects = new ArrayList<>(PROJ_COUNT_PER_ORG);
        for (int i = 0; i < PROJ_COUNT_PER_ORG; i++) {
            projects.add(createProject(org, i));
        }
        return projects;
    }

    public static List<RegObject> getObjectsOfClass(Env env, String className) {
        return getObjectsOfClass(new OrgProj(env.getOrg(), env.getProj()), env.getEnv(), className);
    }

    public static List<RegObject> getObjectsOfClass(OrgProj orgProj, String env, String className) {
        final int count = getObjectCountForClass(orgProj, env, className);
        final ArrayList<RegObject> res = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            res.add( new RegObject(objectName(i), false, null, SIMPLE_DATE_FORMAT.format(new Date()), "", className, null) );
        }
        return res;
    }

    private static Map<OrgProjEnvClass, Integer> initObjectCountPerClass() {
        final HashMap<OrgProjEnvClass, Integer> res = new HashMap<>();
        res.put(new OrgProjEnvClass(organizationName(0), projectName(0), "dev", className(0)), 26 );
        res.put(new OrgProjEnvClass(organizationName(0), projectName(0), "dev", className(1)), 7 );
        res.put(new OrgProjEnvClass(organizationName(0), projectName(0), "prod", className(0)), 3 );
        res.put(new OrgProjEnvClass(organizationName(0), projectName(0), "prod", className(1)), 7 );
        res.put(new OrgProjEnvClass(organizationName(0), projectName(1), "dev", className(0)), 2 );
        res.put(new OrgProjEnvClass(organizationName(1), projectName(0), "dev", className(0)), 4 );
        return res;
    }

    private static int getObjectCountForClass(OrgProj orgProj, String env, String clazz) {
        final OrgProjEnvClass key = new OrgProjEnvClass(orgProj.getOrg(), orgProj.getProj(), env, clazz);
        return OBJECT_COUNT_PER_CLASS.getOrDefault(key, 0);
    }


    @SuppressWarnings("unused")
    static int getProjectCountForOrg(String org) {
        return PROJ_COUNT_PER_ORG;
    }

    public static ArrayList<Organization> getAllOrganizations() {
        final ArrayList<Organization> res = new ArrayList<>();
        for (int i = 0; i < ORG_COUNT; i++) {
            res.add( new Organization( organizationName(i), organizationName(i) ) );
        }
        return res;
    }

    private static class UuidStore {
        private static int nextIndex = 0;
        private static List<UUID> inventar = init();

        private static List<UUID> init() {
            final ArrayList<UUID> res = new ArrayList<>(100);
            for (int i = 1; i <= ORG_COUNT * PROJ_COUNT_PER_ORG *2; i++) {
                res.add( UUID.fromString(String.format("bbbbbbbb-bbbb-bbbb-bbbb-000000%06d", i)) );
            }
            return res;
        }

        static UUID next() {
            return inventar.get(nextIndex++);
        }
    }
}
