package com.images3.rest.codec;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.images3.TemplateUpdateRequest;

public class TemplateUpdateRequestSerializer extends JsonSerializer<TemplateUpdateRequest> {

    @Override
    public void serialize(TemplateUpdateRequest value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeObjectField("id", value.getId());
        jgen.writeBooleanField("isArchived", value.isArchived());
        jgen.writeEndObject();
    }


}
