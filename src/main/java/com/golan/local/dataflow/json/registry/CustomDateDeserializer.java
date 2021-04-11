package com.golan.local.dataflow.json.registry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss,SSS'Z'");

    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final String text = jsonParser.getText();
        try {
            return formatter.parse(text);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date: ["+ text +"]", e);
        }
    }

}
