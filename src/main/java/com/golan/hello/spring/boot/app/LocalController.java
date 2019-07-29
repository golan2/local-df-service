package com.golan.hello.spring.boot.app;

import com.golan.hello.spring.registry.ObjectCount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@RequestMapping("/v1")
@RestController
@Slf4j
public class LocalController {


    private static final String ID_ORG_PROJ   = "892daa98-e8dd-11e8-9f32-f2801f1b9fd1";
    private static final String PS_ORG_PROJ   = "{\"apiVersion\":0,\"classes\":{\"vehicle\":{\"name\":\"Vehicle\",\"streams\":{\"packetId\":{\"name\":\"PacketID\",\"type\":\"number\"},\"location\":{\"name\":\"Location\",\"type\":\"geopoint\"},\"deviceId\":{\"name\":\"DeviceID\",\"type\":\"integer\"},\"vin\":{\"name\":\"VIN\",\"type\":\"text\"},\"unixTime\":{\"name\":\"Unix Time\",\"type\":\"number\"},\"reasonCode\":{\"name\":\"Reason Code\",\"type\":\"number\"},\"heading\":{\"name\":\"Heading\",\"type\":\"number\"},\"hdop\":{\"name\":\"Hdop\",\"type\":\"number\"},\"numSats\":{\"name\":\"NumStats\",\"type\":\"number\"},\"vehicleBattVolts\":{\"name\":\"Vehicle Battery Volts\",\"type\":\"number\"},\"gpsLifetimeOdom\":{\"name\":\"GPS Lifetime Odom\",\"type\":\"number\"},\"packetSerialNumber\":{\"name\":\"Packet Serial Number\",\"type\":\"number\"},\"speed\":{\"name\":\"Speed\",\"type\":\"number\"},\"imei\":{\"name\":\"IMEI\",\"type\":\"number\"},\"dtcSize\":{\"name\":\"DTCSize\",\"type\":\"number\"},\"dtcArray\":{\"name\":\"DTCArray\",\"type\":\"number\"},\"acceleration\":{\"name\":\"Acceleration\",\"type\":\"number\"},\"deceleration\":{\"name\":\"Deceleration\",\"type\":\"number\"},\"crashPacketSize\":{\"name\":\"Crash Packet Size\",\"type\":\"number\"},\"crashPacket\":{\"name\":\"Crash Packet\",\"type\":\"number\"},\"idleTime\":{\"name\":\"Idle Time\",\"type\":\"number\"},\"rpm\":{\"name\":\"RPM\",\"type\":\"number\"},\"obdVehicleSpeed\":{\"name\":\"OBD Vehicle Speed\",\"type\":\"number\"},\"rawMessage\":{\"name\":\"Raw hex data from device\",\"type\":\"text\"},\"gpsSpeed\":{\"name\":\"GPS Speed\",\"type\":\"integer\"},\"wakeReason\":{\"name\":\"Wake Reason\",\"type\":\"number\"},\"mil\":{\"name\":\"MIL\",\"type\":\"integer\"},\"ecuCount\":{\"name\":\"ECU Count\",\"type\":\"integer\"},\"ecuID\":{\"name\":\"ECU ID\",\"type\":\"text\"},\"dtcCount\":{\"name\":\"DTC Count\",\"type\":\"integer\"},\"dtcCodes\":{\"name\":\"DTC Codes Object Array\",\"type\":\"text\"}},\"adapter\":{\"template\":\"custom-adapter\",\"publishTo\":\"telemetry\",\"count\":2}},\"error\":{\"name\":\"Error\",\"streams\":{\"message\":{\"name\":\"Message\",\"type\":\"text\"},\"code\":{\"name\":\"Code\",\"type\":\"text\"}}}},\"architecture\":{\"telemetry\":{\"type\":\"input\",\"name\":\"Telemetry\"},\"storage\":{\"type\":\"storage\",\"from\":[\"telemetry\",\"errors\"],\"name\":\"Data Storage\"},\"cartasiteEndpoint\":{\"type\":\"custom\",\"from\":[\"telemetry\"],\"config\":{\"count\":2,\"template\":\"custom-adapter\",\"properties\":{\"cartasiteUrl\":\"${CARTASITE_URL}\"}}},\"rawDatatoXirgo\":{\"type\":\"custom\",\"from\":[\"RawXirgoDataOnOff\"],\"comment\":\"This output is just the raw data so that Xirgo can troubleshoot several issuestwo.\",\"config\":{\"count\":2,\"template\":\"custom-adapter\"}},\"RawXirgoDataOnOff\":{\"type\":\"condition\",\"from\":[\"telemetry\"],\"config\":{\"conditions\":[{\"allOf\":[{\"data.unixTime\":[{\"eq\":\"0\"}]}]}]}},\"errors\":{\"type\":\"input\",\"name\":\"Errors\"}},\"services\":{\"importRegDevices\":{\"template\":\"custom-service\",\"count\":2}},\"env_uuid\":\"" + ID_ORG_PROJ   + "\"}";
    private static final String ID_NO_CLASSES = "43c81429-605c-43c9-a98e-0afc18f89bd0";
    private static final String PS_NO_CLASSES = "{\"apiVersion\":0,\"classes\":{},\"env_uuid\":\""                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         + ID_NO_CLASSES + "\"}";
    private static final String CLASS_VEHICLE = "vehicle";
    private static final String CLASS_ERROR = "error";
    private static final Map<String, ObjectCount> counters = initCounters();

    private static Map<String, ObjectCount> initCounters() {
        final HashMap<String, ObjectCount> result = new HashMap<>();
        result.put(CLASS_VEHICLE, new ObjectCount(CLASS_VEHICLE, 12, null));
        result.put(CLASS_ERROR, new ObjectCount(CLASS_ERROR, 9, null));
        return result;
    }


    @SuppressWarnings("unused")
    @GetMapping(value="/orch/{org}/{project}/spec/revisions/latest/source.json", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getLatestProjectSpec(@PathVariable("org") String org, @PathVariable("project") String project, @RequestParam("with_env_uuid") String with_env_uuid) {
        log.debug("[getLatestProjectSpec] org={} prog={} withEnv={}", org, project, with_env_uuid);

        final String env = org + "_" + project;
        switch (env) {
            case "org_proj":
                return PS_ORG_PROJ;
            case "no_classes":
                return PS_NO_CLASSES;
            default:
                throw new IllegalArgumentException("No such env: " + env);
        }
    }

    @SuppressWarnings("unused")
    @GetMapping(value="/objects/_/~{unv_uuid}/_count", produces= MediaType.APPLICATION_JSON_VALUE)
    public Collection<ObjectCount> countObjects(@PathVariable("unv_uuid") String envUuid) {
        log.debug("[countObjects] envUuid={}", envUuid);
        switch (envUuid) {
            case ID_NO_CLASSES:
                return Collections.emptyList();
            case ID_ORG_PROJ:
                return counters.values();
            default:
                throw new IllegalArgumentException("No such env: " + envUuid);
        }
    }

    @SuppressWarnings("unused")
    @GetMapping(value="/objects/_/~{unv_uuid}/{class}/_count", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<ObjectCount> countObjects(@PathVariable("unv_uuid") String envUuid, @PathVariable("class") String className) {
        log.debug("[countObjects] envUuid={}", envUuid);
        switch (envUuid) {
            case ID_NO_CLASSES:
                return Collections.emptyList();
            case ID_ORG_PROJ:
                return Collections.singletonList(counters.get(className));
            default:
                throw new IllegalArgumentException("No such env: " + envUuid);
        }
    }
}
