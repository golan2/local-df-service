package com.golan.local.dataflow.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.golan.local.dataflow.controllers.Scenarios.Scenario;
import com.golan.local.dataflow.data.Dfql;
import com.golan.local.dataflow.data.Env;
import com.golan.local.dataflow.data.Fleet;
import com.golan.local.dataflow.data.LoadEnvData;
import com.golan.local.dataflow.data.Shayba;
import com.golan.local.dataflow.data.WhiteRaven;
import com.golan.local.dataflow.json.iam.organizations.Organization;
import com.golan.local.dataflow.json.orchestration.environments.Environment;
import com.golan.local.dataflow.json.orchestration.projects.Project;
import com.golan.local.dataflow.json.orchestration.spec.ProjectSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrcDataGenerator {


    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final Scenarios scenarios;



    List<Organization> getOrganizationsList() {
        log.debug("~~~[getOrganizations]");
        if (scenarios.isEmpty()) {
            throw new IllegalArgumentException("Unknown Scenario CSV: " + scenarios);
        }

        final ArrayList<Organization> result = new ArrayList<>();
        if (scenarios.contains(Scenario.Load)) {
            result.addAll( LoadEnvData.getAllOrganizations() );
        }
        if (scenarios.contains(Scenario.WhiteRaven)) {
            result.addAll( WhiteRaven.getAllOrganizations() );
        }
        if (scenarios.contains(Scenario.Fleet)) {
            result.addAll( Fleet.getAllOrganizations() );
        }
        return result;
    }



    List<Project> getProjectsForOrg(String org) {
        List<Project> allProjects;
        if (WhiteRaven.matchOrganization(org)) {
            allProjects = WhiteRaven.getProjectsForOrg(org);
        }
        else if (Fleet.matchOrganization(org)) {
            return Collections.singletonList(Fleet.FLEET_PROJ);
        }
        else {
            allProjects = Collections.emptyList();
        }
        return allProjects;
    }

    ArrayList<Environment> getEnvironmentsForProject() {
        final ArrayList<Environment> environments = new ArrayList<>(2);
        environments.add(new Environment("dev", "Development", "An environment for development of new features.", "prod", null, null));
        environments.add(new Environment("prod", "Production", "An environment for live production data.", null, null, null));
        return environments;
    }

    UUID convertToEnvUUID(String organization, String project) throws RejectException {
        final Env white = WhiteRaven.findEnvironment(organization, project);
        if (white != null) {
            return white.getUuid();
        }

        final Env fleet = Fleet.findEnvironment(organization, project);
        if (fleet != null) {
            return fleet.getUuid();
        }

        final Env shayba = Shayba.findEnvironment(organization, project);
        if (shayba != null) {
            return shayba.getUuid();
        }

        throw new ProjectDoesNotExistsException();
    }

    Env findEnvByUuid(String envUuid) throws RejectException {
        final Env white = WhiteRaven.findEnvironment(UUID.fromString(envUuid));
        if (white != null) {
            return white;
        }

        final Env fleet = Fleet.findEnvironment(UUID.fromString(envUuid));
        if (fleet != null) {
            return fleet;
        }

        final Env shayba = Shayba.findEnvironment(UUID.fromString(envUuid));
        if (shayba != null) {
            return shayba;
        }

        throw new ProjectDoesNotExistsException();

    }

    ProjectSpec getLatestProjectSpec(Env env) throws IOException {
        if (WhiteRaven.findEnvironment(env.getUuid()) != null) {
            return WhiteRaven.getProjectSpec(env);
        }
        else {
            return MAPPER.readValue(projectSpecForOtherUsages(env.getOrg(), env.getProj() + "~" + env.getEnv()), ProjectSpec.class);
        }
    }

    String projectSpecForOtherUsages(@PathVariable("org") String org, @PathVariable("project") String project) {
        log.debug("~~~[projectSpecForOtherUsages] organization={} project={}", org, project);
        final String env = org + "/" + project;

        switch (env) {
            case "org/proj":
                return Dfql.PS_ORG_PROJ;
            case "nes/cows":
                return Dfql.PS_NES_COWS;
            case "no/classes":
                return Dfql.PS_NO_CLASSES;
            case "mormont/shayba":
            case "mormont/shayba~prod":
                throw new IllegalArgumentException("We do not support [prod] for [mormont/shayba] yet");
            case "mormont/shayba~dev":
                return Shayba.PS_MORMONT_SHAYBA_DEV;
            case "golan2/shayba":
            case "golan2/shayba~prod":
                return Shayba.PS_GOLAN2_SHAYBA_PROD;
            case "golan2/shayba~dev":
                return Shayba.PS_GOLAN2_SHAYBA_DEV;
            case "fleet/fleet-trucks-iot~dev":
                return Fleet.PROJECT_SPEC_DEV;
            case "fleet/fleet-trucks-iot":
            case "fleet/fleet-trucks-iot~prod":
                return Fleet.PROJECT_SPEC_PROD;
            default:
                throw new IllegalArgumentException("No such env: " + env);
        }
    }

    ProjectSpec getLatestCompiledSpec(Env env) throws IOException {
        return getLatestCompiledSpec(env.getOrg() ,env.getProj() + "~" + env.getEnv());
    }

    ProjectSpec getLatestCompiledSpec(String organization, String project) throws IOException {
        return MAPPER.readValue( getLatestCompiledSpecAsString(organization, project), ProjectSpec.class );
    }

    String getLatestCompiledSpecAsString(String organization, String project) {
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
}
