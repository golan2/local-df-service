package com.golan.hello.spring.boot.orchestration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSpec {
    private final String envUuid;
    private final Map<String, Component> architecture;
    private final Map<String, Class> classes;

    @JsonCreator
    public ProjectSpec(@JsonProperty("env_uuid") String envUuid, @JsonProperty("architecture") Map<String, Component> architecture, @JsonProperty("classes") Map<String, Class> classes){
        this.envUuid = envUuid;
        this.architecture = architecture == null ? emptyMap(): architecture;
        this.classes = classes == null ? emptyMap() : collectClasses(classes);
    }

    private Map<String, Class> collectClasses(@JsonProperty("classes") Map<String, Class> classes) {
        return classes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new Class(entry.getValue().isAdapter, entry.getKey(), entry.getValue().streams)));
    }

    public String getEnvUuid() {
        return envUuid;
    }

    public Collection<Class> getAllClasses(){
        return classes.isEmpty() ? emptyList() : Collections.unmodifiableCollection(classes.values());
    }

    public Set<String> getAllClassesNames(){
        return classes.isEmpty() ? emptySet() : Collections.unmodifiableSet(classes.keySet());
    }

    public Class getClass(String name) {
        if (classes == null || !classes.containsKey(name)) {
            return null;
        }
        return classes.get(name);
    }

    public boolean containsClass(String name) {
        return getClass(name) != null;
    }

    public boolean containsStream(String className, String stream) {
        Class aClass = getClass(className);
        if (aClass == null) {
            return false;
        }
        return aClass.getStream(stream) != null;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public Set<String> getComponentsOfType(ComponentType type) {
        switch (type) {
            case ADAPTER: return getAdapters();
            default: return findComponentsOfType(type);
        }
    }

    private Set<String> findComponentsOfType(ComponentType type) {
        if (architecture == null) {
            return emptySet();
        }
        return architecture.entrySet().stream()
                .filter(entry -> type.equals(entry.getValue().type))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private Set<String> getAdapters() {
        if (classes == null) return emptySet();
        return classes.entrySet().stream()
                .filter(entry -> entry.getValue().isAdapter())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Component {
        private final ComponentType type;

        @JsonCreator
        public Component(@JsonProperty("type") ComponentType type) {
            this.type = type;
        }
    }

    public enum ComponentType {
        ADAPTER,
        CUSTOM,
        INPUT;

        @SuppressWarnings("unused")//used by deserializer
        @JsonCreator
        public static ComponentType forValue(String value) {
            return java.util.stream.Stream.of(ComponentType.values())
                    .filter(type -> type.name().equalsIgnoreCase(value))
                    .findFirst()
                    .orElse(null);
        }
    }


    @SuppressWarnings("unused")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Class {
        private final String name;
        private final boolean isAdapter;
        private final Map<String, Stream> streams;

        @JsonCreator
        private Class(@JsonProperty("adapter") Object adapter, @JsonProperty("streams") Map<String, Stream> streams) {
            this(adapter != null, null, collectStreams(streams));
        }

        private static Map<String, Stream> collectStreams(Map<String, Stream> streams) {
            if (streams == null) {
                return emptyMap();
            }
            return streams.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> new Stream(e.getValue().type, e.getKey())));
        }

        Class(boolean isAdapter, String name, Map<String, Stream> streams) {
            this.name = name;
            this.streams = streams;
            this.isAdapter = isAdapter;
        }

        boolean isAdapter() {
            return isAdapter;
        }

        public Collection<Stream> getStreams() {
            if (streams == null) {
                return emptyList();
            }
            return Collections.unmodifiableCollection(streams.values());
        }

        Set<String> getStreamNames() {
            if (streams == null) {
                return emptySet();
            }
            return Collections.unmodifiableSet(streams.keySet());
        }

        public String getName() {
            return name;
        }

        public Stream getStream(String name) {
            if (streams == null || !streams.containsKey(name)) {
                return null;
            }
            return streams.get(name);
        }

        public boolean containsStream(String name) {
            return getStream(name) != null;
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonDeserialize(using = StreamsDeserializer.class)
    public static class Stream {

        private final StreamType type;
        private final String name;

        Stream(StreamType type) {
            this(type, null);
        }

        Stream(Stream stream, String name) {
            this.type = stream.type;
            this.name = name;
        }

        public boolean isNumeric() {
            return StreamType.NUMBER == type || StreamType.INTEGER == type;
        }
    }

    public enum StreamType {
        NUMBER,
        TEXT,
        BOOLEAN,
        GEOPOINT,
        INTEGER,
        TIMESTAMP;

        @SuppressWarnings("unused")//used by deserializer
        @JsonCreator
        public static StreamType forValue(String value) {
            return java.util.stream.Stream.of(StreamType.values())
                    .filter(type -> type.name().equalsIgnoreCase(value))
                    .findFirst()
                    .orElse(null);
        }
    }

    public static class Builder {

        private final Map<String, Class> classes = new HashMap<>();
        private final Map<String, Component> components = new HashMap<>();
        private final String envUUID;

        private Builder(String envUUID) {
            this.envUUID = envUUID;
        }

        public static Builder instance(String envUUID) {
            return new Builder(envUUID);
        }

        public Builder withClass(String name, Stream ... streams) {
            return buildClass(name, streams, false);
        }

        public Builder withAdapter(String name, Stream ... streams) {
            return buildClass(name, streams, true);
        }

        private Builder buildClass(String name, Stream[] streams, boolean isAdapter) {
            Map<String, Stream> mapOfStreams = java.util.stream.Stream.of(streams).collect(Collectors.toMap(s -> s.name, Function.identity()));
            classes.put(name, new Class(isAdapter, name, mapOfStreams));
            return this;
        }

        public Builder withInput(String name) {
            components.put(name, new Component(ComponentType.INPUT));
            return this;
        }

        public Builder withCustomComponent(String name) {
            components.put(name, new Component(ComponentType.CUSTOM));
            return this;
        }

        public ProjectSpec build() {
            return new ProjectSpec(envUUID, components, classes);
        }
    }


}