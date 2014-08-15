package com.images3.rest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.images3.ImagePlantRequest;
import com.images3.ImagePlantResponse;
import com.images3.ImageS3;

import play.mvc.Controller;
import play.mvc.Result;

public class ImagePlantResource extends Controller {
    
    private ImageS3 imageS3;
    
    @Inject
    public ImagePlantResource(ImageS3 imageS3) {
        this.imageS3 = imageS3;
    }

    public Result createImagePlant() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String respJson = null;
        ImagePlantRequest request = objectMapper.readValue(
                request().body().asJson().toString(), ImagePlantRequest.class);
        ImagePlantResponse response = imageS3.addImagePlant(request);
        respJson = objectMapper.writeValueAsString(response);
        
        return ok(respJson);
    }
}
