package com.golan.local.dataflow.data.registry;

import org.json.JSONObject;

public class StreamSpec extends ValueSpec {
    /**
     * Instantiate an object representing a stream as defined in the project spec.
     *
     * @param identifier {String} - The identifier of the stream
     * @param object {JSONObject} - The stream spec
     */
    public StreamSpec(String identifier, JSONObject object) {
        super(identifier, object);
    }

}
