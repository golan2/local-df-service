package com.golan.local.dataflow.data;

import java.util.UUID;

/**
 * We use it whenever we need to generate UUID that is not random rather consistent (each run same UUID)
 * It will start from some UUID (based on the prefix) and each time will increment.
 * Example:
 *  bbbbbbbb-bbbb-bbbb-bbbb-000000000001
 *  bbbbbbbb-bbbb-bbbb-bbbb-000000000002
 *  bbbbbbbb-bbbb-bbbb-bbbb-000000000003
 *  bbbbbbbb-bbbb-bbbb-bbbb-000000000004
 */
class UuidStore {
    private final String envUuidPrefix;

    private int nextIndex = 0;

    UuidStore(String envUuidPrefix) {
        this.envUuidPrefix = envUuidPrefix;
        verifyValidUuidPrefix();
    }

    private void verifyValidUuidPrefix() {
        try {
            buildUuid(1);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Initialize this class with a valid prefix (it should be a valid uuid structure without the last 6 characters)", e);
        }
    }

    private UUID buildUuid(int i) {
        return UUID.fromString(String.format(envUuidPrefix + "%06d", i));
    }

    UUID next() {
        return buildUuid(nextIndex++);
    }

}
