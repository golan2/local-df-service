package com.golan.local.dataflow.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golan.local.dataflow.authentication.InternalApi;
import com.golan.local.dataflow.data.Env;
import com.golan.local.dataflow.data.Fleet;
import com.golan.local.dataflow.data.OrgProjEnv;
import com.golan.local.dataflow.data.Shayba;
import com.golan.local.dataflow.data.WhiteRaven;
import com.golan.local.dataflow.json.Meta;
import com.golan.local.dataflow.json.Paging;
import com.golan.local.dataflow.json.iam.organizations.OrganizationsResponse;
import com.golan.local.dataflow.json.kong.Consumer;
import com.golan.local.dataflow.json.orchestration.environments.Environment;
import com.golan.local.dataflow.json.orchestration.environments.EnvironmentsResponse;
import com.golan.local.dataflow.json.orchestration.projects.Project;
import com.golan.local.dataflow.json.orchestration.projects.ProjectsResponse;
import com.golan.local.dataflow.json.orchestration.spec.UuidResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RequestMapping("/")
@RestController
@Slf4j
@InternalApi
@RequiredArgsConstructor
public class OrchestrationController {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Value("${data.environment.name}")
    private String envName;
    private int counter;
    final OrcDataGenerator dataGenerator;

    @GetMapping(value = "v1/tps")
    public boolean getTps() {
        log.debug("getTps {}", ++counter);
        return true;
    }


    @GetMapping(value = "v1/organizations")
    public OrganizationsResponse getOrganizations() {
        return new OrganizationsResponse(new Meta(), dataGenerator.getOrganizationsList());
    }


    @GetMapping(value = "v1/projects/{org}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectsResponse getProjectsForOrg(
            @PathVariable("org") String org,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor,
            @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) {

        log.debug("~~~[getProjectsForOrg] org={} limit={} cursor={}", org, limit, cursor);
        log.debug("X-Internal-Token: {}", internalToken);

        if (WhiteRaven.invalidOrganization(org))
            throw new IllegalArgumentException("Unrecognized organization: " + org);

        final ProjectsResponse projectsResponse = new ProjectsResponse();


        final List<Project> allProjects = dataGenerator.getProjectsForOrg(org);

        final int startIndex = Paging.startIndex(cursor);
        final int lastIndex = Paging.lastIndex(startIndex, limit, allProjects.size());
        final List<Project> thisBulk = Paging.thisBulk(allProjects, startIndex, lastIndex);
        final Meta meta = Paging.meta(limit, cursor, allProjects.size(), lastIndex);
        projectsResponse.setProjects(thisBulk);
        projectsResponse.setMeta(meta);
        return projectsResponse;
    }


    //   /v1/projects


    @GetMapping(value = "v1/projects/{envUuid}/_/spec/revisions/latest/compiled", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLatestCompiledSpecInternal(@PathVariable("envUuid") String envUuid,
                                                @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) {
        log.debug("~~~[getLatestCompiledSpecInternal] envUuid={}", envUuid);

        if (Shayba.ENV_UUID_MORMONT_SHAYBA_DEV.equalsIgnoreCase(envUuid)) {
            return getLatestCompiledSpec(Shayba.ORG_MORMONT, Shayba.PROJECT +"~dev", internalToken);
        }
        else if (Shayba.ENV_UUID_GOLAN2_SHAYBA_PROD.equalsIgnoreCase(envUuid)) {
            return getLatestCompiledSpec(Shayba.ORG_GOLAN2, Shayba.PROJECT +"~prod", internalToken);
        }
        else if (Shayba.ENV_UUID_GOLAN2_SHAYBA_DEV.equalsIgnoreCase(envUuid)) {
            return getLatestCompiledSpec(Shayba.ORG_GOLAN2, Shayba.PROJECT +"~dev", internalToken);
        }
        else {
            throw new IllegalArgumentException("Unrecognized envUuid: " + envUuid);
        }
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    @GetMapping(value = "v1/projects/{org}/{project}/spec/revisions/latest/compiled", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLatestCompiledSpec(@PathVariable("org") String organization,
                                        @PathVariable("project") String project,
                                        @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) {
        log.debug("~~~[getLatestCompiledSpec] organization={} project={}", organization, project);

        final String orgProj = organization + "/" + project;
        switch (orgProj) {
            case "mormont/shayba~dev":
                return Shayba.CS_MORMONT_SHAYBA_DEV;
            case "mormont/shayba":
            case "mormont/shayba~prod":
                throw new IllegalArgumentException("We do not support [prod] for [mormont/shayba] yet");
            case "golan2/shayba~dev":
                return Shayba.CS_GOLAN2_SHAYBA_DEV;
            case "golan2/shayba":
            case "golan2/shayba~prod":
                return Shayba.CS_GOLAN2_SHAYBA_PROD;
            case "fleet/fleet-trucks-iot~dev":
                return Fleet.COMPILED_SPEC_DEV;
            case "fleet/fleet-trucks-iot":
            case "fleet/fleet-trucks-iot~prod":
                return Fleet.COMPILED_SPEC_PROD;
            default:
                throw new IllegalArgumentException("Unrecognized project: " + orgProj);

        }
    }

    ///v1/projects/_/~aea50ab6-32db-11ea-977e-29d880279c99/spec/revisions/470230d5a568a353585033e6aa06d1e6d43ad4ab/source.json
    @GetMapping(value = "v1/projects/_/~{envUuid}/spec/revisions/{revision}/source.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRevisionProjectSpecInternal(@PathVariable("envUuid") String envUuid,
                                         @PathVariable("revision") String revision,
                                         @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) throws Exception {
        log.debug("~~~[getRevisionProjectSpecInternal] envUuid={} revision={}", envUuid, revision);
        if (Shayba.ENV_UUID_MORMONT_SHAYBA_DEV.equalsIgnoreCase(envUuid)) {
            return getLatestProjectSpec(Shayba.ORG_MORMONT, Shayba.PROJECT+"~dev", revision, "", internalToken);
        }
        if (Shayba.ENV_UUID_GOLAN2_SHAYBA_DEV.equalsIgnoreCase(envUuid)) {
            return getLatestProjectSpec(Shayba.ORG_GOLAN2, Shayba.PROJECT+"~dev", revision, "", internalToken);
        }
        if (Shayba.ENV_UUID_GOLAN2_SHAYBA_PROD.equalsIgnoreCase(envUuid)) {
            return getLatestProjectSpec(Shayba.ORG_GOLAN2, Shayba.PROJECT+"~prod", revision, "", internalToken);
        }
        else if (Fleet.ENV_UUID_DEV.equalsIgnoreCase(envUuid)) {
            return getLatestProjectSpec(Fleet.ORG, Fleet.PROJ, revision, "", internalToken);
        }
        else if (Fleet.ENV_UUID_PROD.equalsIgnoreCase(envUuid)) {
            return getLatestProjectSpec(Fleet.ORG, Fleet.PROJ, revision, "", internalToken);
        }
        else if (WhiteRaven.findEnvironment(UUID.fromString(envUuid)) != null) {
            final Env env = WhiteRaven.findEnvironment(UUID.fromString(envUuid));
            return WhiteRaven.getProjectSpecAsString(env);
        }
        else {
            throw new IllegalArgumentException("Unsupported ENVUUID: " + envUuid);
        }

    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    @GetMapping(value = "v1/projects/{org}/{project}/spec/revisions/{revision}/source.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLatestProjectSpec(@PathVariable("org") String organization,
                                       @PathVariable("project") String project,
                                       @PathVariable("revision") String revision,
                                       @RequestParam("with_env_uuid") String with_env_uuid,
                                       @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) throws JsonProcessingException {
        log.debug("~~~[getLatestProjectSpec] organization={} project={} revision={} withEnv={}", organization, project, revision, with_env_uuid);

        final Env envObj = WhiteRaven.findEnvironment(organization, project);

        if (envObj != null) {
            return WhiteRaven.getProjectSpecAsString(envObj);
        } else {
            return dataGenerator.projectSpecForOtherUsages(organization, project);
        }
    }


    @GetMapping(value = "v1/projects/{org}/{project}/spec/revisions/latest/classes_structure", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getClassesStructure(@PathVariable("org") String organization,
                                      @PathVariable("project") String project,
                                      @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) throws Exception {

        log.debug("~~~[getClassesStructure] organization={} project={}", organization, project);
        log.debug("X-Internal-Token: {}", internalToken);

        return WhiteRaven.classesStructure();

    }


    //   /v1/environments

    @GetMapping(value = "v1/environments/{org}/{project}/uuid", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEnvUuid(@PathVariable("org") String organization,
                             @PathVariable("project") String project,
                             @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) throws JsonProcessingException, RejectException {

        log.debug("~~~[getEnvUuid] organization={} project={}", organization, project);
        log.debug("X-Internal-Token: {}", internalToken);

        return MAPPER.writeValueAsString(new UuidResponse(dataGenerator.convertToEnvUUID(organization, project)));
    }

    @GetMapping(value = "v1/environments/{envUuid}/identifier", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrgProjEnv getOrgProjEnv(@PathVariable("envUuid") String envUuid,
                             @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) throws JsonProcessingException, RejectException {

        log.debug("~~~[getOrgProjEnv] envUuid={}", envUuid);
        log.debug("X-Internal-Token: {}", internalToken);

        return new OrgProjEnv(dataGenerator.findEnvByUuid(envUuid));
    }

    @GetMapping(value = "v1/environments/{org}/{project}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EnvironmentsResponse getEnvironmentsForProject(
            @PathVariable("org") String org,
            @PathVariable("project") String project,
            @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) {

        log.debug("~~~[getEnvironmentsForProject] org={} project={}", org, project);
        log.debug("X-Internal-Token: {}", internalToken);

        if (WhiteRaven.invalidOrganization(org))
            throw new IllegalArgumentException("Unrecognized organization: " + org);
        if (WhiteRaven.invalidProject(project)) throw new IllegalArgumentException("Unrecognized project: " + project);


        final EnvironmentsResponse environmentsResponse = new EnvironmentsResponse();
        environmentsResponse.setDefault("prod");
        final ArrayList<Environment> environments = dataGenerator.getEnvironmentsForProject();
        environmentsResponse.setEnvironments(environments);
        return environmentsResponse;
    }


    @GetMapping(value = "/consumers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Consumer consumers(@RequestParam("custom_id") String customId) {
        log.debug("~~~[Kong][consumers] customId={}", customId);
        return new Consumer(customId, LocalDateTime.now(), "", UUID.randomUUID());
    }





}