package com.golan.hello.spring.orchestration.spec;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

class StreamsDeserializer extends JsonDeserializer<ProjectSpec.Stream> {

    @Override
    public ProjectSpec.Stream deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        ProjectSpec.StreamType streamType = deserializeStreamType(node);
        return new ProjectSpec.Stream(streamType);
    }

    private ProjectSpec.StreamType deserializeStreamType(JsonNode node) {
        if (node.isObject()) {
            String type = node.get("type").asText();
            return ProjectSpec.StreamType.forValue(type);
        }
        return ProjectSpec.StreamType.forValue(node.asText());
    }
}
