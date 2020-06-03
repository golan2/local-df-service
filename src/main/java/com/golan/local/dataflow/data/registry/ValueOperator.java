package com.golan.local.dataflow.data.registry;

import com.golan.local.dataflow.controllers.RulesEngineController.DataflowStreamType;

import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum ValueOperator {
    EQUALS("eq", EnumSet.of(
            DataflowStreamType.NUMBER,
            DataflowStreamType.INTEGER,
            DataflowStreamType.TEXT,
            DataflowStreamType.BOOLEAN,
            DataflowStreamType.GEOPOINT,
            DataflowStreamType.TIMESTAMP,
            DataflowStreamType.IMAGE)),
    LESS_THAN("lt", EnumSet.of(
            DataflowStreamType.NUMBER,
            DataflowStreamType.INTEGER,
            DataflowStreamType.TIMESTAMP)),
    LESS_THAN_EQUALS("lte", EnumSet.of(
            DataflowStreamType.NUMBER,
            DataflowStreamType.INTEGER,
            DataflowStreamType.TIMESTAMP)),
    GREATER_THAN("gt", EnumSet.of(
            DataflowStreamType.NUMBER,
            DataflowStreamType.INTEGER,
            DataflowStreamType.TIMESTAMP)),
    GREATER_THAN_EQUALS("gte", EnumSet.of(
            DataflowStreamType.NUMBER,
            DataflowStreamType.INTEGER,
            DataflowStreamType.TIMESTAMP)),
    STARTS_WITH("starts_with", EnumSet.of(
            DataflowStreamType.TEXT));

    private final String value;
    private final EnumSet<DataflowStreamType> compatibleTypes;

    public String getValue() {
        return value;
    }

    public boolean isCompatibleType(DataflowStreamType type) {
        return compatibleTypes.contains(type);
    }

    public static Optional<ValueOperator> fromString(String value) {
        return Optional.ofNullable(stringToEnum.get(value));
    }


    ValueOperator(String value, EnumSet<DataflowStreamType> compatibleTypes) {
        this.value = value;
        this.compatibleTypes = compatibleTypes;
    }

    private static final Map<String, ValueOperator> stringToEnum = Stream.of(values())
            .collect(Collectors.toMap(ValueOperator::getValue, Function.identity()));
}
