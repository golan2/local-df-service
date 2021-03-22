package com.golan.local.dataflow.json;

import java.util.ArrayList;
import java.util.List;

public class Paging {
    public static Meta meta(int limit, String cursor, int totalSize, int lastIndex) {
        return new Meta(cursor, nextCursor(lastIndex, totalSize), limit, null);
    }

    public static int lastIndex(int startIndex, int bulkSize, int totalSize) {
        return Math.min(startIndex+bulkSize-1, totalSize -1);
    }

    public static int startIndex(String cursor) {
        final int startIndex;
        if (cursor.isEmpty()) {
            startIndex = 0;
        }
        else {
            startIndex = Integer.parseInt(cursor);
        }
        return startIndex;
    }

    public static <T> List<T> thisBulk(List<T> items, int startIndex, int lastIndex) {
        final ArrayList<T> res = new ArrayList<>(lastIndex - startIndex + 1);
        for (int i = startIndex; i <= lastIndex; i++) {
            res.add(items.get(i));
        }
        return res;
    }

    private static String nextCursor(int lastIndexProvided, int totalSize) {
        final String nextCursor;
        if (lastIndexProvided+1 < totalSize) {
            nextCursor = "" + (lastIndexProvided + 1);
        }
        else {
            nextCursor = null;
        }
        return nextCursor;
    }
}
