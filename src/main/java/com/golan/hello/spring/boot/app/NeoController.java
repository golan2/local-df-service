package com.golan.hello.spring.boot.app;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RequestMapping("/query")
@RestController
@Slf4j
public class NeoController {

    public enum EntityType {
        MESSAGES,
        VOLUME_SIZE,
        DATA_POINTS,
        COUNT_DISTINCT_DEVICE
    }

    public enum AggregationFunction {
        SUM,
        MAX,
        MIN,
        AVG
    }

    @GetMapping(value = "/counters/overtime/singleval")
    SingleValueChart getCountersPerClassSingleValue(
            @RequestParam("env_uuid") UUID envUuid,
            @RequestParam("entity") EntityType entity,
            @RequestParam("device_type")String deviceType,
            @RequestParam("aggregateFunction") AggregationFunction aggregateFunction,
            @RequestParam("from")String from,
            @RequestParam("to")String to) {

        log.info("envUuid=[{}] entity=[{}] deviceType=[{}] aggregateFunction=[{}] from=[{}] to=[{}] ", envUuid, entity, deviceType, aggregateFunction, from, to);

        return new SingleValueChart(100, "abc");
    }




    public static class SingleValueChart {

        private Number data;
        private String key;

        public SingleValueChart() {
        }

        public SingleValueChart(Number data, String key) {
            this.data = data;
            this.key = key;
        }

        public Number getData() {
            return data;
        }

        public String getKey() {
            return key;
        }
    }



}
