package com.golan.hello.spring.orchestration.spec;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassesStructureResponse {
    private final Map<String, Class> classes;

    public ClassesStructureResponse(@JsonProperty("classes") Map<String, Class> classes) {
        this.classes = classes == null ? emptyMap() : collectClasses(classes);
    }

    private Map<String, Class> collectClasses(@JsonProperty("classes") Map<String, Class> classes) {
        return classes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new Class(entry.getKey())));
    }

    Collection<Class> getAllClasses() {
        return classes.isEmpty() ? emptyList() : Collections.unmodifiableCollection(classes.values());
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Class {
        private final String name;

        @JsonCreator
        public Class(@JsonProperty("name") String name) {
            this.name = name;
        }

    }
}
