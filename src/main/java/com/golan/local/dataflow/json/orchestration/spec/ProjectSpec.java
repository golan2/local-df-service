package com.golan.local.dataflow.json.orchestration.spec;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.golan.local.dataflow.json.registry.Attribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSpec {
    private final String envUuid;
    private final Map<String, Component> architecture;
    private final Map<String, ClassSpec> classes;

    @JsonCreator
    public ProjectSpec(@JsonProperty("env_uuid") String envUuid, @JsonProperty("architecture") Map<String, Component> architecture, @JsonProperty("classes") Map<String, ClassSpec> classes){
        this.envUuid = envUuid;
        this.architecture = architecture == null ? emptyMap(): architecture;
        this.classes = classes == null ? emptyMap() : classes;
    }

    private static Map<String, ClassSpec> Add(@JsonProperty("classes") Map<String, ClassSpec> classes) {
        return classes.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new ClassSpec(entry.getValue().getClassUuid(), entry.getKey(), entry.getValue().streams, entry.getValue().getAttributes(), entry.getValue().isAdapter)));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Component { }

    @AllArgsConstructor
    @Getter
    @SuppressWarnings("unused")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ClassSpec {
        @JsonProperty("uuid")
        private final UUID classUuid;
        private final String name;
        private final Map<String, StreamSpec> streams;
        private final Map<String, Attribute> attributes;
        private final boolean isAdapter;

        @JsonCreator
        private ClassSpec(@JsonProperty("adapter") Object adapter, @JsonProperty("streams") Map<String, StreamSpec> streams) {
            this(null, null, collectStreams(streams), Collections.emptyMap(), adapter != null);
        }

        private static Map<String, StreamSpec> collectStreams(Map<String, StreamSpec> streams) {
            if (streams == null) {
                return emptyMap();
            }
            return streams.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> new StreamSpec(e.getValue().type, e.getKey())));
        }

        boolean isAdapter() {
            return isAdapter;
        }

        Set<String> getStreamNames() {
            if (streams == null) {
                return emptySet();
            }
            return Collections.unmodifiableSet(streams.keySet());
        }

        public String getName() {
            return name.toLowerCase();
        }

        StreamSpec getStream(String name) {
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
    static class StreamSpec {

        private final StreamType type;
        private final String name;

        StreamSpec(StreamType type) {
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
