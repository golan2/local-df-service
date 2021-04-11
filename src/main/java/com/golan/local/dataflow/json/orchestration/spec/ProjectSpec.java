package com.golan.local.dataflow.json.orchestration.spec;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSpec {
    private final String envUuid;
    private final Map<String, Component> architecture;
    private final Map<String, Class> classes;

    @JsonCreator
    public ProjectSpec(@JsonProperty("env_uuid") String envUuid, @JsonProperty("architecture") Map<String, Component> architecture, @JsonProperty("classes") Map<String, Class> classes){
        this.envUuid = envUuid;
        this.architecture = architecture == null ? emptyMap(): architecture;
        this.classes = classes == null ? emptyMap() : classes;
    }

    private static Map<String, Class> collectClasses(@JsonProperty("classes") Map<String, Class> classes) {
        return classes.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new Class(entry.getValue().getClassUuid(), entry.getKey(), entry.getValue().streams, entry.getValue().isAdapter)));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Component { }

    @AllArgsConstructor
    @Getter
    @SuppressWarnings("unused")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Class {
        private final UUID classUuid;
        private final String name;
        private final Map<String, Stream> streams;
        private final boolean isAdapter;

        @JsonCreator
        private Class(@JsonProperty("adapter") Object adapter, @JsonProperty("streams") Map<String, Stream> streams) {
            this(null, null, collectStreams(streams), adapter != null);
        }

        private static Map<String, Stream> collectStreams(Map<String, Stream> streams) {
            if (streams == null) {
                return emptyMap();
            }
            return streams.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> new Stream(e.getValue().type, e.getKey())));
        }

        boolean isAdapter() {
            return isAdapter;
        }

//        public Collection<Stream> getStreams() {
//            if (streams == null) {
//                return emptyList();
//            }
//            return Collections.unmodifiableCollection(streams.values());
//        }

        Set<String> getStreamNames() {
            if (streams == null) {
                return emptySet();
            }
            return Collections.unmodifiableSet(streams.keySet());
        }

        public String getName() {
            return name;
        }

        Stream getStream(String name) {
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
    static class Stream {

        private final StreamType type;
        private final String name;

        Stream(StreamType type) {
            this(type, null);
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


}
