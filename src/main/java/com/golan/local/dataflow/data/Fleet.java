package com.golan.local.dataflow.data;

import java.util.UUID;

public class Fleet {
    public static final String ENV_DEV = "db01c146-8f68-11ea-a4eb-6398166be91d";
    public static final String ENV_PROD = "db01c0ec-8f68-11ea-a4eb-6398166be91d";
    public static final String PROJ = "fleet-trucks-iot";
    public static final String ORG = "fleet";


    private static final String COMPILED_SPEC = constructCompiledSpec();

    public static final String PROJECT_SPEC = constructProjectSpec();
    public static final String COMPILED_SPEC_DEV = String.format(COMPILED_SPEC, ENV_DEV);
    public static final String COMPILED_SPEC_PROD = String.format(COMPILED_SPEC, ENV_PROD);
    private static final String CB_URL_1 = "runi.flow.cloud";
    private static final String CB_URL_2 = "att";
    private static final String CB_URL_3 = "com";
    private static final String CB_URL = CB_URL_1 + "." + CB_URL_2 + "."  + CB_URL_3;

    private static String constructProjectSpec() {
        return "" +
                "{\n" +
                "  \"apiVersion\": 0,\n" +
                "  \"architecture\": {\n" +
                "    \"input\": {},\n" +
                "    \"storage\": {\n" +
                "      \"type\": \"storage\",\n" +
                "      \"from\": [\n" +
                "        \"ProdSoutheastInput\",\n" +
                "        \"ProdMidwestInput\",\n" +
                "        \"ProdWestInput\",\n" +
                "        \"NonCollectionInput\",\n" +
                "        \"GeotabAndProfessorInput\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"ProdSoutheastInput\": {\n" +
                "      \"type\": \"input\"\n" +
                "    },\n" +
                "    \"ProdMidwestInput\": {\n" +
                "      \"type\": \"input\"\n" +
                "    },\n" +
                "    \"ProdWestInput\": {\n" +
                "      \"type\": \"input\"\n" +
                "    },\n" +
                "    \"NonCollectionInput\": {\n" +
                "      \"type\": \"input\"\n" +
                "    },\n" +
                "    \"GeotabAndProfessorInput\": {\n" +
                "      \"type\": \"input\"\n" +
                "    },\n" +
                "    \"post-ingest\": {\n" +
                "      \"type\": \"custom\",\n" +
                "      \"from\": [\n" +
                "        \"NonCollectionInput\",\n" +
                "        \"ProdWestInput\",\n" +
                "        \"ProdMidwestInput\",\n" +
                "        \"ProdSoutheastInput\",\n" +
                "        \"GeotabAndProfessorInput\"\n" +
                "      ],\n" +
                "      \"name\": \"Post Ingest\",\n" +
                "      \"comment\": \"Will process the message, on a custom component flow, after the ingestion from the tracker Calamp adapter\",\n" +
                "      \"config\": {\n" +
                "        \"count\": 8,\n" +
                "        \"template\": \"custom-component\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"classes\": {\n" +
                "    \"collection\": {\n" +
                "      \"name\": \"collection\",\n" +
                "      \"comment\": \"creating groups for objects relatioship\",\n" +
                "      \"hasMany\": [\n" +
                "        \"tracker\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"tracker\": {\n" +
                "      \"name\": \"Tracker\",\n" +
                "      \"comment\": \"vehicle in the fleet, the device reports stream data, and is assigned to a vehicle and a region.\",\n" +
                "      \"hasMany\": [\n" +
                "        \"collection\"\n" +
                "      ],\n" +
                "      \"streams\": {\n" +
                "        \"location\": {\n" +
                "          \"type\": \"geopoint\"\n" +
                "        },\n" +
                "        \"m2xMessageID\": {\n" +
                "          \"name\": \"M2X Message ID\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"barometricPressure\": {\n" +
                "          \"name\": \"barometricPressure\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"milesPerGallon\": {\n" +
                "          \"name\": \"milesPerGallon\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"queryID\": {\n" +
                "          \"name\": \"queryID\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"region\": {\n" +
                "          \"name\": \"region\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"messageType\": {\n" +
                "          \"name\": \"messageType\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"appMsgType\": {\n" +
                "          \"name\": \"appMsgType\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"complete\": {\n" +
                "          \"name\": \"complete\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"MESSAGE_IN_BYTES\": {\n" +
                "          \"name\": \"MESSAGE_IN_BYTES\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"transmissionFluidTemperature\": {\n" +
                "          \"name\": \"transmissionFluidTemperature\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"timeStamp\": {\n" +
                "          \"name\": \"Timestamp\",\n" +
                "          \"type\": \"timestamp\"\n" +
                "        },\n" +
                "        \"engineRunTime\": {\n" +
                "          \"name\": \"engineRunTime\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"messageSequenceNumber\": {\n" +
                "          \"name\": \"Message Sequence Number\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"processedAt\": {\n" +
                "          \"name\": \"Processed At\",\n" +
                "          \"type\": \"timestamp\"\n" +
                "        },\n" +
                "        \"timeofFix\": {\n" +
                "          \"name\": \"Time Of Fix\",\n" +
                "          \"type\": \"timestamp\"\n" +
                "        },\n" +
                "        \"updateTime\": {\n" +
                "          \"name\": \"Update Time\",\n" +
                "          \"type\": \"timestamp\"\n" +
                "        },\n" +
                "        \"clientId\": {\n" +
                "          \"name\": \"Client ID\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"phoneNumber\": {\n" +
                "          \"name\": \"Phone Number\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"mobileID\": {\n" +
                "          \"name\": \"Mobile ID\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"firmwareScriptVersion\": {\n" +
                "          \"name\": \"Firmware Script Version\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"mobileIdEnabled\": {\n" +
                "          \"name\": \"Mobile ID Enabled?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"mobileIdTypeEnabled\": {\n" +
                "          \"name\": \"Mobile ID Type Enabled?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"mobileIDType\": {\n" +
                "          \"name\": \"Moble ID Type\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"dataService\": {\n" +
                "          \"name\": \"Data Service\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"firmware\": {\n" +
                "          \"name\": \"Firmware\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"ICCID\": {\n" +
                "          \"name\": \"ICCID\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"assetIdentifier\": {\n" +
                "          \"name\": \"Asset Identifier\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"vinSource\": {\n" +
                "          \"name\": \"VIN Source\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"locationFlag\": {\n" +
                "          \"name\": \"Location Flag\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"street\": {\n" +
                "          \"name\": \"Street\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"connected\": {\n" +
                "          \"name\": \"Connected?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"networkService\": {\n" +
                "          \"name\": \"Network Service?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"roaming\": {\n" +
                "          \"name\": \"Roaming?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"forwardingEnabled\": {\n" +
                "          \"name\": \"Forwarding Enabled?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"eventIndex\": {\n" +
                "          \"name\": \"Event Index\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"calAmpEventCode\": {\n" +
                "          \"name\": \"CalAmp Event Code\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"vtsEvent\": {\n" +
                "          \"name\": \"VTS Event\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"altitude\": {\n" +
                "          \"name\": \"Altitude\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"idleTime\": {\n" +
                "          \"name\": \"Idle Time\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"speed\": {\n" +
                "          \"name\": \"Speed\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"rollspeed\": {\n" +
                "          \"name\": \"Roll Speed\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"heading\": {\n" +
                "          \"name\": \"Heading\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"lgpDate\": {\n" +
                "          \"name\": \"LGP Date\",\n" +
                "          \"type\": \"timestamp\"\n" +
                "        },\n" +
                "        \"RSSI\": {\n" +
                "          \"name\": \"RSSI\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"HDOP\": {\n" +
                "          \"name\": \"HDOP\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"satellites\": {\n" +
                "          \"name\": \"Satellites\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"serviceType\": {\n" +
                "          \"name\": \"Service Type\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"carrier\": {\n" +
                "          \"name\": \"Carrier\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"deviceInputIgnition\": {\n" +
                "          \"name\": \"Ignition?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"gpsInvalidFix\": {\n" +
                "          \"name\": \"GPS Invalid Fix?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"odometer\": {\n" +
                "          \"name\": \"Odometer\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"tripOdometer\": {\n" +
                "          \"name\": \"Trip Odometer\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"distanceTravelledSinceLastPowerWakeUp\": {\n" +
                "          \"name\": \"Distance Travelled Since Last Power Wake Up\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"derivedMileage\": {\n" +
                "          \"name\": \"Derived Mileage\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"authenticationWordEnabled\": {\n" +
                "          \"name\": \"Authentication Word Enabled?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"routingEnabled\": {\n" +
                "          \"name\": \"Routing Enabled?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"UMTS\": {\n" +
                "          \"name\": \"UMTS\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"twoDimFix\": {\n" +
                "          \"name\": \"Two Dim Fix?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"lastKnown\": {\n" +
                "          \"name\": \"Last Known?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"vehicleVoltage10V\": {\n" +
                "          \"name\": \"vehicleVoltage10V\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"vehicleVoltage7_9V\": {\n" +
                "          \"name\": \"vehicleVoltage7_9V\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"vehicleVoltage9V\": {\n" +
                "          \"name\": \"vehicleVoltage9V\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"alwaysEnabled\": {\n" +
                "          \"name\": \"Always Enabled?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"optionsExtensionEnabled\": {\n" +
                "          \"name\": \"Options Extension Enabled?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"historic\": {\n" +
                "          \"name\": \"Historic?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"predicted\": {\n" +
                "          \"name\": \"Predicted?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"available\": {\n" +
                "          \"name\": \"Available?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"gpsReceiverTrackingError\": {\n" +
                "          \"name\": \"GPS Receiver Tracking Error?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"invalidTime\": {\n" +
                "          \"name\": \"Invalid Time?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"voiceCallIsActive\": {\n" +
                "          \"name\": \"Voice Call Active?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"responseRedirectionEnabled\": {\n" +
                "          \"name\": \"Response Redirection Enabled?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"accums\": {\n" +
                "          \"name\": \"Accumulations\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"lmu32HttpOtaUpdateStatusError\": {\n" +
                "          \"name\": \"LMU 32 HTTP OTA Update Status Error?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"spare\": {\n" +
                "          \"name\": \"Spare\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"timeFromPowerUp\": {\n" +
                "          \"name\": \"Time Since Power Up\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"vehicleSpeed\": {\n" +
                "          \"name\": \"Vehicle Speed\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"engineSpeed\": {\n" +
                "          \"name\": \"Engine Speed\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"malfunctionIndicatorLampStatusIndicator\": {\n" +
                "          \"name\": \"Malfunction Indicator Lamp Status Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"powerTakeOffsStatusIndicator\": {\n" +
                "          \"name\": \"Power Take Offs Status Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"b" + CB_URL_2 + "eryVoltageFrom12V\": {\n" +
                "          \"name\": \"B" + CB_URL_2 + "ery Voltage from 12V\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"secondaryAirSystemMonitorIndicator\": {\n" +
                "          \"name\": \"Secondary Air System Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"gpsReceiverSelfTestError\": {\n" +
                "          \"name\": \"GPS Receiver Self Test Error\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"exhaustGasrecirculationSystemMonitorIndicator\": {\n" +
                "          \"name\": \"Exhaust Gas Recirculation System Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"airbagDashIndicator\": {\n" +
                "          \"name\": \"Airbag Dash Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"acSystemRefrigerantMonitorIndicator\": {\n" +
                "          \"name\": \"A/C System Refrigerant Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"engineCoolantTemp\": {\n" +
                "          \"name\": \"Engine Coolant Temperature\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"fuelLevelVolume\": {\n" +
                "          \"name\": \"Fuel Level Volume\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"fuelSystemMonitorIndicator\": {\n" +
                "          \"name\": \"Fuel System Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"fuelLevelRemaining\": {\n" +
                "          \"name\": \"Fuel Level Remaining\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"tripFuelConsumption\": {\n" +
                "          \"name\": \"Trip Fuel Consumption\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"catalystMonitorIndicator\": {\n" +
                "          \"name\": \"Catalyst Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"throttlePosition\": {\n" +
                "          \"name\": \"Throttle Position\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"evaporativeSystemMonitorIndicator\": {\n" +
                "          \"name\": \"Evaporative System Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"b" + CB_URL_2 + "eryVoltage\": {\n" +
                "          \"name\": \"B" + CB_URL_2 + "ery Voltage\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"oxygenSensorHeaterMonitorIndicator\": {\n" +
                "          \"name\": \"Oxygen Sensor Heater Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"comprehensiveComponentMonitorIndicator\": {\n" +
                "          \"name\": \"Comprehensive Component Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"oxygenSensorMonitorIndicator\": {\n" +
                "          \"name\": \"Oxygen Sensor Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"heatedCatalystMonitorIndicator\": {\n" +
                "          \"name\": \"Heated Catalyst Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"coolantHotLightIndicator\": {\n" +
                "          \"name\": \"Coolant Hot Light Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"differntiallyCorrected\": {\n" +
                "          \"name\": \"Differentially Corrected\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"seatbeltFastenedIndicator\": {\n" +
                "          \"name\": \"Seatbelt Fastened Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"misfireMonitorIndicator\": {\n" +
                "          \"name\": \"Misfire Monitor Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"gpsAntennaStatusError\": {\n" +
                "          \"name\": \"GPS Antenna Status Error?\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"oilLifeRemaining\": {\n" +
                "          \"name\": \"Oil Life Remaining\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"rawData\": {\n" +
                "          \"name\": \"Raw Data\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"diagnosticCodes\": {\n" +
                "          \"name\": \"Diagnostic Codes\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"brakePedalStatus\": {\n" +
                "          \"name\": \"Brake Pedal Status\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"cruiseControlStatus\": {\n" +
                "          \"name\": \"Cruise Control Status\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"parkBrakeLightIndicator\": {\n" +
                "          \"name\": \"Park Brake Light Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"oilPressureLamp\": {\n" +
                "          \"name\": \"Oil Pressure Lamp\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"absDashIndicator\": {\n" +
                "          \"name\": \"ABS Dash Indicator\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"absActiveLamp\": {\n" +
                "          \"name\": \"ABS Active Lamp\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"tpmsStatus\": {\n" +
                "          \"name\": \"Tire Pressure Monitoring System Status\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"ambientAirTemp\": {\n" +
                "          \"name\": \"Ambient Air Temperature\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"TMPS1LA\": {\n" +
                "          \"name\": \"TMPS1LA\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"TMPS1RA\": {\n" +
                "          \"name\": \"TMPS1RA\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"TMPS2LA\": {\n" +
                "          \"name\": \"TMPS2LA\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"TMPS2LB\": {\n" +
                "          \"name\": \"TMPS2LB\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"TMPS2RB\": {\n" +
                "          \"name\": \"TMPS2RB\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"TMPS2RA\": {\n" +
                "          \"name\": \"TMPS2RA\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"tirePressure1LA\": {\n" +
                "          \"name\": \"tirePressure1LA\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"tirePressure1RA\": {\n" +
                "          \"name\": \"tirePressure1RA\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"tirePressure2LA\": {\n" +
                "          \"name\": \"tirePressure2LA\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"tirePressure2LB\": {\n" +
                "          \"name\": \"tirePressure2LB\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"tirePressure2RB\": {\n" +
                "          \"name\": \"tirePressure2RB\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"tirePressure2RA\": {\n" +
                "          \"name\": \"tirePressure2RA\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"highTransmission\": {\n" +
                "          \"name\": \"Transmission\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"lostExternalPower\": {\n" +
                "          \"name\": \"Lost External Power\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"input7\": {\n" +
                "          \"name\": \"Input 7\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"input6\": {\n" +
                "          \"name\": \"Input 6\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"input5\": {\n" +
                "          \"name\": \"Input 5\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"input4\": {\n" +
                "          \"name\": \"Input 4\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"input3\": {\n" +
                "          \"name\": \"Input 3\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"input2\": {\n" +
                "          \"name\": \"Input 2\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"input1\": {\n" +
                "          \"name\": \"Input 1\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"o2SensorCircuitNoActivity\": {\n" +
                "          \"name\": \"O2 Sensor Circuit No Activity\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"o2SensorHeaterCircuitMalfunction\": {\n" +
                "          \"name\": \"O2 Sensor Circuit Malfunction\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"ho2sHeaterControlMalfunction\": {\n" +
                "          \"name\": \"HO2S Heater Control Malfunction\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"ho2sHeaterResistanceMalfunction\": {\n" +
                "          \"name\": \"HO2S Heater Resistance Malfunction\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"engineOilTemp\": {\n" +
                "          \"name\": \"Engine Oil Temperature\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"oxygenSensorNoActivityDetected\": {\n" +
                "          \"name\": \"oxygenSensorNoActivityDetected\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"oxygenSensorHeaterMalfunction\": {\n" +
                "          \"name\": \"oxygenSensorHeaterMalfunction\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"signalStatus\": {\n" +
                "          \"name\": \"signalStatus\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"fuelRate\": {\n" +
                "          \"name\": \"fuelRate\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"brakeSwitchStatus\": {\n" +
                "          \"name\": \"brakeSwitchStatus\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"deviceType\": {\n" +
                "          \"name\": \"Device Type\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"ignitionStatus\": {\n" +
                "          \"name\": \"Ignition Status\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"timeBetweenIgnitionOnAndVehicleMoving\": {\n" +
                "          \"name\": \"Time Between Ignition On and Vehicle Moving\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"indicator0\": {\n" +
                "          \"name\": \"indicator0\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"indicator1\": {\n" +
                "          \"name\": \"indicator1\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"calculatedFuelUsage\": {\n" +
                "          \"name\": \"Calculated Fuel Usage\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"diagnosticsReportType\": {\n" +
                "          \"name\": \"Diagnostics Report Type\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"numberDiagnosticsReported\": {\n" +
                "          \"name\": \"Number of Diagnostics Reported\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"proto\": {\n" +
                "          \"name\": \"Protocol\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"params\": {\n" +
                "          \"name\": \"Params\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"indctrs\": {\n" +
                "          \"name\": \"Indicators\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"b" + CB_URL_2 + "eryVoltageFrom10V\": {\n" +
                "          \"name\": \"B" + CB_URL_2 + "ery Voltage from 10V\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"timeSinceDevicePowerWakeUp\": {\n" +
                "          \"name\": \"Time Since Device Power Wake Up\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"maxSpeed\": {\n" +
                "          \"name\": \"Maximum Speed\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"positionAccuracy\": {\n" +
                "          \"name\": \"Position Accuracy\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"numberOfPowerWakeUpTimes\": {\n" +
                "          \"name\": \"Number of Power Wake Up Times\",\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"userMsg\": {\n" +
                "          \"name\": \"userMsg\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"userMsgLen\": {\n" +
                "          \"name\": \"userMsgLen\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"userMsgID\": {\n" +
                "          \"name\": \"userMsgID\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"userMsgRoute\": {\n" +
                "          \"name\": \"userMsgRoute\",\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"bb_configured\": {\n" +
                "          \"name\": \"bb_configured\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"Prod-southeast\": {\n" +
                "          \"name\": \"Prod-southeast\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"Prod-west\": {\n" +
                "          \"name\": \"Prod-west\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"Prod-midwest\": {\n" +
                "          \"name\": \"Prod-midwestt\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"Geotab\": {\n" +
                "          \"name\": \"Geotab\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"m2xDeviceID\": {\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"subRegion\": {\n" +
                "          \"name\": \"Sub Region\",\n" +
                "          \"comment\": \"current device sub-region for AT&T fleet trucks. Used by VTS and taken from the tracker collection\",\n" +
                "          \"type\": \"text\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"" + CB_URL_2 + "ributes\": {\n" +
                "        \"geotabid\": {\n" +
                "          \"name\": \"geotabid\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"oldGeotabId\": {\n" +
                "          \"name\": \"GeotabId on retirement\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"tech_data\": {\n" +
                "          \"name\": \"tech_data\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"vin_data\": {\n" +
                "          \"name\": \"vin_data\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"random\": {\n" +
                "          \"name\": \"random\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"retire_date\": {\n" +
                "          \"name\": \"Retiring date\",\n" +
                "          \"type\": \"timestamp\"\n" +
                "        },\n" +
                "        \"oldVid\": {\n" +
                "          \"name\": \"vID before retiring\",\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"surplus_date\": {\n" +
                "          \"name\": \"Surplused date\",\n" +
                "          \"type\": \"timestamp\"\n" +
                "        },\n" +
                "        \"depot_date\": {\n" +
                "          \"name\": \"Depot date\",\n" +
                "          \"type\": \"timestamp\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"commands\": {\n" +
                "        \"vehicleRedetect\": {}\n" +
                "      },\n" +
                "      \"adapter\": {\n" +
                "        \"publishTo\": \"input\",\n" +
                "        \"template\": \"custom-adapter\",\n" +
                "        \"count\": 10\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"env_uuid\": \"db01c146-8f68-11ea-a4eb-6398166be91d\"\n" +
                "}";
    }

    private static String constructCompiledSpec() {
        return "" +
                "{\n" +
                "  \"apiVersion\": 0,\n" +
                "  \"architecture\": {\n" +
                "    \"input\": {\n" +
                "      \"type\": \"input\",\n" +
                "      \"name\": \"\",\n" +
                "      \"comment\": \"\",\n" +
                "      \"config\": {},\n" +
                "      \"from\": [],\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"storage\": {\n" +
                "      \"type\": \"storage\",\n" +
                "      \"from\": [\n" +
                "        \"ProdSoutheastInput\",\n" +
                "        \"ProdMidwestInput\",\n" +
                "        \"ProdWestInput\",\n" +
                "        \"NonCollectionInput\",\n" +
                "        \"GeotabAndProfessorInput\"\n" +
                "      ],\n" +
                "      \"name\": \"\",\n" +
                "      \"comment\": \"\",\n" +
                "      \"config\": {},\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_MW_SW_2\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdMidwestInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_MW_SW_2 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_MW_SW_2\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_MW_SW_test_1\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdMidwestInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_MW_SW_test_1 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_MW_SW_test_1\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_MW_SW_test_2\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdMidwestInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_MW_SW_test_2 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_MW_SW_test_2\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_SE_1\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdSoutheastInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_SE_1 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_SE_1\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_SE_2\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdSoutheastInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_SE_2 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_SE_2\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_SE_test_1\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdSoutheastInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_SE_test_1 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_SE_test_1\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_SE_test_2\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdSoutheastInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_SE_test_2 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_SE_test_2\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_W_1\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdWestInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_W_1 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_W_1\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_W_2\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdWestInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_W_2 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_W_2\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_W_test_1\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdWestInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_W_test_1 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_W_test_1\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_W_test_2\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdWestInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_W_test_2 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_W_test_2\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"professor\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdSoutheastInput\",\n" +
                "        \"ProdMidwestInput\",\n" +
                "        \"ProdWestInput\",\n" +
                "        \"GeotabAndProfessorInput\"\n" +
                "      ],\n" +
                "      \"name\": \"professor production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"professorQ\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"professorTest\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdWestInput\",\n" +
                "        \"ProdMidwestInput\",\n" +
                "        \"ProdSoutheastInput\",\n" +
                "        \"GeotabAndProfessorInput\"\n" +
                "      ],\n" +
                "      \"name\": \"professorTest production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"professorTestQ\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"vts_MW_SW_1\": {\n" +
                "      \"type\": \"amqpOutput\",\n" +
                "      \"from\": [\n" +
                "        \"ProdMidwestInput\"\n" +
                "      ],\n" +
                "      \"name\": \"vts_MW_SW_1 production AMQP Output\",\n" +
                "      \"config\": {\n" +
                "        \"queueSuffix\": \"vts_MW_SW_1\"\n" +
                "      },\n" +
                "      \"comment\": \"\",\n" +
                "      \"to\": []\n" +
                "    },\n" +
                "    \"ProdSoutheastInput\": {\n" +
                "      \"type\": \"input\",\n" +
                "      \"name\": \"\",\n" +
                "      \"comment\": \"\",\n" +
                "      \"config\": {},\n" +
                "      \"from\": [],\n" +
                "      \"to\": [\n" +
                "        \"storage\",\n" +
                "        \"vts_SE_1\",\n" +
                "        \"vts_SE_2\",\n" +
                "        \"vts_SE_test_1\",\n" +
                "        \"vts_SE_test_2\",\n" +
                "        \"professor\",\n" +
                "        \"professorTest\",\n" +
                "        \"post-ingest.send\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"ProdMidwestInput\": {\n" +
                "      \"type\": \"input\",\n" +
                "      \"name\": \"\",\n" +
                "      \"comment\": \"\",\n" +
                "      \"config\": {},\n" +
                "      \"from\": [],\n" +
                "      \"to\": [\n" +
                "        \"storage\",\n" +
                "        \"vts_MW_SW_2\",\n" +
                "        \"vts_MW_SW_test_1\",\n" +
                "        \"vts_MW_SW_test_2\",\n" +
                "        \"professor\",\n" +
                "        \"professorTest\",\n" +
                "        \"vts_MW_SW_1\",\n" +
                "        \"post-ingest.send\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"ProdWestInput\": {\n" +
                "      \"type\": \"input\",\n" +
                "      \"name\": \"\",\n" +
                "      \"comment\": \"\",\n" +
                "      \"config\": {},\n" +
                "      \"from\": [],\n" +
                "      \"to\": [\n" +
                "        \"storage\",\n" +
                "        \"vts_W_1\",\n" +
                "        \"vts_W_2\",\n" +
                "        \"vts_W_test_1\",\n" +
                "        \"vts_W_test_2\",\n" +
                "        \"professor\",\n" +
                "        \"professorTest\",\n" +
                "        \"post-ingest.send\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"NonCollectionInput\": {\n" +
                "      \"type\": \"input\",\n" +
                "      \"name\": \"\",\n" +
                "      \"comment\": \"\",\n" +
                "      \"config\": {},\n" +
                "      \"from\": [],\n" +
                "      \"to\": [\n" +
                "        \"storage\",\n" +
                "        \"post-ingest.send\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"GeotabAndProfessorInput\": {\n" +
                "      \"type\": \"input\",\n" +
                "      \"name\": \"\",\n" +
                "      \"comment\": \"\",\n" +
                "      \"config\": {},\n" +
                "      \"from\": [],\n" +
                "      \"to\": [\n" +
                "        \"storage\",\n" +
                "        \"professor\",\n" +
                "        \"professorTest\",\n" +
                "        \"post-ingest.send\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"post-ingest.return\": {\n" +
                "      \"name\": \"post-ingest.return\",\n" +
                "      \"comment\": \"Auto generated for post-ingest custom component\",\n" +
                "      \"type\": \"input\",\n" +
                "      \"config\": {},\n" +
                "      \"to\": [],\n" +
                "      \"from\": []\n" +
                "    },\n" +
                "    \"post-ingest.send\": {\n" +
                "      \"name\": \"post-ingest.send\",\n" +
                "      \"comment\": \"Auto generated for post-ingest custom component\",\n" +
                "      \"type\": \"httpOutput\",\n" +
                "      \"to\": [],\n" +
                "      \"from\": [\n" +
                "        \"NonCollectionInput\",\n" +
                "        \"ProdWestInput\",\n" +
                "        \"ProdMidwestInput\",\n" +
                "        \"ProdSoutheastInput\",\n" +
                "        \"GeotabAndProfessorInput\"\n" +
                "      ],\n" +
                "      \"config\": {\n" +
                "        \"callbackUrl\": \"https://" + CB_URL + "/7df3d932135b1/dd784199381e/60e85cbe5a51aa8/in/flow/_dataflow_in\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"classes\": {\n" +
                "    \"collection\": {\n" +
                "      \"name\": \"collection\",\n" +
                "      \"comment\": \"creating groups for objects relatioship\",\n" +
                "      \"hasMany\": {\n" +
                "        \"tracker\": {\n" +
                "          \"class\": \"tracker\",\n" +
                "          \"inverse\": [\n" +
                "            \"hasMany\",\n" +
                "            \"collection\"\n" +
                "          ],\n" +
                "          \"uuid\": \"f3fc044d-5374-4ccb-ae4f-939401294b09\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"has\": {},\n" +
                "      \"objectProperties\": {},\n" +
                "      \"" + CB_URL_2 + "ributes\": {},\n" +
                "      \"streams\": {},\n" +
                "      \"events\": {},\n" +
                "      \"commands\": {},\n" +
                "      \"onCreate\": [],\n" +
                "      \"uuid\": \"7f63f482-116e-436b-b423-3fcb6c110dbd\"\n" +
                "    },\n" +
                "    \"tracker\": {\n" +
                "      \"name\": \"Tracker\",\n" +
                "      \"comment\": \"vehicle in the fleet, the device reports stream data, and is assigned to a vehicle and a region.\",\n" +
                "      \"hasMany\": {\n" +
                "        \"collection\": {\n" +
                "          \"class\": \"collection\",\n" +
                "          \"inverse\": [\n" +
                "            \"hasMany\",\n" +
                "            \"tracker\"\n" +
                "          ],\n" +
                "          \"uuid\": \"f99d26c4-b124-4b5a-b2ac-058ce147980d\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"streams\": {\n" +
                "        \"location\": {\n" +
                "          \"type\": \"geopoint\",\n" +
                "          \"name\": \"\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"f4bd4644-c139-4d7a-8ca2-d399135c759b\"\n" +
                "        },\n" +
                "        \"m2xMessageID\": {\n" +
                "          \"name\": \"M2X Message ID\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"0725ac01-5a48-4a39-a78a-d00caaae881a\"\n" +
                "        },\n" +
                "        \"barometricPressure\": {\n" +
                "          \"name\": \"barometricPressure\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"3edb7f8b-cfe3-4d8e-812f-c17ac495c9ef\"\n" +
                "        },\n" +
                "        \"milesPerGallon\": {\n" +
                "          \"name\": \"milesPerGallon\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"34fdea76-3fe7-491b-b740-2919fec4a35b\"\n" +
                "        },\n" +
                "        \"queryID\": {\n" +
                "          \"name\": \"queryID\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"fe06c27e-de4f-442e-a0f0-22182fe3e6cb\"\n" +
                "        },\n" +
                "        \"region\": {\n" +
                "          \"name\": \"region\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"006f96af-92d6-4e60-8015-793667d95b22\"\n" +
                "        },\n" +
                "        \"messageType\": {\n" +
                "          \"name\": \"messageType\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"af16d58c-690c-43ec-ab59-ed8591fa4215\"\n" +
                "        },\n" +
                "        \"appMsgType\": {\n" +
                "          \"name\": \"appMsgType\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"a49dd2c2-e1b5-43e2-8728-21be2be69617\"\n" +
                "        },\n" +
                "        \"complete\": {\n" +
                "          \"name\": \"complete\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"77f13d75-07e0-43f6-b762-bb172c749b61\"\n" +
                "        },\n" +
                "        \"MESSAGE_IN_BYTES\": {\n" +
                "          \"name\": \"MESSAGE_IN_BYTES\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"5b699697-7fde-4a64-8c6a-192761f176b3\"\n" +
                "        },\n" +
                "        \"transmissionFluidTemperature\": {\n" +
                "          \"name\": \"transmissionFluidTemperature\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"e3f48ab8-dfeb-4233-82e0-1e4915dbceb5\"\n" +
                "        },\n" +
                "        \"timeStamp\": {\n" +
                "          \"name\": \"Timestamp\",\n" +
                "          \"type\": \"timestamp\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"3d60612e-df47-4294-b5ad-7e1fa92516f2\"\n" +
                "        },\n" +
                "        \"engineRunTime\": {\n" +
                "          \"name\": \"engineRunTime\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"789c9bfc-263a-477d-b802-b6b481df3b59\"\n" +
                "        },\n" +
                "        \"messageSequenceNumber\": {\n" +
                "          \"name\": \"Message Sequence Number\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"9b9c3dd4-63ae-43e9-a955-34842e4ab060\"\n" +
                "        },\n" +
                "        \"processedAt\": {\n" +
                "          \"name\": \"Processed At\",\n" +
                "          \"type\": \"timestamp\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"be1722ee-1cd1-4ccc-b2bb-220ff4035834\"\n" +
                "        },\n" +
                "        \"timeofFix\": {\n" +
                "          \"name\": \"Time Of Fix\",\n" +
                "          \"type\": \"timestamp\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"982966c7-36d8-4a37-84eb-948268e77ab2\"\n" +
                "        },\n" +
                "        \"updateTime\": {\n" +
                "          \"name\": \"Update Time\",\n" +
                "          \"type\": \"timestamp\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"16e1e065-1cc6-4289-a507-c1ec7c6fac70\"\n" +
                "        },\n" +
                "        \"clientId\": {\n" +
                "          \"name\": \"Client ID\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"abd497e5-fa22-4838-925c-c167c384a4f1\"\n" +
                "        },\n" +
                "        \"phoneNumber\": {\n" +
                "          \"name\": \"Phone Number\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"369e0003-4b55-489f-ac35-db6ce4f4ee77\"\n" +
                "        },\n" +
                "        \"mobileID\": {\n" +
                "          \"name\": \"Mobile ID\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"834f4477-658d-4fe1-a81b-f4cd4e337ca6\"\n" +
                "        },\n" +
                "        \"firmwareScriptVersion\": {\n" +
                "          \"name\": \"Firmware Script Version\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"36c7f67e-2708-45fb-990c-a7f43d4d579d\"\n" +
                "        },\n" +
                "        \"mobileIdEnabled\": {\n" +
                "          \"name\": \"Mobile ID Enabled?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"8385da6e-a7d9-40e6-b6f1-8cd5b06921e7\"\n" +
                "        },\n" +
                "        \"mobileIdTypeEnabled\": {\n" +
                "          \"name\": \"Mobile ID Type Enabled?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"430eb90f-7a25-413f-88c0-cb1b591c4836\"\n" +
                "        },\n" +
                "        \"mobileIDType\": {\n" +
                "          \"name\": \"Moble ID Type\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"a9dbde5b-8f69-45b5-b65f-04490a5b12aa\"\n" +
                "        },\n" +
                "        \"dataService\": {\n" +
                "          \"name\": \"Data Service\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"2beea76a-767e-450d-9c93-d8bc312503a5\"\n" +
                "        },\n" +
                "        \"firmware\": {\n" +
                "          \"name\": \"Firmware\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"862571d6-9829-4f00-9de6-5115ae8e47cb\"\n" +
                "        },\n" +
                "        \"ICCID\": {\n" +
                "          \"name\": \"ICCID\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"41d530f9-860b-4a79-9784-e1bb79feceea\"\n" +
                "        },\n" +
                "        \"assetIdentifier\": {\n" +
                "          \"name\": \"Asset Identifier\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"9d25b286-9738-4664-bd8d-5afe1437f3f6\"\n" +
                "        },\n" +
                "        \"vinSource\": {\n" +
                "          \"name\": \"VIN Source\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"57d3dcf8-7d85-468c-a74d-c58e274d5a38\"\n" +
                "        },\n" +
                "        \"locationFlag\": {\n" +
                "          \"name\": \"Location Flag\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"b0e3c79b-0dc5-4c06-b8db-af3e6ac007ea\"\n" +
                "        },\n" +
                "        \"street\": {\n" +
                "          \"name\": \"Street\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"afc0b398-4042-46a6-9cfe-529d99fdb2dc\"\n" +
                "        },\n" +
                "        \"connected\": {\n" +
                "          \"name\": \"Connected?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"5e6dfcbb-3874-4bc0-ad78-5e8ead2efb56\"\n" +
                "        },\n" +
                "        \"networkService\": {\n" +
                "          \"name\": \"Network Service?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"d5636f22-1006-445d-9aa2-b9fbe7e3ccde\"\n" +
                "        },\n" +
                "        \"roaming\": {\n" +
                "          \"name\": \"Roaming?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"95420106-1f36-4b2b-ab71-aec2014ad71c\"\n" +
                "        },\n" +
                "        \"forwardingEnabled\": {\n" +
                "          \"name\": \"Forwarding Enabled?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"2bef8cb9-e3d2-4eb8-a322-8a3543c68a7b\"\n" +
                "        },\n" +
                "        \"eventIndex\": {\n" +
                "          \"name\": \"Event Index\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"af25cf1c-b01b-4029-b44f-730ec2130e86\"\n" +
                "        },\n" +
                "        \"calAmpEventCode\": {\n" +
                "          \"name\": \"CalAmp Event Code\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"bf1462fa-3547-4fe0-9d88-9497774efdfd\"\n" +
                "        },\n" +
                "        \"vtsEvent\": {\n" +
                "          \"name\": \"VTS Event\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"68b0230a-561c-4747-9722-edca3222e97a\"\n" +
                "        },\n" +
                "        \"altitude\": {\n" +
                "          \"name\": \"Altitude\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"61470e8d-ad49-4817-baf2-5e5c0c0d5ccf\"\n" +
                "        },\n" +
                "        \"idleTime\": {\n" +
                "          \"name\": \"Idle Time\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"63ff144f-7640-44f7-8a5b-8366849f0030\"\n" +
                "        },\n" +
                "        \"speed\": {\n" +
                "          \"name\": \"Speed\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"c58d88d3-19ff-48f4-b907-2de661dff9db\"\n" +
                "        },\n" +
                "        \"rollspeed\": {\n" +
                "          \"name\": \"Roll Speed\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"b60cc424-b6e1-4ecf-b289-98ef2b47397b\"\n" +
                "        },\n" +
                "        \"heading\": {\n" +
                "          \"name\": \"Heading\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"075cc926-7327-45aa-a2a0-794bc32e4ecf\"\n" +
                "        },\n" +
                "        \"lgpDate\": {\n" +
                "          \"name\": \"LGP Date\",\n" +
                "          \"type\": \"timestamp\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"0ef02836-25b4-437c-888b-c6b8d8fc8707\"\n" +
                "        },\n" +
                "        \"RSSI\": {\n" +
                "          \"name\": \"RSSI\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"c437b65a-36fe-470b-b467-2f59560cdc4c\"\n" +
                "        },\n" +
                "        \"HDOP\": {\n" +
                "          \"name\": \"HDOP\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"7565146d-b95a-4cb5-84d1-0528ea4381e4\"\n" +
                "        },\n" +
                "        \"satellites\": {\n" +
                "          \"name\": \"Satellites\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"9fbb34ca-dd7d-4956-a882-0ee77f9fd3e6\"\n" +
                "        },\n" +
                "        \"serviceType\": {\n" +
                "          \"name\": \"Service Type\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"bc27589e-3207-4d32-8f9b-b6f0f9bd926f\"\n" +
                "        },\n" +
                "        \"carrier\": {\n" +
                "          \"name\": \"Carrier\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"cb7323de-417c-4dc1-ba20-263fea109753\"\n" +
                "        },\n" +
                "        \"deviceInputIgnition\": {\n" +
                "          \"name\": \"Ignition?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"67dadbc6-d061-4510-a875-cbfdbb462953\"\n" +
                "        },\n" +
                "        \"gpsInvalidFix\": {\n" +
                "          \"name\": \"GPS Invalid Fix?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"0a852681-2803-4e2f-8562-8ff5eba1e440\"\n" +
                "        },\n" +
                "        \"odometer\": {\n" +
                "          \"name\": \"Odometer\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"333d1ea9-a166-4056-a931-0431ae93f389\"\n" +
                "        },\n" +
                "        \"tripOdometer\": {\n" +
                "          \"name\": \"Trip Odometer\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"d80040be-ab1c-4a7d-8f2e-e3b280b47ec0\"\n" +
                "        },\n" +
                "        \"distanceTravelledSinceLastPowerWakeUp\": {\n" +
                "          \"name\": \"Distance Travelled Since Last Power Wake Up\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"fcae74ef-1be8-4527-8de3-9db97da5bbcb\"\n" +
                "        },\n" +
                "        \"derivedMileage\": {\n" +
                "          \"name\": \"Derived Mileage\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"85690cce-e3a5-4db8-9dde-502223d6c622\"\n" +
                "        },\n" +
                "        \"authenticationWordEnabled\": {\n" +
                "          \"name\": \"Authentication Word Enabled?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"661cb69c-5a02-4aa7-89eb-be4d71350760\"\n" +
                "        },\n" +
                "        \"routingEnabled\": {\n" +
                "          \"name\": \"Routing Enabled?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"ddd30d25-53cc-49e4-8be8-8725a17024ff\"\n" +
                "        },\n" +
                "        \"UMTS\": {\n" +
                "          \"name\": \"UMTS\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"d6599bdd-18cb-47e3-ac4f-346c938b776a\"\n" +
                "        },\n" +
                "        \"twoDimFix\": {\n" +
                "          \"name\": \"Two Dim Fix?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"7e85c1c8-6686-4e8c-83b1-6488b985e2c7\"\n" +
                "        },\n" +
                "        \"lastKnown\": {\n" +
                "          \"name\": \"Last Known?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"f245ef9b-a298-4eb0-b60d-2a5e188181e1\"\n" +
                "        },\n" +
                "        \"vehicleVoltage10V\": {\n" +
                "          \"name\": \"vehicleVoltage10V\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"60df5a90-bec3-4ba9-a2c5-d3d22de3da82\"\n" +
                "        },\n" +
                "        \"vehicleVoltage7_9V\": {\n" +
                "          \"name\": \"vehicleVoltage7_9V\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"d08fb5f8-d7bb-402a-8814-c2d0557ae6a1\"\n" +
                "        },\n" +
                "        \"vehicleVoltage9V\": {\n" +
                "          \"name\": \"vehicleVoltage9V\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"a289b038-b27b-4f57-85cc-e5e24f00321a\"\n" +
                "        },\n" +
                "        \"alwaysEnabled\": {\n" +
                "          \"name\": \"Always Enabled?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"1f755715-afac-4036-96d9-75186bc9c1c4\"\n" +
                "        },\n" +
                "        \"optionsExtensionEnabled\": {\n" +
                "          \"name\": \"Options Extension Enabled?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"136e3400-34d5-4e75-95e3-9c71062a8a44\"\n" +
                "        },\n" +
                "        \"historic\": {\n" +
                "          \"name\": \"Historic?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"43b6d59d-b874-4db1-b02e-937cc15fe6cd\"\n" +
                "        },\n" +
                "        \"predicted\": {\n" +
                "          \"name\": \"Predicted?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"b9d20bf7-6152-4165-81e0-b04d59dfa40c\"\n" +
                "        },\n" +
                "        \"available\": {\n" +
                "          \"name\": \"Available?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"3e2fda8f-2e8b-4761-b491-62076b2cabb9\"\n" +
                "        },\n" +
                "        \"gpsReceiverTrackingError\": {\n" +
                "          \"name\": \"GPS Receiver Tracking Error?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"d40adc21-9fc6-4053-94ab-9b94ecab4098\"\n" +
                "        },\n" +
                "        \"invalidTime\": {\n" +
                "          \"name\": \"Invalid Time?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"e462d4b0-a7bc-4b66-ac56-4fc9e23f45f9\"\n" +
                "        },\n" +
                "        \"voiceCallIsActive\": {\n" +
                "          \"name\": \"Voice Call Active?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"26ea597c-8291-41b7-ad7d-29a152a568da\"\n" +
                "        },\n" +
                "        \"responseRedirectionEnabled\": {\n" +
                "          \"name\": \"Response Redirection Enabled?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"a55905cc-6c41-41f3-930b-6aecaf14f05c\"\n" +
                "        },\n" +
                "        \"accums\": {\n" +
                "          \"name\": \"Accumulations\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"c9661f69-10b0-4b9e-94ca-4bc20462850d\"\n" +
                "        },\n" +
                "        \"lmu32HttpOtaUpdateStatusError\": {\n" +
                "          \"name\": \"LMU 32 HTTP OTA Update Status Error?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"f5e06818-4ddc-4e8e-be5b-e66706a08295\"\n" +
                "        },\n" +
                "        \"spare\": {\n" +
                "          \"name\": \"Spare\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"3d526ed6-4ed3-4236-8055-27f8be62afab\"\n" +
                "        },\n" +
                "        \"timeFromPowerUp\": {\n" +
                "          \"name\": \"Time Since Power Up\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"41716002-1af0-445e-9532-b58b67c2a960\"\n" +
                "        },\n" +
                "        \"vehicleSpeed\": {\n" +
                "          \"name\": \"Vehicle Speed\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"d94d3f49-657a-4164-a45d-9e181e3cd5db\"\n" +
                "        },\n" +
                "        \"engineSpeed\": {\n" +
                "          \"name\": \"Engine Speed\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"c909ff16-5fa9-4563-8d2e-6b03745a764e\"\n" +
                "        },\n" +
                "        \"malfunctionIndicatorLampStatusIndicator\": {\n" +
                "          \"name\": \"Malfunction Indicator Lamp Status Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"9753f551-bf3f-4b3e-bee1-1286f75b8d5d\"\n" +
                "        },\n" +
                "        \"powerTakeOffsStatusIndicator\": {\n" +
                "          \"name\": \"Power Take Offs Status Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"9faf1b64-46b5-456b-9ab0-a5a269197698\"\n" +
                "        },\n" +
                "        \"b" + CB_URL_2 + "eryVoltageFrom12V\": {\n" +
                "          \"name\": \"B" + CB_URL_2 + "ery Voltage from 12V\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"4ea25296-9c56-4ee3-b2b1-b26f1d06ab91\"\n" +
                "        },\n" +
                "        \"secondaryAirSystemMonitorIndicator\": {\n" +
                "          \"name\": \"Secondary Air System Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"6d6207fe-14e0-4a40-8ab0-b9ed993e4c78\"\n" +
                "        },\n" +
                "        \"gpsReceiverSelfTestError\": {\n" +
                "          \"name\": \"GPS Receiver Self Test Error\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"422fe068-a70a-4920-8a34-ff21ed9ad6ae\"\n" +
                "        },\n" +
                "        \"exhaustGasrecirculationSystemMonitorIndicator\": {\n" +
                "          \"name\": \"Exhaust Gas Recirculation System Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"8a4392cf-ea1e-4526-9263-eb46daa3a119\"\n" +
                "        },\n" +
                "        \"airbagDashIndicator\": {\n" +
                "          \"name\": \"Airbag Dash Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"9794d911-c3f6-4f46-bdf4-c4fa35a8a531\"\n" +
                "        },\n" +
                "        \"acSystemRefrigerantMonitorIndicator\": {\n" +
                "          \"name\": \"A/C System Refrigerant Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"864d8830-2865-4060-9f93-b8caadd1f6ea\"\n" +
                "        },\n" +
                "        \"engineCoolantTemp\": {\n" +
                "          \"name\": \"Engine Coolant Temperature\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"2a5c62bd-de9e-4058-a04e-a3852282d238\"\n" +
                "        },\n" +
                "        \"fuelLevelVolume\": {\n" +
                "          \"name\": \"Fuel Level Volume\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"94ab5e31-3f02-4bc0-9ac9-9b0e359b7110\"\n" +
                "        },\n" +
                "        \"fuelSystemMonitorIndicator\": {\n" +
                "          \"name\": \"Fuel System Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"f58a1322-d463-4d65-9a07-7133ca501f3d\"\n" +
                "        },\n" +
                "        \"fuelLevelRemaining\": {\n" +
                "          \"name\": \"Fuel Level Remaining\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"85bba794-2cf3-4f3d-8389-04f0350dd2e9\"\n" +
                "        },\n" +
                "        \"tripFuelConsumption\": {\n" +
                "          \"name\": \"Trip Fuel Consumption\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"5e16ded8-8722-4fa1-98b9-143ac67cb5d4\"\n" +
                "        },\n" +
                "        \"catalystMonitorIndicator\": {\n" +
                "          \"name\": \"Catalyst Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"2eda859e-ebaf-4e4e-89a1-fa7065d3c637\"\n" +
                "        },\n" +
                "        \"throttlePosition\": {\n" +
                "          \"name\": \"Throttle Position\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"149bc98f-e70b-4644-a57c-049cc44c96e3\"\n" +
                "        },\n" +
                "        \"evaporativeSystemMonitorIndicator\": {\n" +
                "          \"name\": \"Evaporative System Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"edf1ea3e-5113-4c5a-8970-429af2a85bb1\"\n" +
                "        },\n" +
                "        \"b" + CB_URL_2 + "eryVoltage\": {\n" +
                "          \"name\": \"B" + CB_URL_2 + "ery Voltage\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"6067a5a9-0287-4d2f-a1b8-6874488639ec\"\n" +
                "        },\n" +
                "        \"oxygenSensorHeaterMonitorIndicator\": {\n" +
                "          \"name\": \"Oxygen Sensor Heater Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"548d1075-8a4d-4842-aeca-59d13544b891\"\n" +
                "        },\n" +
                "        \"comprehensiveComponentMonitorIndicator\": {\n" +
                "          \"name\": \"Comprehensive Component Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"6f10ab4b-e0e8-44ff-b57d-244a20673c77\"\n" +
                "        },\n" +
                "        \"oxygenSensorMonitorIndicator\": {\n" +
                "          \"name\": \"Oxygen Sensor Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"43c7fe5a-f724-4427-9d94-da0d367d199e\"\n" +
                "        },\n" +
                "        \"heatedCatalystMonitorIndicator\": {\n" +
                "          \"name\": \"Heated Catalyst Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"04d1ca3d-9d1c-40e0-a23c-a2feda9e6958\"\n" +
                "        },\n" +
                "        \"coolantHotLightIndicator\": {\n" +
                "          \"name\": \"Coolant Hot Light Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"44d2299a-e83c-448c-b678-e0e2dea1ec13\"\n" +
                "        },\n" +
                "        \"differntiallyCorrected\": {\n" +
                "          \"name\": \"Differentially Corrected\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"a8138d2d-629f-448c-8c6c-2d4b62266870\"\n" +
                "        },\n" +
                "        \"seatbeltFastenedIndicator\": {\n" +
                "          \"name\": \"Seatbelt Fastened Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"5be8976d-433c-45c8-b663-55a574075631\"\n" +
                "        },\n" +
                "        \"misfireMonitorIndicator\": {\n" +
                "          \"name\": \"Misfire Monitor Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"c398a5d0-f0f9-4e00-93fd-b3a7b5a79226\"\n" +
                "        },\n" +
                "        \"gpsAntennaStatusError\": {\n" +
                "          \"name\": \"GPS Antenna Status Error?\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"e03d6f52-3052-497c-9bea-0e6696d6220a\"\n" +
                "        },\n" +
                "        \"oilLifeRemaining\": {\n" +
                "          \"name\": \"Oil Life Remaining\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"d1d4c09d-362c-457b-b7bc-c9e039ebd5bb\"\n" +
                "        },\n" +
                "        \"rawData\": {\n" +
                "          \"name\": \"Raw Data\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"ab28a8c5-588f-4a63-a226-7c540cf33f5b\"\n" +
                "        },\n" +
                "        \"diagnosticCodes\": {\n" +
                "          \"name\": \"Diagnostic Codes\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"a5a6ce79-7762-45a4-b55e-a2d6e4db353b\"\n" +
                "        },\n" +
                "        \"brakePedalStatus\": {\n" +
                "          \"name\": \"Brake Pedal Status\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"2a75f59e-a343-423d-ad13-3bbf65b60014\"\n" +
                "        },\n" +
                "        \"cruiseControlStatus\": {\n" +
                "          \"name\": \"Cruise Control Status\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"95d74441-c6b9-4f7a-84cf-e903c6bb877b\"\n" +
                "        },\n" +
                "        \"parkBrakeLightIndicator\": {\n" +
                "          \"name\": \"Park Brake Light Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"129ee184-c14e-41bd-8f76-6c81360dad40\"\n" +
                "        },\n" +
                "        \"oilPressureLamp\": {\n" +
                "          \"name\": \"Oil Pressure Lamp\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"c459f9c3-9969-4148-90bd-0660a71fe049\"\n" +
                "        },\n" +
                "        \"absDashIndicator\": {\n" +
                "          \"name\": \"ABS Dash Indicator\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"e1cc6495-9c2c-4afc-9369-b6a5992caada\"\n" +
                "        },\n" +
                "        \"absActiveLamp\": {\n" +
                "          \"name\": \"ABS Active Lamp\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"e2c98303-b91a-4449-9433-df2083227b4b\"\n" +
                "        },\n" +
                "        \"tpmsStatus\": {\n" +
                "          \"name\": \"Tire Pressure Monitoring System Status\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"3b345803-6c3e-4ea1-8d7b-f5744ab0603e\"\n" +
                "        },\n" +
                "        \"ambientAirTemp\": {\n" +
                "          \"name\": \"Ambient Air Temperature\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"6f466fd4-73f4-46d2-8639-c0ae22b1a3f8\"\n" +
                "        },\n" +
                "        \"TMPS1LA\": {\n" +
                "          \"name\": \"TMPS1LA\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"cdae68b5-12be-49fc-ad62-24e9be8e0776\"\n" +
                "        },\n" +
                "        \"TMPS1RA\": {\n" +
                "          \"name\": \"TMPS1RA\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"1fdf5042-3511-4d79-bbb2-f9d86bbf9258\"\n" +
                "        },\n" +
                "        \"TMPS2LA\": {\n" +
                "          \"name\": \"TMPS2LA\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"2b602857-7b1e-46be-907f-2f6f6e2e8472\"\n" +
                "        },\n" +
                "        \"TMPS2LB\": {\n" +
                "          \"name\": \"TMPS2LB\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"470b79df-06a3-42f2-88a7-2cad1ac5da15\"\n" +
                "        },\n" +
                "        \"TMPS2RB\": {\n" +
                "          \"name\": \"TMPS2RB\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"ba695e8d-873b-435e-a995-900029ba2e4c\"\n" +
                "        },\n" +
                "        \"TMPS2RA\": {\n" +
                "          \"name\": \"TMPS2RA\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"83034281-7b73-420b-92e9-ad4845ac8c44\"\n" +
                "        },\n" +
                "        \"tirePressure1LA\": {\n" +
                "          \"name\": \"tirePressure1LA\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"471244d7-911e-4d30-83fa-9775ff3750d1\"\n" +
                "        },\n" +
                "        \"tirePressure1RA\": {\n" +
                "          \"name\": \"tirePressure1RA\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"dceb0c44-488c-49ee-813e-82a9d8d5b2c2\"\n" +
                "        },\n" +
                "        \"tirePressure2LA\": {\n" +
                "          \"name\": \"tirePressure2LA\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"79909092-96f3-49b9-ba91-84983ad85316\"\n" +
                "        },\n" +
                "        \"tirePressure2LB\": {\n" +
                "          \"name\": \"tirePressure2LB\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"ce421cc0-6bfc-4acd-8690-1bb2e9f114c8\"\n" +
                "        },\n" +
                "        \"tirePressure2RB\": {\n" +
                "          \"name\": \"tirePressure2RB\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"315ab3c6-c36f-4af4-ab71-3e81eb789443\"\n" +
                "        },\n" +
                "        \"tirePressure2RA\": {\n" +
                "          \"name\": \"tirePressure2RA\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"2ca3de89-118f-474d-92d8-aeaf2378ab79\"\n" +
                "        },\n" +
                "        \"highTransmission\": {\n" +
                "          \"name\": \"Transmission\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"e2f64799-0832-41b9-98bc-7b28e04341f3\"\n" +
                "        },\n" +
                "        \"lostExternalPower\": {\n" +
                "          \"name\": \"Lost External Power\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"57b1e76a-12e5-466a-93f6-d41177d22adf\"\n" +
                "        },\n" +
                "        \"input7\": {\n" +
                "          \"name\": \"Input 7\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"619c3d7e-ab4b-4783-a64a-97762dbd9aba\"\n" +
                "        },\n" +
                "        \"input6\": {\n" +
                "          \"name\": \"Input 6\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"ccf22d1d-bc46-49f1-bb7d-ada33e968b63\"\n" +
                "        },\n" +
                "        \"input5\": {\n" +
                "          \"name\": \"Input 5\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"8ebfb3bc-991c-4eae-942d-6616c8579c54\"\n" +
                "        },\n" +
                "        \"input4\": {\n" +
                "          \"name\": \"Input 4\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"fc8f602d-8eb2-4690-ab4d-b0c8b991e431\"\n" +
                "        },\n" +
                "        \"input3\": {\n" +
                "          \"name\": \"Input 3\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"1e225615-4eeb-4320-af62-b0be28fd6d79\"\n" +
                "        },\n" +
                "        \"input2\": {\n" +
                "          \"name\": \"Input 2\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"665a18aa-bd10-46b1-873f-dbaee4ff44a0\"\n" +
                "        },\n" +
                "        \"input1\": {\n" +
                "          \"name\": \"Input 1\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"7e8f2eac-c6b1-4f09-9b0f-c51d7cbb2669\"\n" +
                "        },\n" +
                "        \"o2SensorCircuitNoActivity\": {\n" +
                "          \"name\": \"O2 Sensor Circuit No Activity\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"db2c2aa3-ec18-46c2-bab8-6bbf073610c5\"\n" +
                "        },\n" +
                "        \"o2SensorHeaterCircuitMalfunction\": {\n" +
                "          \"name\": \"O2 Sensor Circuit Malfunction\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"47a1de11-dd07-4327-b75c-e817fb6c281c\"\n" +
                "        },\n" +
                "        \"ho2sHeaterControlMalfunction\": {\n" +
                "          \"name\": \"HO2S Heater Control Malfunction\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"e55ef670-9120-4957-b5c2-d3ea43574cd1\"\n" +
                "        },\n" +
                "        \"ho2sHeaterResistanceMalfunction\": {\n" +
                "          \"name\": \"HO2S Heater Resistance Malfunction\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"cc58a488-9d13-4490-8162-8ec774d999bb\"\n" +
                "        },\n" +
                "        \"engineOilTemp\": {\n" +
                "          \"name\": \"Engine Oil Temperature\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"5723f76a-cdec-4284-864b-68f0b0bfdce5\"\n" +
                "        },\n" +
                "        \"oxygenSensorNoActivityDetected\": {\n" +
                "          \"name\": \"oxygenSensorNoActivityDetected\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"93e4da41-d1df-4d0b-8fd3-f0ced5ac5e1b\"\n" +
                "        },\n" +
                "        \"oxygenSensorHeaterMalfunction\": {\n" +
                "          \"name\": \"oxygenSensorHeaterMalfunction\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"161495a2-9e7f-450a-a3f1-1de3efc0326e\"\n" +
                "        },\n" +
                "        \"signalStatus\": {\n" +
                "          \"name\": \"signalStatus\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"735b9ae1-1bd3-4e68-acb5-d218d9d0d2f3\"\n" +
                "        },\n" +
                "        \"fuelRate\": {\n" +
                "          \"name\": \"fuelRate\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"abe8aa63-4805-42b2-8691-ec5edf0e66ec\"\n" +
                "        },\n" +
                "        \"brakeSwitchStatus\": {\n" +
                "          \"name\": \"brakeSwitchStatus\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"3d695e17-30c1-4737-98e2-3fddcc21e512\"\n" +
                "        },\n" +
                "        \"deviceType\": {\n" +
                "          \"name\": \"Device Type\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"b9dffd85-8153-4803-87c4-066da5390d8f\"\n" +
                "        },\n" +
                "        \"ignitionStatus\": {\n" +
                "          \"name\": \"Ignition Status\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"41320e6a-0216-4179-912f-777e3f2c4d07\"\n" +
                "        },\n" +
                "        \"timeBetweenIgnitionOnAndVehicleMoving\": {\n" +
                "          \"name\": \"Time Between Ignition On and Vehicle Moving\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"ed8a7666-5994-4601-83b0-f799c806750a\"\n" +
                "        },\n" +
                "        \"indicator0\": {\n" +
                "          \"name\": \"indicator0\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"b40881c4-1b44-49bf-a2b7-17d103843f70\"\n" +
                "        },\n" +
                "        \"indicator1\": {\n" +
                "          \"name\": \"indicator1\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"f229cc96-f1fb-4997-857c-f55c1dfe9479\"\n" +
                "        },\n" +
                "        \"calculatedFuelUsage\": {\n" +
                "          \"name\": \"Calculated Fuel Usage\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"5dd38cf1-7c1e-4913-9ad5-46d1a87f1be6\"\n" +
                "        },\n" +
                "        \"diagnosticsReportType\": {\n" +
                "          \"name\": \"Diagnostics Report Type\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"3524e904-4af0-4fbe-85e6-38218b91eea8\"\n" +
                "        },\n" +
                "        \"numberDiagnosticsReported\": {\n" +
                "          \"name\": \"Number of Diagnostics Reported\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"8085ae64-8e0c-4943-86de-2fe47a619c84\"\n" +
                "        },\n" +
                "        \"proto\": {\n" +
                "          \"name\": \"Protocol\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"c33147a1-abf0-4108-8ccc-529c812b5605\"\n" +
                "        },\n" +
                "        \"params\": {\n" +
                "          \"name\": \"Params\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"1b9f6e0e-8360-43dd-8e88-46e40d099692\"\n" +
                "        },\n" +
                "        \"indctrs\": {\n" +
                "          \"name\": \"Indicators\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"a1560991-c759-48c8-be13-e09415a35dab\"\n" +
                "        },\n" +
                "        \"b" + CB_URL_2 + "eryVoltageFrom10V\": {\n" +
                "          \"name\": \"B" + CB_URL_2 + "ery Voltage from 10V\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"e61cb685-d58e-4f8c-821b-771a302f86a4\"\n" +
                "        },\n" +
                "        \"timeSinceDevicePowerWakeUp\": {\n" +
                "          \"name\": \"Time Since Device Power Wake Up\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"d2685b81-c598-4b06-9fa5-2dfbdb090766\"\n" +
                "        },\n" +
                "        \"maxSpeed\": {\n" +
                "          \"name\": \"Maximum Speed\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"44c7debc-9161-4499-98c2-8bf2fe84c1e8\"\n" +
                "        },\n" +
                "        \"positionAccuracy\": {\n" +
                "          \"name\": \"Position Accuracy\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"2acb9324-a12f-4268-892b-76b5fbdfce99\"\n" +
                "        },\n" +
                "        \"numberOfPowerWakeUpTimes\": {\n" +
                "          \"name\": \"Number of Power Wake Up Times\",\n" +
                "          \"type\": \"integer\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"a9ec5160-2148-457e-a106-a3b8709faa02\"\n" +
                "        },\n" +
                "        \"userMsg\": {\n" +
                "          \"name\": \"userMsg\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"2b8fcf76-e5f5-4e0f-ae22-d852cb7cef57\"\n" +
                "        },\n" +
                "        \"userMsgLen\": {\n" +
                "          \"name\": \"userMsgLen\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"7d88dd39-4036-4bf1-bf21-4280a5472564\"\n" +
                "        },\n" +
                "        \"userMsgID\": {\n" +
                "          \"name\": \"userMsgID\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"e28bfd23-958e-49aa-baef-f21e1d12bcc5\"\n" +
                "        },\n" +
                "        \"userMsgRoute\": {\n" +
                "          \"name\": \"userMsgRoute\",\n" +
                "          \"type\": \"number\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"3763b8aa-e2ed-4c56-835d-780f417bd55f\"\n" +
                "        },\n" +
                "        \"bb_configured\": {\n" +
                "          \"name\": \"bb_configured\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"4aa3aa99-683b-4762-945c-4c72af5003a5\"\n" +
                "        },\n" +
                "        \"Prod-southeast\": {\n" +
                "          \"name\": \"Prod-southeast\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"0b51889e-4417-4252-aced-ea8bd0b218a4\"\n" +
                "        },\n" +
                "        \"Prod-west\": {\n" +
                "          \"name\": \"Prod-west\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"b8ee3543-5418-4442-8555-660910b240d6\"\n" +
                "        },\n" +
                "        \"Prod-midwest\": {\n" +
                "          \"name\": \"Prod-midwestt\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"ebb6c1db-1c2d-4e91-bd3c-40964cd7b52b\"\n" +
                "        },\n" +
                "        \"Geotab\": {\n" +
                "          \"name\": \"Geotab\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"5e088223-8825-4dee-a799-da21a2bb3342\"\n" +
                "        },\n" +
                "        \"m2xDeviceID\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"name\": \"\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"81c86eaa-4521-415f-8782-1c2d318bdc2e\"\n" +
                "        },\n" +
                "        \"subRegion\": {\n" +
                "          \"name\": \"Sub Region\",\n" +
                "          \"comment\": \"current device sub-region for AT&T fleet trucks. Used by VTS and taken from the tracker collection\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"uuid\": \"e2e0d088-ab6c-4742-9148-edfe11744db2\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"" + CB_URL_2 + "ributes\": {\n" +
                "        \"geotabid\": {\n" +
                "          \"name\": \"geotabid\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"de846607-2ae0-40b8-8df3-ede5c449d5b3\"\n" +
                "        },\n" +
                "        \"oldGeotabId\": {\n" +
                "          \"name\": \"GeotabId on retirement\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"2f23d89d-a31a-4a8b-99e9-63872b7435db\"\n" +
                "        },\n" +
                "        \"tech_data\": {\n" +
                "          \"name\": \"tech_data\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"c2657a6f-fa19-4ecc-a3b2-5c3a02ae0d36\"\n" +
                "        },\n" +
                "        \"vin_data\": {\n" +
                "          \"name\": \"vin_data\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"b07e9b30-d75b-4386-80ae-7b38c6781ee9\"\n" +
                "        },\n" +
                "        \"random\": {\n" +
                "          \"name\": \"random\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"1549f12b-37dd-4fb3-b64f-e6a5cd49eab9\"\n" +
                "        },\n" +
                "        \"retire_date\": {\n" +
                "          \"name\": \"Retiring date\",\n" +
                "          \"type\": \"timestamp\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"897a94db-90c2-49f8-8013-d7075acf733f\"\n" +
                "        },\n" +
                "        \"oldVid\": {\n" +
                "          \"name\": \"vID before retiring\",\n" +
                "          \"type\": \"text\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"54f8cf31-a4a4-4cf4-b3bd-c8f3d5a2ce50\"\n" +
                "        },\n" +
                "        \"surplus_date\": {\n" +
                "          \"name\": \"Surplused date\",\n" +
                "          \"type\": \"timestamp\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"21188b0d-3a90-45ad-b4a3-460344c3f626\"\n" +
                "        },\n" +
                "        \"depot_date\": {\n" +
                "          \"name\": \"Depot date\",\n" +
                "          \"type\": \"timestamp\",\n" +
                "          \"comment\": \"\",\n" +
                "          \"uuid\": \"bbb17a5c-c9a0-4177-b3b6-a9a723a61f47\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"adapter\": {\n" +
                "        \"publishTo\": \"input\",\n" +
                "        \"template\": \"custom-adapter\",\n" +
                "        \"count\": 10,\n" +
                "        \"properties\": {},\n" +
                "        \"uuid\": \"e01e7025-fdab-4a22-9cf0-a481232da3f9\",\n" +
                "        \"_changes\": {\n" +
                "          \"template\": false,\n" +
                "          \"count\": false,\n" +
                "          \"properties\": false\n" +
                "        }\n" +
                "      },\n" +
                "      \"has\": {},\n" +
                "      \"objectProperties\": {},\n" +
                "      \"events\": {},\n" +
                "      \"commands\": {},\n" +
                "      \"onCreate\": [],\n" +
                "      \"uuid\": \"33824a1f-2292-4d8b-b2dc-b36e9b8d2a0e\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"services\": {\n" +
                "    \"post-ingest.component\": {\n" +
                "      \"count\": 5,\n" +
                "      \"template\": \"custom-component\",\n" +
                "      \"properties\": {},\n" +
                "      \"comment\": \"Will process the message, on a custom component flow, after the ingestion from the tracker Calamp adapter\",\n" +
                "      \"uuid\": \"09bb14c2-a03f-43f2-b4c0-44e8ef2d03fe\",\n" +
                "      \"_changes\": {\n" +
                "        \"template\": false,\n" +
                "        \"count\": false,\n" +
                "        \"properties\": false\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"uuid\": \"db01c056-8f68-11ea-a4eb-6398166be91d\",\n" +
                "  \"env_uuid\": \"%s\",\n" +
                "  \"organization\": \"fleet\",\n" +
                "  \"identifier\": \"fleet-trucks-iot\",\n" +
                "  \"env\": \"prod\",\n" +
                "  \"revision\": \"c1b69ccf4d3982a7b2523ce7c8d64aed29ead295\",\n" +
                "  \"deletions\": []\n" +
                "}";
    }


    public static Env findEnvironment(String organization, String projectEnv) {
        if (!ORG.equals(organization)) return null;
        final String[] arr = projectEnv.split("~");
        final String project = arr[0].trim();
        final String env = arr.length < 2 ? "prod" : arr[1].trim();
        if (!PROJ.equals(project)) return null;
        if ("prod".equals(env)) {
            return new Env(ORG, project, env, UUID.fromString("db01c0ec-8f68-11ea-a4eb-6398166be91d"));
        }
        else if ("dev".equals(env)) {
            return new Env(ORG, project, env, UUID.fromString("db01c146-8f68-11ea-a4eb-6398166be91d"));
        }
        else {
            return null;
        }

    }
}
