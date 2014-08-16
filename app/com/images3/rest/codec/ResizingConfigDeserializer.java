package com.images3.rest.codec;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.images3.ResizingConfig;
import com.images3.ResizingUnit;

public class ResizingConfigDeserializer extends JsonDeserializer<ResizingConfig> {

    @Override
    public ResizingConfig deserialize(JsonParser arg0,
            DeserializationContext arg1) throws IOException,
            JsonProcessingException {
        JsonNode node = arg0.getCodec().readTree(arg0);
        return new ResizingConfig(
                ResizingUnit.valueOf(node.get("unit").asText()), 
                node.get("width").asInt(),
                node.get("height").asInt(),
                node.get("isKeepProportions").asBoolean());
    }

}
