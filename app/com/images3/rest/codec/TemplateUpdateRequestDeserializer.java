package com.images3.rest.codec;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.images3.TemplateIdentity;
import com.images3.TemplateUpdateRequest;

public class TemplateUpdateRequestDeserializer extends JsonDeserializer<TemplateUpdateRequest> {

    @Override
    public TemplateUpdateRequest deserialize(JsonParser arg0,
            DeserializationContext arg1) throws IOException,
            JsonProcessingException {
        JsonNode node = arg0.getCodec().readTree(arg0);
        TemplateIdentity id = 
                arg0.getCodec().treeToValue(node.get("id"), TemplateIdentity.class);
        return new TemplateUpdateRequest(id, node.get("isArchived").asBoolean());
    }

}
