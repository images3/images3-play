package com.images3.rest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.images3.ImagePlantRequest;

import play.mvc.Controller;
import play.mvc.Result;

public class ImagePlantResource extends Controller {

    public Result createImagePlant() {
        ObjectMapper objectMapper = new ObjectMapper();
        String response = null;
        String json = request().body().asJson().toString();
        try {
            ImagePlantRequest request = objectMapper.readValue(json, ImagePlantRequest.class);
            response = objectMapper.writeValueAsString(request);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ok(response);
    }
}
