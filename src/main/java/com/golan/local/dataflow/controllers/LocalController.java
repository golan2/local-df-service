package com.golan.local.dataflow.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.golan.local.dataflow.data.Dfql;
import com.golan.local.dataflow.data.Env;
import com.golan.local.dataflow.data.WhiteRaven;
import com.golan.local.dataflow.json.iam.organizations.OrganizationsResponse;
import com.golan.local.dataflow.json.kong.Consumer;
import com.golan.local.dataflow.json.orchestration.environments.Environment;
import com.golan.local.dataflow.json.orchestration.environments.EnvironmentsResponse;
import com.golan.local.dataflow.json.orchestration.projects.Project;
import com.golan.local.dataflow.json.orchestration.projects.ProjectsResponse;
import com.golan.local.dataflow.json.orchestration.spec.UuidResponse;
import com.golan.local.dataflow.data.LoadEnvData;
import com.golan.local.dataflow.json.Meta;
import com.golan.local.dataflow.json.Paging;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RequestMapping("/")
@RestController
@Slf4j
public class LocalController {

    @Value("${data.environment.name}")
    private String envName;


    @GetMapping(value = "v1/organizations")
    public OrganizationsResponse getOrganizations() {
        log.debug("~~~[getOrganizations]");
        if (envName.equalsIgnoreCase("load")) {
            return new OrganizationsResponse(new Meta(), LoadEnvData.getAllOrganizations());
        } else if (envName.equalsIgnoreCase("WhiteRaven")) {
            return new OrganizationsResponse(new Meta(), WhiteRaven.getAllOrganizations());
        } else {
            throw new IllegalArgumentException("Unknown Env Name: " + envName);
        }
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


        List<Project> allProjects;
        if (WhiteRaven.matchOrganization(org)) {
            allProjects = WhiteRaven.getProjectsForOrg(org);
        } else {
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



    //   /v1/projects

    @SuppressWarnings("unused")
    @GetMapping(value = "v1/projects/{org}/{project}/spec/revisions/latest/compiled", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLatestCompiledSpec(@PathVariable("org") String organization,
                                        @PathVariable("project") String project,
                                        @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) {
        log.debug("~~~[getLatestCompiledSpec] organization={} project={}", organization, project);

        final String orgProj = organization + "/" + project;
        switch (orgProj) {
            case "mormont/shayba":
            case "mormont/shayba~dev":
            case "mormont/shayba~prod":
                return Dfql.CS_MORMONT_SHAYBA;
            default:
                throw new IllegalArgumentException("Unrecognized project: " + orgProj);

        }
    }

    ///v1/projects/_/~aea50ab6-32db-11ea-977e-29d880279c99/spec/revisions/470230d5a568a353585033e6aa06d1e6d43ad4ab/source.json
    @GetMapping(value = "v1/projects/_/~{envUuid}/spec/revisions/{revision}/source.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRevisionProjectSpec(@PathVariable("envUuid") String envUuid,
                                          @PathVariable("revision") String revision,
                                          @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) throws Exception {
        log.debug("~~~[getRevisionProjectSpec] envUuid={} revision={}", envUuid, revision);
        if (Dfql.ID_SHAYBA_DEV.equalsIgnoreCase(envUuid)) {
            return getLatestProjectSpec("mormont", "shayba", "", internalToken);
        }
        else {
            throw new IllegalArgumentException("Unsupported ENVUUID: " + envUuid);
        }

    }

    @GetMapping(value = "v1/projects/{envUuid}/_/spec/revisions/latest/compiled", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLatestCompiledSpec(@PathVariable("envUuid") String envUuid,
                                        @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) {
        log.debug("~~~[getLatestCompiledSpec] envUuid={}", envUuid);

        if (Dfql.ID_SHAYBA_DEV.equalsIgnoreCase(envUuid)) {
            return getLatestCompiledSpec("mormont", "shayba~dev", internalToken);
        }
        else {
            throw new IllegalArgumentException("Unrecognized envUuid: " + envUuid);
        }
    }

    @SuppressWarnings("unused")
    @GetMapping(value = "v1/projects/{org}/{project}/spec/revisions/latest/source.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLatestProjectSpec(@PathVariable("org") String organization,
                                       @PathVariable("project") String project,
                                       @RequestParam("with_env_uuid") String with_env_uuid,
                                       @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) throws Exception {
        log.debug("~~~[getLatestProjectSpec] organization={} project={} withEnv={}", organization, project, with_env_uuid);

        final Env envObj = WhiteRaven.findEnvironment(organization, project);

        if (envObj != null) {
            return WhiteRaven.projectSpecForWhiteRaven(envObj);
        } else {
            return projectSpecForDfql(organization, project);
        }
    }


    private String projectSpecForDfql(@PathVariable("org") String org, @PathVariable("project") String project) {
        log.debug("~~~[projectSpecForDfql] organization={} project={}", org, project);
        final String env = org + "/" + project;

        switch (env) {
            case "org/proj":
                return Dfql.PS_ORG_PROJ;
            case "nes/cows":
                return Dfql.PS_NES_COWS;
            case "no/classes":
                return Dfql.PS_NO_CLASSES;
            case "mormont/shayba":
            case "mormont/shayba~dev":
            case "mormont/shayba~prod":
                return Dfql.PS_MORMONT_SHAYBA;
            default:
                throw new IllegalArgumentException("No such env: " + env);
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
                             @RequestHeader(name = "X-Internal-Token", defaultValue = "", required = false) String internalToken) throws Exception {

        log.debug("~~~[getEnvUuid] organization={} project={}", organization, project);
        log.debug("X-Internal-Token: {}", internalToken);

        final Env envObj = WhiteRaven.findEnvironment(organization, project);

        if (envObj == null) return null;
        return new ObjectMapper().writeValueAsString(new UuidResponse(envObj.getUuid()));
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
        final ArrayList<Environment> environments = new ArrayList<>(2);
        environments.add(new Environment("dev", "Development", "An environment for development of new features.", "prod", null, null));
        environments.add(new Environment("prod", "Production", "An environment for live production data.", null, null, null));
        environmentsResponse.setEnvironments(environments);
        return environmentsResponse;
    }


    @GetMapping(value = "/consumers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Consumer consumers(@RequestParam("custom_id") String customId) {
        log.debug("~~~[Kong][consumers] customId={}", customId);
        return new Consumer(customId, LocalDateTime.now(), "", UUID.randomUUID());
    }





}