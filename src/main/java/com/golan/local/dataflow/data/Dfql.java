package com.golan.local.dataflow.data;

public class Dfql {
    public static final String ID_ORG_PROJ = "892daa98-e8dd-11e8-9f32-f2801f1b9fd1";
    public static final String ID_SHAYBA_DEV = "aea50ab6-32db-11ea-977e-29d880279c99";

    public static final String PS_ORG_PROJ = "" +
            "{\n" +
            "  \"apiVersion\": 0,\n" +
            "  \"classes\": {\n" +
            "    \"vehicle\": {\n" +
            "      \"name\": \"Vehicle\",\n" +
            "      \"streams\": {\n" +
            "        \"packetId\": {\n" +
            "          \"name\": \"PacketID\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"location\": {\n" +
            "          \"name\": \"Location\",\n" +
            "          \"type\": \"geopoint\"\n" +
            "        },\n" +
            "        \"deviceId\": {\n" +
            "          \"name\": \"DeviceID\",\n" +
            "          \"type\": \"integer\"\n" +
            "        },\n" +
            "        \"vin\": {\n" +
            "          \"name\": \"VIN\",\n" +
            "          \"type\": \"text\"\n" +
            "        },\n" +
            "        \"unixTime\": {\n" +
            "          \"name\": \"Unix Time\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"reasonCode\": {\n" +
            "          \"name\": \"Reason Code\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"heading\": {\n" +
            "          \"name\": \"Heading\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"hdop\": {\n" +
            "          \"name\": \"Hdop\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"numSats\": {\n" +
            "          \"name\": \"NumStats\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"vehicleBattVolts\": {\n" +
            "          \"name\": \"Vehicle Battery Volts\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"gpsLifetimeOdom\": {\n" +
            "          \"name\": \"GPS Lifetime Odom\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"packetSerialNumber\": {\n" +
            "          \"name\": \"Packet Serial Number\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"speed\": {\n" +
            "          \"name\": \"Speed\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"speed2\": {\n" +
            "          \"name\": \"Speed\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"speed3\": {\n" +
            "          \"name\": \"Speed\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"speed4\": {\n" +
            "          \"name\": \"Speed\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"slow\": {\n" +
            "          \"name\": \"Slow\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"imei\": {\n" +
            "          \"name\": \"IMEI\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"dtcSize\": {\n" +
            "          \"name\": \"DTCSize\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"dtcArray\": {\n" +
            "          \"name\": \"DTCArray\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"acceleration\": {\n" +
            "          \"name\": \"Acceleration\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"deceleration\": {\n" +
            "          \"name\": \"Deceleration\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"crashPacketSize\": {\n" +
            "          \"name\": \"Crash Packet Size\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"crashPacket\": {\n" +
            "          \"name\": \"Crash Packet\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"idleTime\": {\n" +
            "          \"name\": \"Idle Time\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"rpm\": {\n" +
            "          \"name\": \"RPM\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"obdVehicleSpeed\": {\n" +
            "          \"name\": \"OBD Vehicle Speed\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"rawMessage\": {\n" +
            "          \"name\": \"Raw hex data from device\",\n" +
            "          \"type\": \"text\"\n" +
            "        },\n" +
            "        \"gpsSpeed\": {\n" +
            "          \"name\": \"GPS Speed\",\n" +
            "          \"type\": \"integer\"\n" +
            "        },\n" +
            "        \"wakeReason\": {\n" +
            "          \"name\": \"Wake Reason\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"mil\": {\n" +
            "          \"name\": \"MIL\",\n" +
            "          \"type\": \"integer\"\n" +
            "        },\n" +
            "        \"ecuCount\": {\n" +
            "          \"name\": \"ECU Count\",\n" +
            "          \"type\": \"integer\"\n" +
            "        },\n" +
            "        \"ecuID\": {\n" +
            "          \"name\": \"ECU ID\",\n" +
            "          \"type\": \"text\"\n" +
            "        },\n" +
            "        \"dtcCount\": {\n" +
            "          \"name\": \"DTC Count\",\n" +
            "          \"type\": \"integer\"\n" +
            "        },\n" +
            "        \"dtcCodes\": {\n" +
            "          \"name\": \"DTC Codes Object Array\",\n" +
            "          \"type\": \"text\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"adapter\": {\n" +
            "        \"template\": \"custom-adapter\",\n" +
            "        \"publishTo\": \"telemetry\",\n" +
            "        \"count\": 2\n" +
            "      }\n" +
            "    },\n" +
            "    \"error\": {\n" +
            "      \"name\": \"Error\",\n" +
            "      \"streams\": {\n" +
            "        \"message\": {\n" +
            "          \"name\": \"Message\",\n" +
            "          \"type\": \"text\"\n" +
            "        },\n" +
            "        \"code\": {\n" +
            "          \"name\": \"Code\",\n" +
            "          \"type\": \"text\"\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"reserved\": {\n" +
            "      \"name\": \"reserved\",\n" +
            "      \"streams\": {\n" +
            "        \"timestamp\": {\n" +
            "          \"name\": \"timestamp\",\n" +
            "          \"type\": \"NUMBER\"\n" +
            "        },\n" +
            "        \"object_id\": {\n" +
            "          \"name\": \"object_id\",\n" +
            "          \"type\": \"text\"\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"drone\": {\n" +
            "      \"name\": \"drone\",\n" +
            "      \"streams\": {\n" +
            "        \"connected\": {\n" +
            "          \"name\": \"connected\",\n" +
            "          \"type\": \"boolean\"\n" +
            "        },\n" +
            "        \"location\": {\n" +
            "          \"name\": \"Location\",\n" +
            "          \"type\": \"geopoint\"\n" +
            "        },\n" +
            "        \"battery_pct\": {\n" +
            "          \"name\": \"battery_pct\",\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"status_code\": {\n" +
            "          \"name\": \"status_code\",\n" +
            "          \"type\": \"integer\"\n" +
            "        },\n" +
            "        \"from\": {\n" +
            "          \"name\": \"from\",\n" +
            "          \"type\": \"timestamp\"\n" +
            "        },\n" +
            "        \"check_time\": {\n" +
            "          \"name\": \"check_time\",\n" +
            "          \"type\": \"timestamp\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"architecture\": {\n" +
            "    \"telemetry\": {\n" +
            "      \"type\": \"input\",\n" +
            "      \"name\": \"Telemetry\"\n" +
            "    },\n" +
            "    \"storage\": {\n" +
            "      \"type\": \"storage\",\n" +
            "      \"from\": [\n" +
            "        \"telemetry\",\n" +
            "        \"errors\"\n" +
            "      ],\n" +
            "      \"name\": \"Data Storage\"\n" +
            "    },\n" +
            "    \"cartasiteEndpoint\": {\n" +
            "      \"type\": \"custom\",\n" +
            "      \"from\": [\n" +
            "        \"telemetry\"\n" +
            "      ],\n" +
            "      \"config\": {\n" +
            "        \"count\": 2,\n" +
            "        \"template\": \"custom-adapter\",\n" +
            "        \"properties\": {\n" +
            "          \"cartasiteUrl\": \"${CARTASITE_URL}\"\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"rawDatatoXirgo\": {\n" +
            "      \"type\": \"custom\",\n" +
            "      \"from\": [\n" +
            "        \"RawXirgoDataOnOff\"\n" +
            "      ],\n" +
            "      \"comment\": \"This output is just the raw data so that Xirgo can troubleshoot several issuestwo.\",\n" +
            "      \"config\": {\n" +
            "        \"count\": 2,\n" +
            "        \"template\": \"custom-adapter\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"RawXirgoDataOnOff\": {\n" +
            "      \"type\": \"condition\",\n" +
            "      \"from\": [\n" +
            "        \"telemetry\"\n" +
            "      ],\n" +
            "      \"config\": {\n" +
            "        \"conditions\": [\n" +
            "          {\n" +
            "            \"allOf\": [\n" +
            "              {\n" +
            "                \"data.unixTime\": [\n" +
            "                  {\n" +
            "                    \"eq\": \"0\"\n" +
            "                  }\n" +
            "                ]\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    \"errors\": {\n" +
            "      \"type\": \"input\",\n" +
            "      \"name\": \"Errors\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"services\": {\n" +
            "    \"importRegDevices\": {\n" +
            "      \"template\": \"custom-service\",\n" +
            "      \"count\": 2\n" +
            "    }\n" +
            "  },\n" +
            "  \"env_uuid\": \"892daa98-e8dd-11e8-9f32-f2801f1b9fd1\"\n" +
            "}";


    public static final String ID_NO_CLASSES = "43c81429-605c-43c9-a98e-0afc18f89bd0";
    public static final String PS_NO_CLASSES = "" +
            "{\n" +
            "  \"apiVersion\": 0,\n" +
            "  \"classes\": {\n" +
            "    \n" +
            "  },\n" +
            "  \"env_uuid\": \"43c81429-605c-43c9-a98e-0afc18f89bd0\"\n" +
            "}";


    public static final String ID_NES_COWS = "fab3866e-39f9-4c24-b7d3-11ee2b56bc3b";
    public static final String PS_NES_COWS = "" +
            "{\n" +
            "  \"apiVersion\": 0,\n" +
            "  \"organization\": \"nes\",\n" +
            "  \"identifier\": \"mc-expo\",\n" +
            "  \"env\": \"dev\",\n" +
            "  \"env_uuid\": \"fab3866e-39f9-4c24-b7d3-11ee2b56bc3b\",\n" +
            "  \"uuid\": \"fab3866e-39f9-4c24-b7d3-11ee2b56bc3b\",\n" +
            "  \"classes\": {\n" +
            "    \"cow\": {\n" +
            "      \"streams\": {\n" +
            "        \"location\": {\n" +
            "          \"name\": \"location\",\n" +
            "          \"comment\": \"GPS coordinates\",\n" +
            "          \"type\": \"geopoint\",\n" +
            "          \"uuid\": \"e026300a-43c4-41f9-a501-076e5637af16\"\n" +
            "        },\n" +
            "        \"x_axis\": {\n" +
            "          \"name\": \"x axis\",\n" +
            "          \"comment\": \"X orientation\",\n" +
            "          \"type\": \"number\",\n" +
            "          \"uuid\": \"da22d46d-f105-4be4-bcb1-8ca75ee2a825\"\n" +
            "        },\n" +
            "        \"speed\": {\n" +
            "          \"name\": \"speed\",\n" +
            "          \"comment\": \"X orientation\",\n" +
            "          \"type\": \"number\",\n" +
            "          \"uuid\": \"da22d46d-f105-4be4-bcb1-8ca75ee2a765\"\n" +
            "        },\n" +
            "        \"birthday\": {\n" +
            "          \"name\": \"birthday\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"type\": \"timestamp\",\n" +
            "          \"uuid\": \"05920ccb-94d2-4781-a8df-ab3f4399b46e\"\n" +
            "        },\n" +
            "        \"name\": {\n" +
            "          \"name\": \"name\",\n" +
            "          \"comment\": \"cow name\",\n" +
            "          \"type\": \"text\",\n" +
            "          \"uuid\": \"c87b1406-75e3-409e-bbcb-3943c5d92ff8\"\n" +
            "        },\n" +
            "        \"health\": {\n" +
            "          \"name\": \"Health\",\n" +
            "          \"type\": \"integer\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"f1edbac5-3ddb-41a2-85d1-f1477b792ea1\"\n" +
            "        },\n" +
            "        \"escaped\": {\n" +
            "          \"name\": \"Escaped the fence\",\n" +
            "          \"type\": \"boolean\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"12c69520-efa2-4de5-b78d-9c1d44f82ec3\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"name\": \"\",\n" +
            "      \"comment\": \"\",\n" +
            "      \"has\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"hasMany\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"objectProperties\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"attributes\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"events\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"commands\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"onCreate\": [\n" +
            "        \n" +
            "      ],\n" +
            "      \"uuid\": \"afdda0d9-8494-4a71-8801-a1e831ca4879\"\n" +
            "    },\n" +
            "    \"farm\": {\n" +
            "      \"streams\": {\n" +
            "        \"weather\": {\n" +
            "          \"name\": \"Weather\",\n" +
            "          \"type\": \"text\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"bcdc4942-b9cc-4940-8858-3b6a657afaed\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"name\": \"\",\n" +
            "      \"comment\": \"\",\n" +
            "      \"has\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"hasMany\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"objectProperties\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"attributes\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"events\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"commands\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"onCreate\": [\n" +
            "        \n" +
            "      ],\n" +
            "      \"uuid\": \"b8c3d9f1-a364-46f5-aff2-2bab00eb2532\"\n" +
            "    },\n" +
            "    \"package\": {\n" +
            "      \"streams\": {\n" +
            "        \"contents\": \"text\",\n" +
            "        \"weight\": \"number\",\n" +
            "        \"volume\": \"number\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"entity-adapter\": {\n" +
            "      \"adapter\": {\n" +
            "        \"publishTo\": \"input\",\n" +
            "        \"template\": \"custom-adapter\",\n" +
            "        \"properties\": {\n" +
            "          \"demo\": \"hell\"\n" +
            "        },\n" +
            "        \"count\": 3,\n" +
            "        \"uuid\": \"3ccb5d26-33d2-417f-a9b7-e2058aaa62ca\",\n" +
            "        \"_changes\": {\n" +
            "          \"template\": false,\n" +
            "          \"count\": false,\n" +
            "          \"properties\": false\n" +
            "        }\n" +
            "      },\n" +
            "      \"name\": \"\",\n" +
            "      \"comment\": \"\",\n" +
            "      \"has\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"hasMany\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"objectProperties\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"attributes\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"streams\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"events\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"commands\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"onCreate\": [\n" +
            "        \n" +
            "      ],\n" +
            "      \"uuid\": \"5836c242-4889-4a7b-a09c-d49c8c997f15\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"architecture\": {\n" +
            "    \"input\": {\n" +
            "      \"type\": \"input\"\n" +
            "    },\n" +
            "    \"storage\": {\n" +
            "      \"from\": \"input\"\n" +
            "    },\n" +
            "    \"geofence-violation\": {\n" +
            "      \"type\": \"condition\",\n" +
            "      \"from\": \"input\",\n" +
            "      \"config\": {\n" +
            "        \"conditions\": [\n" +
            "          {\n" +
            "            \"data.escaped\": 2\n" +
            "          },\n" +
            "          {\n" +
            "            \"noneOf\": [\n" +
            "              {\n" +
            "                \"class\": \"enclosure\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"anyOf\": [\n" +
            "              {\n" +
            "                \"data.z_axis\": [\n" +
            "                  {\n" +
            "                    \"lte\": -408\n" +
            "                  }\n" +
            "                ]\n" +
            "              },\n" +
            "              {\n" +
            "                \"data.z_axis\": [\n" +
            "                  {\n" +
            "                    \"gte\": -395\n" +
            "                  }\n" +
            "                ]\n" +
            "              },\n" +
            "              {\n" +
            "                \"data.x_axis\": [\n" +
            "                  {\n" +
            "                    \"lte\": -265\n" +
            "                  }\n" +
            "                ]\n" +
            "              },\n" +
            "              {\n" +
            "                \"data.x_axis\": [\n" +
            "                  {\n" +
            "                    \"gte\": -245\n" +
            "                  }\n" +
            "                ]\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    \"geoSlackAlert\": {\n" +
            "      \"type\": \"slackOutput\",\n" +
            "      \"from\": \"geofence-violation\",\n" +
            "      \"config\": {\n" +
            "        \"webhookUrl\": \"https://hooks.slack.com/services/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\",\n" +
            "        \"text\": \":cow2: @namit @olga FYI - An animal has escaped a geofence: {{object}}\",\n" +
            "        \"channel\": \"#p1-hello-world\",\n" +
            "        \"iconEmoji\": \":cow2:\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"geoWSNotification\": {\n" +
            "      \"type\": \"custom\",\n" +
            "      \"from\": \"geofence-violation\",\n" +
            "      \"config\": {\n" +
            "        \"template\": \"custom-component\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"fireNotification\": {\n" +
            "      \"type\": \"custom\",\n" +
            "      \"from\": \"temperature-violation\",\n" +
            "      \"config\": {\n" +
            "        \"template\": \"custom-component\"\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"services\": {\n" +
            "    \"registry-service\": {\n" +
            "      \"template\": \"custom-adapter\",\n" +
            "      \"count\": 2,\n" +
            "      \"properties\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"uuid\": \"89d70c75-aed1-4e0d-900e-f0316827d008\",\n" +
            "      \"_changes\": {\n" +
            "        \"template\": false,\n" +
            "        \"count\": false,\n" +
            "        \"properties\": false\n" +
            "      }\n" +
            "    },\n" +
            "    \"geoWSNotification.component\": {\n" +
            "      \"template\": \"custom-component\",\n" +
            "      \"count\": 2,\n" +
            "      \"properties\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"comment\": \"\",\n" +
            "      \"uuid\": \"95ce1693-e87b-47f9-8fb6-8f970e9a2779\",\n" +
            "      \"_changes\": {\n" +
            "        \"template\": false,\n" +
            "        \"count\": false,\n" +
            "        \"properties\": false\n" +
            "      }\n" +
            "    },\n" +
            "    \"geoWSRestNotif.component\": {\n" +
            "      \"template\": \"custom-component\",\n" +
            "      \"count\": 2,\n" +
            "      \"properties\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"comment\": \"\",\n" +
            "      \"uuid\": \"4f74bc18-9e61-4524-be6f-fa10842b4304\",\n" +
            "      \"_changes\": {\n" +
            "        \"template\": false,\n" +
            "        \"count\": false,\n" +
            "        \"properties\": false\n" +
            "      }\n" +
            "    },\n" +
            "    \"fireNotification.component\": {\n" +
            "      \"template\": \"custom-component\",\n" +
            "      \"count\": 2,\n" +
            "      \"properties\": {\n" +
            "        \n" +
            "      },\n" +
            "      \"comment\": \"\",\n" +
            "      \"uuid\": \"3fe112c0-2329-4d4b-9250-4622ac7ef2f1\",\n" +
            "      \"_changes\": {\n" +
            "        \"template\": false,\n" +
            "        \"count\": false,\n" +
            "        \"properties\": false\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"actions\": {\n" +
            "    \n" +
            "  },\n" +
            "  \"events\": {\n" +
            "    \n" +
            "  },\n" +
            "  \"revision\": \"412d0aaf5b1d31f72234b27a3b2093614fd90952\",\n" +
            "  \"deletions\": [\n" +
            "    \n" +
            "  ]\n" +
            "}";


    //Project Spec
    public static final String PS_MORMONT_SHAYBA = "" +
            "{\n" +
            "  \"apiVersion\": 0,\n" +
            "  \"classes\": {\n" +
            "    \"drone\": {\n" +
            "      \"streams\": {\n" +
            "        \"speed\": {\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"location\": {\n" +
            "          \"type\": \"geopoint\"\n" +
            "        },\n" +
            "        \"message\": {\n" +
            "          \"type\": \"text\"\n" +
            "        },\n" +
            "        \"acSystemRefrigerantMonitorIndicator\": {\n" +
            "          \"name\": \"acSystemRefrigerantMonitorIndicator\",\n" +
            "          \"type\": \"text\"\n" +
            "        },\n" +
            "        \"fast\": {\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"slow\": {\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"timestamp\": {\n" +
            "          \"type\": \"timestamp\"\n" +
            "        },\n" +
            "        \"check_time\": {\n" +
            "          \"type\": \"timestamp\"\n" +
            "        },\n" +
            "        \"connected\": {\n" +
            "          \"type\": \"boolean\"\n" +
            "        },\n" +
            "        \"status_code\": {\n" +
            "          \"type\": \"integer\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"events\": {\n" +
            "        \"speed2high\": {\n" +
            "          \"data\": {\n" +
            "            \"speed\": {\n" +
            "              \"type\": \"number\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"adapter\": {\n" +
            "        \"template\": \"custom-adapter\",\n" +
            "        \"publishTo\": \"input\",\n" +
            "        \"count\": 1\n" +
            "      }\n" +
            "    },\n" +
            "    \"izik\": {\n" +
            "      \"streams\": {\n" +
            "        \"speed\": {\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"where_i_am\": {\n" +
            "          \"type\": \"geopoint\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"adapter\": {\n" +
            "        \"template\": \"custom-adapter\",\n" +
            "        \"publishTo\": \"input\",\n" +
            "        \"count\": 2\n" +
            "      }\n" +
            "    },\n" +
            "    \"reserved\": {\n" +
            "      \"streams\": {\n" +
            "        \"timestamp\": {\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"object_id\": {\n" +
            "          \"type\": \"text\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"architecture\": {\n" +
            "    \"input\": {\n" +
            "      \"type\": \"input\"\n" +
            "    },\n" +
            "    \"storage\": {\n" +
            "      \"type\": \"storage\",\n" +
            "      \"from\": [\n" +
            "        \"input\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  \"env_uuid\": \"aea50ab6-32db-11ea-977e-29d880279c99\"\n" +
            "}";


    public static final String CLASS_UUID_DRONE = "ae80f688-fca2-467c-8605-86d367c9640c";

    //Compiled Spec
    public static final String CS_MORMONT_SHAYBA = "" +
            "{\n" +
            "  \"apiVersion\": 0,\n" +
            "  \"classes\": {\n" +
            "    \"drone\": {\n" +
            "      \"streams\": {\n" +
            "        \"speed\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"6af5cbdc-7fda-426d-b9fc-c7d59d86527c\"\n" +
            "        },\n" +
            "        \"location\": {\n" +
            "          \"type\": \"geopoint\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"98bcd4b8-f6e8-4dc4-be12-00bcd15ae58f\"\n" +
            "        },\n" +
            "        \"message\": {\n" +
            "          \"type\": \"text\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"80707908-c8ff-4779-a0da-d26bcd11c390\"\n" +
            "        },\n" +
            "        \"fast\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"ebe85315-2eed-456e-a2fd-cb03a9926556\"\n" +
            "        },\n" +
            "        \"slow\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"71b902ae-df7c-4bcb-8d97-995754fd9b69\"\n" +
            "        },\n" +
            "        \"timestamp\": {\n" +
            "          \"type\": \"timestamp\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"c6bf792b-e39c-4ac1-a84b-2031b06fe3b9\"\n" +
            "        },\n" +
            "        \"check_time\": {\n" +
            "          \"type\": \"timestamp\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"c23d044a-219c-49b7-bab3-fe50d631b131\"\n" +
            "        },\n" +
            "        \"connected\": {\n" +
            "          \"type\": \"boolean\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"1ce54de4-d039-4279-bef0-ed23b1ab891b\"\n" +
            "        },\n" +
            "        \"status_code\": {\n" +
            "          \"type\": \"integer\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"66340a9b-a45d-43c4-9c67-d99d1a2ad867\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"events\": {\n" +
            "        \"speed2high\": {\n" +
            "          \"data\": {\n" +
            "            \"speed\": {\n" +
            "              \"type\": \"number\",\n" +
            "              \"name\": \"\",\n" +
            "              \"comment\": \"\",\n" +
            "              \"uuid\": \"51b535f6-6745-4e4d-82a7-2cbbdc231980\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"1f958630-af8e-45cc-abf6-d736513e60d4\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"adapter\": {\n" +
            "        \"template\": \"custom-adapter\",\n" +
            "        \"publishTo\": \"input\",\n" +
            "        \"count\": 1,\n" +
            "        \"properties\": {},\n" +
            "        \"uuid\": \"548f05a8-d5ac-43e8-8151-eff9ec2a5a7d\",\n" +
            "        \"_changes\": {\n" +
            "          \"template\": false,\n" +
            "          \"count\": false,\n" +
            "          \"properties\": false\n" +
            "        }\n" +
            "      },\n" +
            "      \"name\": \"\",\n" +
            "      \"comment\": \"\",\n" +
            "      \"has\": {},\n" +
            "      \"hasMany\": {},\n" +
            "      \"objectProperties\": {},\n" +
            "      \"attributes\": {},\n" +
            "      \"commands\": {},\n" +
            "      \"onCreate\": [],\n" +
            "      \"uuid\": \"ae80f688-fca2-467c-8605-86d367c9640c\"\n" +
            "    },\n" +
            "    \"izik\": {\n" +
            "      \"streams\": {\n" +
            "        \"speed\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"b3869369-4740-477c-9bc9-33be3f2fdc49\"\n" +
            "        },\n" +
            "        \"where_i_am\": {\n" +
            "          \"type\": \"geopoint\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"3bf772b3-0bd2-4293-8de5-23ad9db3dc11\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"adapter\": {\n" +
            "        \"template\": \"custom-adapter\",\n" +
            "        \"publishTo\": \"input\",\n" +
            "        \"count\": 2,\n" +
            "        \"properties\": {},\n" +
            "        \"uuid\": \"7d64b8e8-9e15-4e4c-af3d-402935d04620\",\n" +
            "        \"_changes\": {\n" +
            "          \"template\": false,\n" +
            "          \"count\": false,\n" +
            "          \"properties\": false\n" +
            "        }\n" +
            "      },\n" +
            "      \"name\": \"\",\n" +
            "      \"comment\": \"\",\n" +
            "      \"has\": {},\n" +
            "      \"hasMany\": {},\n" +
            "      \"objectProperties\": {},\n" +
            "      \"attributes\": {},\n" +
            "      \"events\": {},\n" +
            "      \"commands\": {},\n" +
            "      \"onCreate\": [],\n" +
            "      \"uuid\": \"7ed73da1-9457-476e-8e61-e185c5d1dbb0\"\n" +
            "    },\n" +
            "    \"reserved\": {\n" +
            "      \"streams\": {\n" +
            "        \"timestamp\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"96fce606-663d-45b1-a342-5297d19ab567\"\n" +
            "        },\n" +
            "        \"object_id\": {\n" +
            "          \"type\": \"text\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"56f91abb-76a2-4626-85b3-3a4e64557881\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"name\": \"\",\n" +
            "      \"comment\": \"\",\n" +
            "      \"has\": {},\n" +
            "      \"hasMany\": {},\n" +
            "      \"objectProperties\": {},\n" +
            "      \"attributes\": {},\n" +
            "      \"events\": {},\n" +
            "      \"commands\": {},\n" +
            "      \"onCreate\": [],\n" +
            "      \"uuid\": \"8a24f45e-06c3-4c55-a3fe-2aabdbce4627\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"architecture\": {\n" +
            "    \"input\": {\n" +
            "      \"type\": \"input\",\n" +
            "      \"name\": \"\",\n" +
            "      \"comment\": \"\",\n" +
            "      \"config\": {},\n" +
            "      \"from\": [],\n" +
            "      \"to\": [\n" +
            "        \"storage\"\n" +
            "      ]\n" +
            "    },\n" +
            "    \"storage\": {\n" +
            "      \"type\": \"storage\",\n" +
            "      \"from\": [\n" +
            "        \"input\"\n" +
            "      ],\n" +
            "      \"name\": \"\",\n" +
            "      \"comment\": \"\",\n" +
            "      \"config\": {},\n" +
            "      \"to\": []\n" +
            "    }\n" +
            "  },\n" +
            "  \"services\": {},\n" +
            "  \"uuid\": \"aea509bc-32db-11ea-977e-29d880279c99\",\n" +
            "  \"env_uuid\": \"aea50ab6-32db-11ea-977e-29d880279c99\",\n" +
            "  \"organization\": \"mormont\",\n" +
            "  \"identifier\": \"shayba\",\n" +
            "  \"env\": \"dev\",\n" +
            "  \"revision\": \"08d0b7069d491cb764a65242c5d55412f3f431ba\",\n" +
            "  \"deletions\": []\n" +
            "}";
}
