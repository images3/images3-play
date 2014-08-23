package com.images3.rest.codec;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.images3.common.TemplateIdentity;

public class TemplateIdentityDeserializer extends JsonDeserializer<TemplateIdentity> {

    @Override
    public TemplateIdentity deserialize(JsonParser arg0,
            DeserializationContext arg1) throws IOException,
            JsonProcessingException {
        JsonNode node = arg0.getCodec().readTree(arg0);
        return new TemplateIdentity(
                node.get("imagePlantId").asText(),
                node.get("templateName").asText());
    }

}
