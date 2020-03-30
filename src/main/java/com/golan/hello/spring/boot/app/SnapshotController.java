package com.golan.hello.spring.boot.app;


import com.golan.hello.spring.snapshot.DataflowMeta;
import com.golan.hello.spring.snapshot.SnapshotsObject;
import com.golan.hello.spring.snapshot.SnapshotsSearchRespBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/v1/snapshots")
@RestController
@Slf4j
public class SnapshotController {
    private static final String OBJECT_ID_1 = "AAAAAAAA-AAAA-AAAA-AAAA-000000000001";
    private static final String OBJECT_ID_2 = "AAAAAAAA-AAAA-AAAA-AAAA-000000000002";
    private static final String OBJECT_ID_3 = "AAAAAAAA-AAAA-AAAA-AAAA-000000000003";
    private static final String DATE_1 = "2010-01-01T00:00:00+00:00";
    private static final String DATE_2 = "2010-01-02T00:00:00+00:00";
    private static final String DATE_3 = "2010-01-03T00:00:00+00:00";

    @SuppressWarnings("unused")
    @PostMapping(value = "/{env_uuid}/{class_uuid}/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public SnapshotsSearchRespBody searchByStreamValue(
            @PathVariable("env_uuid") String envUuid,
            @PathVariable("class_uuid") String classUuid,
            @RequestParam(value = "revision", defaultValue = "", required = false) String revision,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor) {

        try {
            if (!cursor.isEmpty()) throw new IllegalArgumentException("Cursor is not IMPLEMENTED");

            List<SnapshotsObject> objects = new ArrayList<>();
            objects.add(new SnapshotsObject(classUuid, envUuid, OBJECT_ID_1, DATE_1));
            objects.add(new SnapshotsObject(classUuid, envUuid, OBJECT_ID_2, DATE_2));
            objects.add(new SnapshotsObject(classUuid, envUuid, OBJECT_ID_3, DATE_3));
            DataflowMeta meta = new DataflowMeta(limit, cursor, "");
            return new SnapshotsSearchRespBody(objects, meta);
        } catch (Exception e) {
            log.error("Error ", e);
            throw e;
        }
    }
}
