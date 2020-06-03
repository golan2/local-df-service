package com.golan.local.dataflow.data.registry;

class Util {
    private final static String NULL_STRING = "null";

    public static String toStringOrDefaultNull(Object obj) {
        return toStringOrDefault(obj, NULL_STRING);
    }

    public static String toStringOrDefault(Object obj, String defaultString) {
        if (obj != null) {
            return obj.toString();
        } else {
            return defaultString;
        }
    }
}
