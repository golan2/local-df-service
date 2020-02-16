package com.golan.hello.spring.boot.app;

import com.golan.hello.spring.registry.Meta;

import java.util.ArrayList;
import java.util.List;

class Paging {
    static Meta meta(int limit, String cursor, int totalSize, int lastIndex) {
        return new Meta(cursor, nextCursor(lastIndex, totalSize), limit, null);
    }

    static int lastIndex(int startIndex, int bulkSize, int totalSize) {
        return Math.min(startIndex+bulkSize-1, totalSize -1);
    }

    static int startIndex(String cursor) {
        final int startIndex;
        if (cursor.isEmpty()) {
            startIndex = 0;
        }
        else {
            startIndex = Integer.parseInt(cursor);
        }
        return startIndex;
    }

    static <T> List<T> thisBulk(List<T> items, int startIndex, int lastIndex) {
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
