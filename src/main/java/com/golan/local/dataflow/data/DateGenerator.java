package com.golan.local.dataflow.data;

import java.time.Instant;
import java.util.Date;

/**
 * Will return date sequence starting from the provided epoch in the constructor
 */
public class DateGenerator {
    static final int JUMP_MILLIS = 1000;        //one second
    private Date nextDate;

    public DateGenerator(final long startDateEpochMillis) {
        nextDate = Date.from(Instant.ofEpochMilli(startDateEpochMillis));
    }

    public Date next() {
        nextDate = new Date(nextDate.getTime() + JUMP_MILLIS);
        return nextDate;
    }

}
