package com.images3.rest.codec;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.images3.ResizingUnit;

public class ResizingUnitDeserializer extends JsonDeserializer<ResizingUnit> {

    @Override
    public ResizingUnit deserialize(JsonParser arg0,
            DeserializationContext arg1) throws IOException,
            JsonProcessingException {
        JsonNode node = arg0.getCodec().readTree(arg0);
        return ResizingUnit.valueOf(node.get("unit").asText());
    }

}
