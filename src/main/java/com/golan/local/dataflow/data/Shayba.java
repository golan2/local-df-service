package com.golan.local.dataflow.data;

import com.golan.local.dataflow.data.Env;

import java.util.UUID;

public class Shayba {
    private static final String PLACE_HOLDER = "%s";
    //Compiled Spec
    private static final String COMPILED_SPEC =
            "" +
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
                    "  \"env_uuid\": \"" + PLACE_HOLDER + "\",\n" +
                    "  \"organization\": \"" + PLACE_HOLDER + "\",\n" +
                    "  \"identifier\": \"" + PLACE_HOLDER + "\",\n" +
                    "  \"env\": \"" + PLACE_HOLDER + "\",\n" +
                    "  \"revision\": \"08d0b7069d491cb764a65242c5d55412f3f431ba\",\n" +
                    "  \"deletions\": []\n" +
                    "}";
    //Project Spec
    private static final String SOURCE_SPEC = "" +
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
            "  \"env_uuid\": \"" + PLACE_HOLDER + "\"\n" +
            "}";


    public static final String ENV_UUID_MORMONT_SHAYBA_DEV = "aea50ab6-32db-11ea-977e-29d880279c99";
    public static final String ENV_UUID_GOLAN2_SHAYBA_PROD = "3fbdd4b2-6ca4-11eb-ae36-3b305d40ca6c";
    public static final String ENV_UUID_GOLAN2_SHAYBA_DEV  = "3fbdd4ee-6ca4-11eb-ae36-3b305d40ca6c";
    public static final String ORG_GOLAN2                  = "golan2";
    public static final String ORG_MORMONT                 = "mormont";
    public static final String PROJECT                     = "shayba";
    public static final String PROD                        = "prod";
    public static final String DEV                         = "dev";

    public static final String CS_MORMONT_SHAYBA_DEV = String.format(COMPILED_SPEC, ENV_UUID_MORMONT_SHAYBA_DEV, ORG_MORMONT, PROJECT, DEV);
    public static final String CS_GOLAN2_SHAYBA_DEV  = String.format(COMPILED_SPEC, ENV_UUID_GOLAN2_SHAYBA_DEV, ORG_GOLAN2, PROJECT, DEV  );
    public static final String CS_GOLAN2_SHAYBA_PROD = String.format(COMPILED_SPEC, ENV_UUID_GOLAN2_SHAYBA_PROD, ORG_GOLAN2, PROJECT, PROD);
    public static final String PS_MORMONT_SHAYBA_DEV = String.format(SOURCE_SPEC, ENV_UUID_MORMONT_SHAYBA_DEV);
    public static final String PS_GOLAN2_SHAYBA_DEV  = String.format(SOURCE_SPEC, ENV_UUID_GOLAN2_SHAYBA_DEV );
    public static final String PS_GOLAN2_SHAYBA_PROD = String.format(SOURCE_SPEC, ENV_UUID_GOLAN2_SHAYBA_PROD);

    public static Env findEnvironment(String organization, String projectEnv) {
        final String[] arr = projectEnv.split("~");
        final String project = arr[0].trim();
        final String env = arr.length < 2 ? PROD : arr[1].trim();
        if (!PROJECT.equals(project)) return null;


        if (ORG_MORMONT.equals(organization)) {
            if (!DEV.equals(env)) return null;    //currently we do not support "prod" for shayba
            return new Env(organization, project, env, UUID.fromString(ENV_UUID_MORMONT_SHAYBA_DEV));
        }
        if (ORG_GOLAN2.equals(organization)) {
            if (DEV.equals(env)) {
                return new Env(organization, project, env, UUID.fromString(ENV_UUID_GOLAN2_SHAYBA_DEV));
            }
            else if (PROD.equals(env)) {
                return new Env(organization, project, env, UUID.fromString(ENV_UUID_GOLAN2_SHAYBA_PROD));
            }
        }

        return null;
    }
}
