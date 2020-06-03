package com.golan.local.dataflow.data.registry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;

class Relationship {


    private static final String UUID_FIELD = "uuid";
    private static final String INVERSE_FIELD = "inverse";
    private static final int    INVERSE_RELATIONSHIP_TYPE = 0;
    private static final int    INVERSE_RELATIONSHIP_ID = 1;

    private static final String CLASS_FIELD = "class";
    private final String id;
    /** Direct relationship fields **/
    //CLASS_FIELD
    private final String relatedClass;
    //UUID_FIELD
    private final UUID relationshipUuid;
    private final RelationshipType directType;
    /** Inverse relationship fields **/
    private final String           inverseRelationshipId;
    private UUID                   inverseRelationshipUuid;
    private final RelationshipType inverseRelationshipType;

    /**
     *
     * @param id the identifier of the relationship. for example: "manyDrivers"
     * @param directType the type of the direction: has/hasMany
     * @param relationshipJson the json from spec representing the relationship. For example:
     *
     *         {
     *           "class": "driver",
     *           "inverse": ["has", "truck"],
     *           "uuid": "2faeba91-76fd-4c98-b557-abbe8f383263"
     *         }
     */
    public Relationship(String id, RelationshipType directType, JSONObject relationshipJson) {
        this.id = id;
        this.directType = directType;
        String uuidStr = relationshipJson.optString(UUID_FIELD, "");
        if ( uuidStr.isEmpty()) {
            throw new IllegalArgumentException("ProjectSpec is missing mandatory value: Relationship relationshipUuid");
        }
        relationshipUuid = UUID.fromString(uuidStr);
        relatedClass = relationshipJson.optString(CLASS_FIELD);
        JSONArray inverse = relationshipJson.optJSONArray(INVERSE_FIELD);
        if (inverse != null && inverse.length() == 2) {
            String stringInverseRelationshipType = inverse.getString(INVERSE_RELATIONSHIP_TYPE);
            Optional<RelationshipType> optionalRelationshipType = RelationshipType.fromString(inverse.getString(INVERSE_RELATIONSHIP_TYPE));
            if (optionalRelationshipType.isPresent()) {
                inverseRelationshipType = optionalRelationshipType.get();
            }
            else{
                String errorMsg = String.format("ProjectSpec inconsistency in Relationship[%s]: %s is not a valid relationship type for inverseRelationshipType",
                        this.toString(), stringInverseRelationshipType );
                throw new IllegalArgumentException(errorMsg);
            }
            inverseRelationshipId = inverse.getString(INVERSE_RELATIONSHIP_ID);
        } else {
            String errorMsg = String.format("ProjectSpec inconsistency in Relationship[%s]: inverse field \"[%s]\" can't be parsed, ",this.toString(), (inverse!=null) ? inverse.toString() : inverse);
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public String getId() {
        return id;
    }

    public RelationshipType getDirectType() {
        return directType;
    }

    public UUID getRelationshipUuid() {
        return relationshipUuid;
    }

    public String getRelatedClass() {
        return relatedClass;
    }

    public RelationshipType getInverseRelationshipType() {
        return inverseRelationshipType;
    }

    public String getInverseRelationshipId() {
        return inverseRelationshipId;
    }

    public UUID getInverseRelationshipUuid() {
        return inverseRelationshipUuid;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "id='" + id + '\'' +
                ", directType='" + directType + '\'' +
                ", relationshipUuid=" + Util.toStringOrDefaultNull(relationshipUuid) +
                ", relatedClass='" + relatedClass + '\'' +
                ", inverseRelationshipId=" + inverseRelationshipId +
                ", inverseRelationshipType=" + inverseRelationshipType +
                '}';
    }

    public void setInverseRelationshipUuid(UUID relationshipUuid) {
        this.inverseRelationshipUuid = relationshipUuid;
    }
}
