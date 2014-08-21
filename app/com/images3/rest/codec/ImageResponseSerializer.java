package com.images3.rest.codec;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.images3.ImageResponse;

public class ImageResponseSerializer extends JsonSerializer<ImageResponse> {

    @Override
    public void serialize(ImageResponse value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeObjectField("id", value.getId());
        jgen.writeObjectField("dateTime", value.getDateTime());
        jgen.writeObjectField("templates", value.getTemplateIds());
        jgen.writeEndObject();
    }


}
