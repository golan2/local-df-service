package com.golan.local.dataflow.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * When an error
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "description",
        "errors"
})
public class ErrorResponse {
    private final String message;
    private final String description;
    private final SingleError errors;

    public ErrorResponse(String message, String description) {
        this(message, description, null);
    }

    @SuppressWarnings("WeakerAccess")   //the [getError] is used but IntelliJ doesn't understand this Lombok usage
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SingleError {
        private final String error;
    }
}
