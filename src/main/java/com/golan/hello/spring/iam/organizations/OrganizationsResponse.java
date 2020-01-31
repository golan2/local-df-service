
package com.golan.hello.spring.iam.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.golan.hello.spring.registry.Meta;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationsResponse {

    private final Meta meta;
    private final List<Organization> organizations;


    public OrganizationsResponse( @JsonProperty("meta") Meta meta,
                                  @JsonProperty("organizations") List<Organization> organizations) {
        this.meta = meta;
        this.organizations = organizations;
    }
}
