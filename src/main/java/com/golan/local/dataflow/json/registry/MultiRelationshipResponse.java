package com.golan.local.dataflow.json.registry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.golan.local.dataflow.json.Meta;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MultiRelationshipResponse {
    private Meta meta;
    private List<RegObject> objects;
    private String type;
    @JsonProperty("class")
    private String clazz;
}
