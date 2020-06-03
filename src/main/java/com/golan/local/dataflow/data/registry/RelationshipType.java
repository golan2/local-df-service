package com.golan.local.dataflow.data.registry;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum RelationshipType {
    HAS("has"),
    HAS_MANY("hasMany");


    private String value;

    RelationshipType(String type) {
        value = type;
    }

    public String getValue() {
        return value;
    }

    public static Optional<RelationshipType> fromString(String value ){
        RelationshipType relationshipType = stringToEnum.get(value);
        if (relationshipType == null ) {
            return Optional.empty();
        }
        else{
            return Optional.of(relationshipType);
        }
    }


    private static final Map<String, RelationshipType> stringToEnum = Stream.of(values())
            .collect(Collectors.toMap(RelationshipType::getValue, Function.identity()));

}
