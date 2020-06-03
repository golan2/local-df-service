package com.golan.local.dataflow.data.registry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.golan.local.dataflow.data.registry.BodyFilters.FilterType.ATTRIBUTE;
import static com.golan.local.dataflow.data.registry.BodyFilters.FilterType.STREAM;

public class BodyFilters {
    private static final String JSON_KEY_FILTER = "filter";

    private static final String ERROR_EMPTY = "Empty json";
    private static final String ERROR_INVALID_JSON = "invalid json";
    private static final String ERROR_OVERSIZE = "Too many fields, expected one single key '" + JSON_KEY_FILTER + "'";
    private static final String ERROR_FILTERS_VALUE_JSON_PARSE = "Value associated with '" + JSON_KEY_FILTER + "' expected to be Json";
    private static final String ERROR_FILTERS_VALUE_SIZE_MUST_BE_ONE = "The Json value associated with '" + JSON_KEY_FILTER + "': expected only one single key: '" + STREAM.getName() + "' or '" + ATTRIBUTE.getName() + "'";

    private static final String ERROR_FORMAT_FILTERS = "Unrecognized field '%s', expected one single field: '" + JSON_KEY_FILTER + "'";
    private static final String ERROR_FORMAT_FILTERS_VALUE_MISSING_ATTRIBUTES_OR_STREAMS = "The Json value associated with '" + JSON_KEY_FILTER + "': unrecognized field '%s', expected  '" + STREAM.getName() + "' or '" + ATTRIBUTE.getName() + "'";

    public enum FilterType {
        STREAM("stream"),
        ATTRIBUTE("attribute");

        public static Optional<FilterType> fromString(String name) {
            return Optional.ofNullable(stringToEnum.get(name));
        }

        FilterType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
        private static final Map<String, FilterType> stringToEnum = Stream.of(values())
                .collect(Collectors.toMap(FilterType::getName, Function.identity()));

        private final String name;
    }

    private final ValueFilter valueFilter;
    private final FilterType type;

    public FilterType getType() {
        return type;
    }

    public ValueFilter getValueFilter() {
        return valueFilter;
    }

    private BodyFilters(ValueFilter valueFilter, FilterType type) {
        this.valueFilter = valueFilter;
        this.type = type;
    }

    public static BodyFilters createFrom(String jsonString,
                                         DataflowClass dataflowClass)  {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            JSONObject filters = extractFilters(jsonObject);

            if (filters.keySet().size() == 1) {
                String attributeOrStream = filters.keys().next();
                Optional<FilterType> optionalFilterType = FilterType.fromString(attributeOrStream);
                if (!optionalFilterType.isPresent()){
                    throw new RuntimeException(String.format(ERROR_FORMAT_FILTERS_VALUE_MISSING_ATTRIBUTES_OR_STREAMS, attributeOrStream));
                }

                JSONObject jsonFilter = filters.getJSONObject(attributeOrStream);
                ValueFilter valueFilter = ValueFilter.createFrom(jsonFilter, dataflowClass, optionalFilterType.get());
                return new BodyFilters(valueFilter, optionalFilterType.get());
            } else {
                throw new RuntimeException(ERROR_FILTERS_VALUE_SIZE_MUST_BE_ONE);
            }
        }
        catch (JSONException e) {
            throw new RuntimeException(ERROR_INVALID_JSON);
        }

    }

    /**
     * Extract the value of "filters", while handling errors
     * @param jsonObject
     * @return The JSONObject associated with "filters"
     * @throws RuntimeException in case size of jsonObject is different than 1, or in case that the key is missing,
     * or the value of 'filters' is not valid JSONObject
     */
    private static JSONObject extractFilters(JSONObject jsonObject) throws RuntimeException {
        JSONObject filters;
        if (jsonObject.keySet().isEmpty()) {
            throw new RuntimeException(ERROR_EMPTY);
        } else if (jsonObject.keySet().size() > 1) {
            throw new RuntimeException(ERROR_OVERSIZE);
        } else if (!jsonObject.has(JSON_KEY_FILTER)) {
            throw new RuntimeException(String.format(ERROR_FORMAT_FILTERS, jsonObject.keys().next()));
        }
        try {
            filters = jsonObject.getJSONObject(JSON_KEY_FILTER);
            return filters;
        }
        catch (JSONException e){
            throw new RuntimeException(ERROR_FILTERS_VALUE_JSON_PARSE);
        }
    }


}
