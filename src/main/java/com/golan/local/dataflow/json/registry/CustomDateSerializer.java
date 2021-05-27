package com.golan.local.dataflow.json.registry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CustomDateSerializer extends StdSerializer<Date> {

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

    @SuppressWarnings("unused") //used by jackson
    public CustomDateSerializer() {
        this(null);
    }

    @SuppressWarnings("SameParameterValue")
    private CustomDateSerializer(Class<Date> vc) {
        super(vc);
    }

    @Override
    public void serialize (Date value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeString(formatter.format(value));
    }

    public String format(Date d) {
        return formatter.format(d);
    }
}
