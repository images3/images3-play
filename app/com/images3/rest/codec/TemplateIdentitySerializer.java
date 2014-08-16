package com.images3.rest.codec;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.images3.TemplateIdentity;

public class TemplateIdentitySerializer extends JsonSerializer<TemplateIdentity> {

    @Override
    public void serialize(TemplateIdentity value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("imagePlantId", value.getImagePlantId());
        jgen.writeStringField("templateName", value.getTemplateName());
        jgen.writeEndObject();
    }


}
