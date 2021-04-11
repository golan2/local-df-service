package com.golan.local.dataflow.data;

import java.util.UUID;

public class Shayba {
    private static final String PLACE_HOLDER = "%s";
    public static final String ENV_UUID_MORMONT_SHAYBA_DEV = "aea50ab6-32db-11ea-977e-29d880279c99";
    public static final String ENV_UUID_GOLAN2_SHAYBA_PROD = "3fbdd4b2-6ca4-11eb-ae36-3b305d40ca6c";
    public static final String ENV_UUID_GOLAN2_SHAYBA_DEV  = "3fbdd4ee-6ca4-11eb-ae36-3b305d40ca6c";
    public static final String ORG_GOLAN2                  = "golan2";
    public static final String ORG_MORMONT                 = "mormont";
    public static final String PROJECT                     = "shayba";
    private static final String PROD                        = "prod";
    private static final String DEV                         = "dev";
    private static final Env OPE_MORMONT_DEV = new Env(ORG_MORMONT, PROJECT, DEV, UUID.fromString(ENV_UUID_MORMONT_SHAYBA_DEV));
    private static final Env OPE_GOLAN2_PROD = new Env(ORG_GOLAN2, PROJECT, PROD, UUID.fromString(ENV_UUID_GOLAN2_SHAYBA_PROD));
    private static final Env OPE_GOLAN2_DEV = new Env(ORG_GOLAN2, PROJECT, DEV, UUID.fromString(ENV_UUID_GOLAN2_SHAYBA_DEV));

    private static final String COMPILED_GOLAN2_SHAYBA_DEV = "" +
            "{\n" +
            "  \"apiVersion\": 0,\n" +
            "  \"classes\": {\n" +
            "    \"drone\": {\n" +
            "      \"streams\": {\n" +
            "        \"speed\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"54da0886-4aa4-421c-80aa-fd0798f4aa26\"\n" +
            "        },\n" +
            "        \"location\": {\n" +
            "          \"type\": \"geopoint\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"d0929ada-f899-486f-88f5-ebba463b93be\"\n" +
            "        },\n" +
            "        \"message\": {\n" +
            "          \"type\": \"text\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"902f80d5-48b1-423e-858e-2ebdf79155f9\"\n" +
            "        },\n" +
            "        \"fast\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"9884a466-a15f-4395-bb91-894e1986f52b\"\n" +
            "        },\n" +
            "        \"slow\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"a944d930-5c69-4311-9e8b-9c98b0833b99\"\n" +
            "        },\n" +
            "        \"ts\": {\n" +
            "          \"type\": \"timestamp\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"246aa00b-d7e8-49bf-a187-750341f0e6f8\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"attributes\": {\n" +
            "        \"attrib_text\": {\n" +
            "          \"type\": \"text\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"c3063861-116d-4c93-b255-908bc1a52302\"\n" +
            "        },\n" +
            "        \"attrib_num\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"383f9c4a-38b9-41ed-a816-8220f87b279a\"\n" +
            "        },\n" +
            "        \"attrib_int\": {\n" +
            "          \"type\": \"integer\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"05341c89-c22d-4ba5-81de-b839319d329f\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"events\": {\n" +
            "        \"speed2high\": {\n" +
            "          \"data\": {\n" +
            "            \"speed\": {\n" +
            "              \"type\": \"number\",\n" +
            "              \"name\": \"\",\n" +
            "              \"comment\": \"\",\n" +
            "              \"uuid\": \"e00defc4-c237-4376-b560-5fa922d64b42\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"b4c63f98-ebcc-4427-ae66-2ad38a28e954\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"adapter\": {\n" +
            "        \"template\": \"custom-adapter\",\n" +
            "        \"publishTo\": \"input\",\n" +
            "        \"count\": 1,\n" +
            "        \"properties\": {},\n" +
            "        \"uuid\": \"0256a095-ba73-4fa5-84a1-885caf10c0f5\",\n" +
            "        \"_changes\": {\n" +
            "          \"template\": true,\n" +
            "          \"count\": true,\n" +
            "          \"properties\": true\n" +
            "        }\n" +
            "      },\n" +
            "      \"name\": \"\",\n" +
            "      \"comment\": \"\",\n" +
            "      \"has\": {},\n" +
            "      \"hasMany\": {},\n" +
            "      \"objectProperties\": {},\n" +
            "      \"commands\": {},\n" +
            "      \"onCreate\": [],\n" +
            "      \"uuid\": \"7a3567c7-6494-4e34-bae0-beb1a2a26770\"\n" +
            "    },\n" +
            "    \"izik\": {\n" +
            "      \"streams\": {\n" +
            "        \"speed\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"a76514e1-ac11-41ba-a605-926ed5721006\"\n" +
            "        },\n" +
            "        \"where_i_am\": {\n" +
            "          \"type\": \"geopoint\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"6cb54eb8-5f68-4274-875b-43ae7cb4be8f\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"adapter\": {\n" +
            "        \"template\": \"custom-adapter\",\n" +
            "        \"publishTo\": \"input\",\n" +
            "        \"count\": 2,\n" +
            "        \"properties\": {},\n" +
            "        \"uuid\": \"b1fbb70c-add8-4a5e-b8e0-fda1c69b9e70\",\n" +
            "        \"_changes\": {\n" +
            "          \"template\": true,\n" +
            "          \"count\": true,\n" +
            "          \"properties\": true\n" +
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
            "      \"uuid\": \"dab8b8a0-7ce0-44b4-83bd-cbc2fc2020b2\"\n" +
            "    },\n" +
            "    \"reserved\": {\n" +
            "      \"streams\": {\n" +
            "        \"timestamp\": {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"5075e7d7-b736-43c1-9ada-a9bc2c895b33\"\n" +
            "        },\n" +
            "        \"object_id\": {\n" +
            "          \"type\": \"text\",\n" +
            "          \"name\": \"\",\n" +
            "          \"comment\": \"\",\n" +
            "          \"uuid\": \"9a191d3d-95f0-498b-9d8f-97c591056965\"\n" +
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
            "      \"uuid\": \"1e5e94c8-8d9e-4a9e-be21-72c31b84ac2a\"\n" +
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
            "  \"uuid\": \"3fbdd426-6ca4-11eb-ae36-3b305d40ca6c\",\n" +
            "  \"env_uuid\": \"" + PLACE_HOLDER + "\",\n" +
            "  \"organization\": \"golan2\",\n" +
            "  \"identifier\": \"shayba\",\n" +
            "  \"env\": \"" + PLACE_HOLDER + "\",\n" +
            "  \"revision\": \"4d519714afd4a2a5e3235a0e3b052b026dc6e13c\",\n" +
            "  \"deletions\": []\n" +
            "}";

    private static final String SOURCE_GOLAN2_SHAYBA_DEV = "" +
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
            "        \"fast\": {\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"slow\": {\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"ts\": {\n" +
            "          \"type\": \"timestamp\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"attributes\": {\n" +
            "        \"attrib_text\": {\n" +
            "          \"type\": \"text\"\n" +
            "        },\n" +
            "        \"attrib_num\": {\n" +
            "          \"type\": \"number\"\n" +
            "        },\n" +
            "        \"attrib_int\": {\n" +
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




    public static final String CS_MORMONT_SHAYBA_DEV = String.format(COMPILED_SPEC, ENV_UUID_MORMONT_SHAYBA_DEV, ORG_MORMONT, PROJECT, DEV);
    public static final String PS_MORMONT_SHAYBA_DEV = String.format(SOURCE_SPEC, ENV_UUID_MORMONT_SHAYBA_DEV);

    public static final String CS_GOLAN2_SHAYBA_DEV  = String.format(COMPILED_GOLAN2_SHAYBA_DEV, ENV_UUID_GOLAN2_SHAYBA_DEV, DEV  );
    public static final String CS_GOLAN2_SHAYBA_PROD  = String.format(COMPILED_GOLAN2_SHAYBA_DEV, ENV_UUID_GOLAN2_SHAYBA_PROD, PROD  );

    public static final String PS_GOLAN2_SHAYBA_DEV = String.format(SOURCE_GOLAN2_SHAYBA_DEV, ENV_UUID_GOLAN2_SHAYBA_DEV);
    public static final String PS_GOLAN2_SHAYBA_PROD = String.format(SOURCE_GOLAN2_SHAYBA_DEV, ENV_UUID_GOLAN2_SHAYBA_PROD);



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

    public static Env findEnvironment(UUID envUuid) {
        if (ENV_UUID_MORMONT_SHAYBA_DEV.equalsIgnoreCase(envUuid.toString())) {
            return OPE_MORMONT_DEV;
        }
        if (ENV_UUID_GOLAN2_SHAYBA_DEV.equalsIgnoreCase(envUuid.toString())) {
            return OPE_GOLAN2_DEV;
        }
        if (ENV_UUID_GOLAN2_SHAYBA_PROD.equalsIgnoreCase(envUuid.toString())) {
            return OPE_GOLAN2_PROD;
        }
        return null;
    }
}
