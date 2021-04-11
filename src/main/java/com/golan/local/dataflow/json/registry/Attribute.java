package com.golan.local.dataflow.json.registry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * This is a registry attribute not a spec attribute
 */
@Data
@Accessors(chain = true)
public class Attribute {
    @JsonProperty("name")
    public String name;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("created_at")
    public Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("updated_at")
    public Date updatedAt;

    @JsonProperty("comment")
    public String comment;

    @JsonProperty("type")
    public DataType type;

    @JsonProperty("uuid")
    public String uuid;

    @JsonProperty("value")
    public Object value;
}

