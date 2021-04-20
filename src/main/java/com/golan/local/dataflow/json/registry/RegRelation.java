package com.golan.local.dataflow.json.registry;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class RegRelation {
    private final Date createdAt;
    private final RegObject source;
    private final RegObject destination;
}
