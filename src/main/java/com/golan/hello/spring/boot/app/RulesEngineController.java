package com.golan.hello.spring.boot.app;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/v1/rules-engine/")
@RestController
@Slf4j
public class RulesEngineController {

    @PostMapping(value = "/{org}/{proj}~{env}/hooks/onObjectCreate", produces = MediaType.APPLICATION_JSON_VALUE)
    public String onObjectCreate(
            @PathVariable("org") String org,
            @PathVariable("proj") String proj,
            @PathVariable("env") String env,
            @RequestBody RegistryObjectNotification message) {
        log.debug("RulesEngineController - onObjectCreate - {}/{}~{}   => {}", org, proj, env, message);
        return "OK";
    }

    @Data
    public static class RegistryObjectNotification {
        @JsonProperty("class")
        private final String clazz;
        @JsonProperty("object")
        private final String object;
        @JsonProperty("attribute")
        private final String attribute;
        @JsonProperty("attributeValue")
        private final String attributeValue;
        @JsonProperty("attributeType")
        private final DataflowStreamType attributeType;
        @JsonProperty("attributePreviousValue")
        private final String attributePreviousValue;

        @JsonCreator
        public RegistryObjectNotification(@JsonProperty("class") String clazz,
                                          @JsonProperty("object") String object,
                                          @JsonProperty("attribute") String attribute,
                                          @JsonProperty("attributeValue") String attributeValue,
                                          @JsonProperty("attributeType") DataflowStreamType attributeType,
                                          @JsonProperty("attributePreviousValue") String attributePreviousValue) {
            this.clazz = clazz;
            this.object = object;
            this.attribute = attribute;
            this.attributeValue = attributeValue;
            this.attributeType = attributeType;
            this.attributePreviousValue = attributePreviousValue;
        }
    }

    public enum DataflowStreamType {
        UNKNOWN(0),
        NUMBER(1),
        TEXT(2),
        BOOLEAN(3),
        GEOPOINT(4),
        INTEGER(5),
        TIMESTAMP(6),
        IMAGE(7);

        private int streamType;

        /**
         * Returns an instance of the enum identified by the int value
         * Returns null if not found
         * @param type {int} - int value of the defined enums
         * @return {DataflowStreamType}
         */
        public static DataflowStreamType create(int type) {
            for (DataflowStreamType streamType: DataflowStreamType.values()) {
                if (streamType.streamType == type) {
                    return streamType;
                }
            }
            return null;
        }

        /**
         * Returns an instance of the enum identified by the string value
         * Returns the UNKNOWN instance if not found
         * @param type {String} - string value of the defined enums
         * @return {DataflowStreamType}
         */
        public static DataflowStreamType create(String type) {
            for (DataflowStreamType streamType: DataflowStreamType.values()) {
                if (streamType.name().equalsIgnoreCase(type)) {
                    return streamType;
                }
            }
            return UNKNOWN;
        }

        DataflowStreamType(int type) {
            streamType = type;
        }

        public String getStreamType() {
            return this.toString().toLowerCase();
        }
    }

}
