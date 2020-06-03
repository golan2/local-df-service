package com.golan.local.dataflow.controllers;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SuppressWarnings("unused")
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
    SingleValueChart getCountersPerClassSingleValue(@RequestParam("env_uuid") UUID envUuid,
                                                    @RequestParam("entity") EntityType entity,
                                                    @RequestParam("device_type")String deviceType,
                                                    @RequestParam("aggregateFunction") AggregationFunction aggregateFunction,
                                                    @RequestParam("from")String from,
                                                    @RequestParam("to")String to) {

        log.info("~~~~~~~  |getCountersPerClassSingleValue| envUuid=[{}] entity=[{}] deviceType=[{}] aggregateFunction=[{}] from=[{}] to=[{}] ", envUuid, entity, deviceType, aggregateFunction, from, to);


        if (EntityType.MESSAGES.equals(entity))
            return new SingleValueChart(100.0, "Transactions");
        else if (EntityType.VOLUME_SIZE.equals(entity))
            return new SingleValueChart(200.0, "Volume Size");
        else
            throw new IllegalArgumentException("Unsupported Entity: " + entity);
    }

    @GetMapping(value = "/counters")
    SingleValueChart getCountersSingleValue(@RequestParam("org_bucket")String orgBucket,
                                   @RequestParam("project_bucket")String projBucket,
                                   @RequestParam("org_id")String org,
                                   @RequestParam("project_id")String project,
                                   @RequestParam("environment")String env,
                                   @RequestParam("entity") EntityType entity,
                                   @RequestParam("device_type")String deviceType,
                                   @RequestParam("aggregateFunction") AggregationFunction aggregateFunction,
                                   @RequestParam("from")String from,
                                   @RequestParam("to")String to) {
        log.info("~~~~~~~  |getCountersSingleValue| orgBucket=[{}] projBucket=[{}] org=[{}] project=[{}] env=[{}] entity=[{}] deviceType=[{}] aggregateFunction=[{}] from=[{}] to=[{}] ",
                orgBucket, projBucket, org, project, env, entity, deviceType, aggregateFunction, from, to);

        if (EntityType.COUNT_DISTINCT_DEVICE.equals(entity))
            return new SingleValueChart(80, "Distinct Active Objects");
        else
            throw new IllegalArgumentException("Unsupported Entity: " + entity);
    }

    @Data
    private static class SingleValueChart {
        private final Number data;
        private final String key;
    }

}
