package com.golan.local.dataflow.json.kong;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
public class Consumer {
    public static final DateTimeFormatter TEXT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static final String CUSTOM_ID_FIELD = "custom_id";
    private static final String CREATED_AT_FIELD = "created_at";
    private static final String USER_NAME_FIELD = "username";
    private static final String ID_FIELD = "id";

    private final String customId;
    private final LocalDateTime createdAt;
    private final String userName;
    private final UUID id;


}
