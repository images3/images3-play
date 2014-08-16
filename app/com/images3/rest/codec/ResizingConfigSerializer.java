package com.images3.rest.codec;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.images3.ResizingConfig;

public class ResizingConfigSerializer extends JsonSerializer<ResizingConfig> {

    @Override
    public void serialize(ResizingConfig value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeObjectField("unit", value.getUnit());
        jgen.writeNumberField("width", value.getWidth());
        jgen.writeNumberField("height", value.getHeight());
        jgen.writeBooleanField("isKeepProportions", value.isKeepProportions());
        jgen.writeEndObject();
    }


}
