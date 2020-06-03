package com.golan.local.dataflow.data.registry;

import com.golan.local.dataflow.controllers.RulesEngineController.DataflowStreamType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DataflowClass {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String NAME_FIELD = "name";
    private static final String ADAPTER_FIELD = "adapter";
    private static final String STREAMS_FIELD = "streams";
    private static final String HAS_FIELD = "has";
    private static final String HAS_MANY_FIELD = "hasMany";
    private static final String OBJECT_PROPERTIES_FIELD = "objectProperties";
    private static final String ATTRIBUTES_FIELD = "attributes";
    private static final String COMMANDS_FIELD = "commands";
    private static final String EVENTS_FIELD = "events";
    private static final String ON_CREATE_FIELD = "onCreate";
    private static final String UUID_FIELD = "uuid";

    private final String identifier;
    private final String name;
    private final JSONObject adapter;
    private final Map<String, StreamSpec> streams;
    private final Map<String, Relationship> has;
    private final Map<String, Relationship> hasMany;
    private final Map<String, AttributeSpec> attributes;
    private final Map<String, DataflowEvent> events;
    private final Map<String, CommandSpec> commands;
    private final Map<String, DataflowHook> onCreate;
    private final UUID uuid;

    public DataflowClass(String classIdentifier, JSONObject object) {
        identifier = classIdentifier;
        name = object.optString(NAME_FIELD);
        adapter = object.optJSONObject(ADAPTER_FIELD);
        streams = serializeStreams(object.optJSONObject(STREAMS_FIELD));
        has = serializeRelationships(object.optJSONObject(HAS_FIELD), RelationshipType.HAS);
        hasMany = serializeRelationships(object.optJSONObject(HAS_MANY_FIELD), RelationshipType.HAS_MANY);
        JSONObject objectProperties = object.optJSONObject(OBJECT_PROPERTIES_FIELD);
        if (objectProperties != null && !objectProperties.keySet().isEmpty()) {
            attributes = serializeAttributes(objectProperties);
        } else {
            attributes = serializeAttributes(object.optJSONObject(ATTRIBUTES_FIELD));
        }
        events = serializeEvents(object.optJSONObject(EVENTS_FIELD));
        commands = serializeCommands(object.optJSONObject(COMMANDS_FIELD));
        onCreate = serializeOnCreate(object.optJSONArray(ON_CREATE_FIELD));
        String uuidStr = object.optString(UUID_FIELD, "");
        if ( uuidStr.isEmpty()) {
            uuid = UUID.randomUUID();
        } else {
            uuid = UUID.fromString(uuidStr);
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    JSONObject getAdapter() {
        return adapter;
    }

    public Map<String, StreamSpec> getStreams() {
        return streams;
    }

    public Map<String, StreamSpec> getImageStreams() {
        return this.streams.entrySet()
                .stream()
                .filter(streamEntry -> streamEntry.getValue().getType() == DataflowStreamType.IMAGE)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public StreamSpec getStream(String streamName) {
        return streams.get(streamName);
    }

    public Map<String, Relationship> getHas() {
        return has;
    }

    public Map<String, Relationship> getHasMany() {
        return hasMany;
    }

    public Relationship getRelationshipById(String relationshipId) {
        Relationship relationship = has.get(relationshipId);
        if (relationship == null) {
            relationship = hasMany.get(relationshipId);
        }
        return relationship;
    }

    public Relationship getRelationship(RelationshipType relationshipType, String relationshipId) {
        Relationship relationship = null;
        if (relationshipType == RelationshipType.HAS) {
            relationship = has.get(relationshipId);
        } else if (relationshipType == RelationshipType.HAS_MANY) {
            relationship = hasMany.get(relationshipId);
        }
        return relationship;
    }

    /**
     * Get Relationship list from Has map. search if any of them has the right UUID.
     * If we did not find any (orElse) do the same for hasMany
     * @param relationshipUuid {UUID}
     * @return Relationship or null if not found
     */
    public Relationship getRelationshipByUuid(UUID relationshipUuid) {
        return has.values().stream()
                .filter(relationship -> relationship.getRelationshipUuid().compareTo(relationshipUuid) == 0)
                .reduce((x, y) -> y)
                .orElse(hasMany.values().stream()
                        .filter(relationship -> relationship.getRelationshipUuid().compareTo(relationshipUuid) == 0)
                        .reduce((x, y) -> y)
                        .orElse(null)
                );
    }

    public Relationship getHas(String relationshipId) {
        return has.get(relationshipId);
    }

    public Relationship getHasMany(String relationshipId) {
        return hasMany.get(relationshipId);
    }

    public Map<String, AttributeSpec> getAttributes() {
        return attributes;
    }

    public AttributeSpec getAttribute(String attribName) {
        return attributes.get(attribName);
    }

    public Map<String, DataflowEvent> getEvents() {
        return events;
    }

    public DataflowEvent getEvent(String eventKey) {
        return events.get(eventKey);
    }

    public Map<String, CommandSpec> getCommands() {
        return commands;
    }

    public CommandSpec getCommand(String commandKey) {
        return commands.get(commandKey);
    }

    public Map<String, DataflowHook> getOnCreate() {
        return onCreate;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "DataflowClass{" +
                "identifier='" + Util.toStringOrDefaultNull(identifier) + '\'' +
                ", name='" + Util.toStringOrDefaultNull(name) + '\'' +
                ", adapter=" + Util.toStringOrDefaultNull(adapter) +
                ", streams=" + Util.toStringOrDefaultNull(streams) +
                ", has=" + has +
                ", hasMany=" + hasMany +
                ", attributes=" + Util.toStringOrDefaultNull(attributes) +
                ", events=" + Util.toStringOrDefaultNull(events) +
                ", commands=" + Util.toStringOrDefaultNull(commands) +
                ", onCreate=" + Util.toStringOrDefaultNull(onCreate) +
                ", uuid=" + Util.toStringOrDefaultNull(uuid) +
                '}';
    }

    /**
     * Translates a JSON object representing streams into a map holding StreamSpec objects
     * @param streamsJson {JSONObject} - Streams spec as defined in the project spec YAML
     * @return {Map<String, StreamSpec>}
     */
    private Map<String, StreamSpec> serializeStreams(JSONObject streamsJson) {
        Map<String, StreamSpec> streamSpecMap = new HashMap<>();
        if (streamsJson != null) {
            for (String streamName : streamsJson.keySet()) {
                try {
                    StreamSpec streamSpec = new StreamSpec(streamName, streamsJson.optJSONObject(streamName));
                    streamSpecMap.put(streamName, streamSpec);
                } catch (IllegalArgumentException e) {
                    logger.error("fail to create StreamSpec for stream {}, JSON: {}", streamName, streamsJson);
                }
            }
        }
        return streamSpecMap;
    }

    /**
     * Translates a JSON object representing relationships into a map holding ReferenceClass objects
     * @param relationshipJson {JSONObject} - Relationships spec as defined in the project spec YAML
     * @param relationshipType {RelationshipType}
     * @return {Map<String, ReferenceClass> The key is relationship Id. The relationship Id is equal to the class id most of the times}
     */
    private Map<String, Relationship> serializeRelationships(JSONObject relationshipJson, RelationshipType relationshipType) {
        Map<String, Relationship> relationshipMap = new HashMap<>();
        if(relationshipJson != null) {
            for (String relatioshipId : relationshipJson.keySet()) {
                try {
                    Relationship relationship = new Relationship(relatioshipId, relationshipType, relationshipJson.optJSONObject(relatioshipId));
                    relationshipMap.put(relatioshipId, relationship);
                } catch (IllegalArgumentException e) {
                    logger.error("fail to create Relationship for class {}, JSON: {}", relatioshipId, relationshipJson);
                }
            }
        }
        return relationshipMap;
    }

    /**
     * Translates a JSON object representing attributes into a map holding AttributeSpec objects
     * @param attributesJson {JSONObject} - Attributes spec as defined in the project spec YAML
     * @return {Map<String, AttributeSpec>}
     */
    private Map<String, AttributeSpec> serializeAttributes(JSONObject attributesJson) {
        Map<String, AttributeSpec> attributeMap = new HashMap<>();
        if (attributesJson != null) {
            for (String attribName : attributesJson.keySet()) {
                try {
                    AttributeSpec attributeSpec = new AttributeSpec(attribName, attributesJson.optJSONObject(attribName));
                    attributeMap.put(attribName, attributeSpec);
                } catch (IllegalArgumentException e) {
                    logger.error("fail to create AttributeSpec for attribute {}, JSON: {}", attribName, attributesJson);
                }
            }
        }
        return attributeMap;
    }

    /**
     * Translates a JSON object representing events into a map holding AttributeSpec objects
     * @param jsonObj {JSONObject} - Events spec as defined in the project spec YAML
     */
    private Map<String, DataflowEvent> serializeEvents(JSONObject jsonObj) {
        Map<String, DataflowEvent> eventMap = new HashMap<>();
        if(jsonObj != null) {
            for (String eventIdentifier : jsonObj.keySet()) {
                DataflowEvent event = new DataflowEvent(jsonObj.getJSONObject(eventIdentifier));
                eventMap.put(eventIdentifier, event);
            }
        }
        return eventMap;
    }

    /**
     * Translates a JSON object representing commands into a map holding commands objects
     * @param jsonObj {JSONObject} - Commands spec as defined in the project spec YAML
     */
    private Map<String, CommandSpec> serializeCommands(JSONObject jsonObj) {
        Map<String, CommandSpec> commandMap = new HashMap<>();
        if(jsonObj != null) {
            for (String commandIdentifier : jsonObj.keySet()) {
                CommandSpec commandSpec = new CommandSpec(commandIdentifier, jsonObj.getJSONObject(commandIdentifier));
                commandMap.put(commandIdentifier, commandSpec);
            }
        }
        return commandMap;
    }

    /**
     * Translates a JSON array representing onCreate hooks into a map holding DataflowHook objects
     * @param jsonArr {JSONArray} - onCreate hooks spec as defined per class in the project spec YAML
     */
    private Map<String, DataflowHook> serializeOnCreate(JSONArray jsonArr) {
        Map<String, DataflowHook> hookMap = new HashMap<>();
        if (jsonArr != null) {
            StreamSupport.stream(jsonArr.spliterator(), false)
                    .map(val -> (JSONObject) val)
                    .forEach(hookJson -> {
                        String hookIdentifier = hookJson.keySet().iterator().next();
                        DataflowHook hook = new CommandHook(hookIdentifier, hookJson.optJSONObject(hookIdentifier));
                        hookMap.put(hookIdentifier, hook);
                    });
        }
        return hookMap;
    }


}


