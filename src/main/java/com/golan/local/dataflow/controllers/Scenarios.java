package com.golan.local.dataflow.controllers;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ToString
@Slf4j
@Component
public class Scenarios {

    enum Scenario {Load, WhiteRaven, Fleet, Dfql}

    private final ScenariosConfig config;
    private final Set<Scenario> scenarios;

    public Scenarios(ScenariosConfig config) {
        this.config = config;
        this.scenarios = buildScenarios();
    }

    boolean isEmpty() {
        return scenarios.isEmpty();
    }

    boolean contains(Scenario scenario) {
        return scenarios.contains(scenario);
    }


    private Set<Scenario> buildScenarios() {
        log.info("Building scenario from: {}", config.csv);

        if (config.csv == null || config.csv.isEmpty()) {
            return Collections.emptySet();
        }

        return Arrays
                .stream(config.csv.split(Pattern.quote(",")))
                .map(String::trim)
                .map(Scenario::valueOf)
                .collect(Collectors.toSet());
    }


    @Data
    @Configuration
    @ConfigurationProperties(prefix = "data.scenarios")
    public static class ScenariosConfig {
        private String csv;
    }
}
