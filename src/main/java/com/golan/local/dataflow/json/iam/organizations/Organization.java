
package com.golan.local.dataflow.json.iam.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization {
    private final String identifier;
    private final String name;

    public Organization( @JsonProperty("identifier") String identifier,
                         @JsonProperty("name")String name) {
        this.identifier = identifier;
        this.name = name;
    }
}
