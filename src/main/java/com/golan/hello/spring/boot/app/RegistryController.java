package com.golan.hello.spring.boot.app;


import com.golan.hello.spring.registry.Meta;
import com.golan.hello.spring.registry.ObjectCount;
import com.golan.hello.spring.registry.ObjectsResponse;
import com.golan.hello.spring.registry.RegObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/v1/objects")
@RestController
@Slf4j
public class RegistryController {
    private static final String CLASS_VEHICLE = "vehicle";
    private static final String CLASS_ERROR = "error";
    private static final String CLASS_COW = "cow";

    private static final Map<UUID, Map<String, ObjectCount>> DFQL_OBJECT_COUNT_PER_CLASS = initDfqlObjectCountPerClass();

    private static Map<UUID, Map<String, ObjectCount>> initDfqlObjectCountPerClass() {

        Map<UUID, Map<String, ObjectCount>> result = new HashMap<>();

        final HashMap<String, ObjectCount> orgProj = new HashMap<>();
        orgProj.put(CLASS_VEHICLE, new ObjectCount(CLASS_VEHICLE, 12, null));
        orgProj.put(CLASS_ERROR, new ObjectCount(CLASS_ERROR, 9, null));
        result.put(UUID.fromString(Dfql.ID_ORG_PROJ), orgProj);

        result.put(UUID.fromString(Dfql.ID_NES_COWS), Collections.singletonMap(CLASS_COW, new ObjectCount(CLASS_COW, 7, null)));

        result.put(UUID.fromString(Dfql.ID_NO_CLASSES), Collections.emptyMap());

        return result;
    }


    @SuppressWarnings("unused")
    @GetMapping(value = "/{org}/{proj}~{env}/{clazz}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectsResponse getObjectsOfClass(
            @PathVariable("org") String org,
            @PathVariable("proj") String proj,
            @PathVariable("env") String env,
            @PathVariable("clazz") String clazz,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit,
            @RequestParam(value = "cursor", defaultValue = "", required = false) String cursor) {

        final OrgProj orgProj = new OrgProj(org, proj);
        final List<RegObject> allObjects;
        if (WhiteRaven.match(orgProj, env)) {
            allObjects = WhiteRaven.getObjectsOfClass(orgProj, env, clazz);
        }
        else {
            allObjects = Collections.emptyList();
        }
        final ArrayList<RegObject> objects = new ArrayList<>(limit);

        final int startIndex = Paging.startIndex(cursor);
        final int lastIndex = Paging.lastIndex(startIndex, limit, allObjects.size());
        final List<RegObject> thisBulk = Paging.thisBulk(allObjects, startIndex, lastIndex);
        final Meta meta = Paging.meta(limit, cursor, allObjects.size(), lastIndex);

        final ObjectsResponse objectsResponse = new ObjectsResponse();
        objectsResponse.setMeta(meta);
        objectsResponse.setObjects(thisBulk);
        return objectsResponse;
    }

    @SuppressWarnings("unused")
    @GetMapping(value = "/_/~{unv_uuid}/_count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ObjectCount> countObjects(@PathVariable("unv_uuid") String envUuid) {
        log.debug("[countObjects] envUuid={}", envUuid);
        final Map<String, ObjectCount> objectCountPerClass = DFQL_OBJECT_COUNT_PER_CLASS.get(UUID.fromString(envUuid));
        if (objectCountPerClass == null) {
            throw new IllegalArgumentException("No such env: " + envUuid);
        } else if (objectCountPerClass.isEmpty()) {
            return Collections.emptyList();
        } else {
            return objectCountPerClass.values();
        }
    }


    @SuppressWarnings("unused")
    @GetMapping(value = "/_/~{unv_uuid}/{class}/_count", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ObjectCount> countObjects(@PathVariable("unv_uuid") String envUuid, @PathVariable("class") String className) {
        log.debug("[countObjects] envUuid={}", envUuid);
        final Map<String, ObjectCount> objectCountPerClass = DFQL_OBJECT_COUNT_PER_CLASS.get(UUID.fromString(envUuid));
        if (objectCountPerClass == null) {
            throw new IllegalArgumentException("No such env: " + envUuid);
        } else if (objectCountPerClass.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(objectCountPerClass.get(className));
        }
    }
}
