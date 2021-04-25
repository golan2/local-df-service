package com.golan.local.dataflow.json.registry;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.text.ParseException;
import java.util.Date;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelationshipDetails {
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("added_at")
    private Date addedAt;

    @JsonProperty("identifier")
    private String objectId;

    @JsonProperty("object_uuid")
    private String objectUuid;

    private String description;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("last_stream_update")
    private Date lastStreamUpdate;

    @JsonProperty("class")
    private String className;

    public RelationshipDetails(RegObject o) {
        try {
            this.addedAt = o.getCreatedAtAsDate();
            this.objectId = o.getIdentifier();
            this.objectUuid = o.getObjectUuid().toString();
            this.lastStreamUpdate = o.getLastStreamUpdateAsDate();
            this.className = o.getClassName();

        } catch (ParseException e) {
            throw new IllegalArgumentException("Can't parse created_at for object: " + o);
        }
    }
}
