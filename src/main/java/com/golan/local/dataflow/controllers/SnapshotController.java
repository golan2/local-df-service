package com.golan.local.dataflow.controllers;


import com.golan.local.dataflow.data.Dfql;
import com.golan.local.dataflow.json.snapshot.DataflowMeta;
import com.golan.local.dataflow.json.snapshot.SnapshotsObject;
import com.golan.local.dataflow.json.snapshot.SnapshotsSearchRespBody;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/v1/snapshots")
@RestController
@Slf4j
public class SnapshotController {
    private static final String OBJ_dron_06 = "b9414a35-72cc-11ea-8b84-c10ad4006bc2";
    private static final String OBJ_dron_07 = "fd390a27-72cc-11ea-8b84-29fc82e8d322";
    private static final String OBJ_dron_08 = "d67f1208-72cf-11ea-8b84-f11ac700f221";

    private static final String DATE_1 = "2010-01-01T00:00:00+00:00";
    private static final String DATE_2 = "2010-01-02T00:00:00+00:00";
    private static final String DATE_3 = "2010-01-03T00:00:00+00:00";
    private static final String SPEED_UUID = "6af5cbdc-7fda-426d-b9fc-c7d59d86527c";

    @SuppressWarnings("unused")
    @PostMapping(value = "/{env_uuid}/{class_uuid}/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public SnapshotsSearchRespBody searchByStreamValue(
            @PathVariable("env_uuid") String envUuid,
            @PathVariable("class_uuid") String classUuid,
            @RequestBody String body,
            @RequestParam(value = "revision", defaultValue = "", required = false) String revision,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor) {

        try {
            if (!cursor.isEmpty()) throw new IllegalArgumentException("Cursor is not IMPLEMENTED");
            if (!isMormontShayba(envUuid) ) throw new IllegalArgumentException("Unrecognized envUuid " + envUuid);
            if (!isDrone(classUuid) ) throw new IllegalArgumentException("Unrecognized classUuid " + classUuid);

            final JSONObject json = new JSONObject(body);

            final JSONObject streams = json.getJSONObject("streams");
            final JSONObject expr = streams.getJSONObject(SPEED_UUID);

            if (expr.keySet().contains("gt")) {
                final double speedValue = expr.getDouble("gt");
                if (speedValue==14.0) {
                    List<SnapshotsObject> objects = new ArrayList<>();
                    objects.add(new SnapshotsObject(classUuid, envUuid, OBJ_dron_06, DATE_1));
                    objects.add(new SnapshotsObject(classUuid, envUuid, OBJ_dron_07, DATE_2));
                    objects.add(new SnapshotsObject(classUuid, envUuid, OBJ_dron_08, DATE_3));
                    DataflowMeta meta = new DataflowMeta(limit, cursor, "");
                    return new SnapshotsSearchRespBody(objects, meta);
                }
            }
            throw new IllegalArgumentException("Unsupported Filter: " + body);

        } catch (Exception e) {
            log.error("Error ", e);
            throw e;
        }
    }

    private boolean isMormontShayba(@PathVariable("env_uuid") String envUuid) {
        return "aea50ab6-32db-11ea-977e-29d880279c99".equalsIgnoreCase(envUuid);
    }

    private boolean isDrone(@PathVariable("class_uuid") String classUuid) {
        return Dfql.CLASS_UUID_DRONE.equalsIgnoreCase(classUuid);
    }
}
