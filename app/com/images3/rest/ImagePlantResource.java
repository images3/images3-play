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
    private ObjectMapper objectMapper;
    
    @Inject
    public ImagePlantResource(ImageS3 imageS3, ObjectMapper objectMapper) {
        this.imageS3 = imageS3;
        this.objectMapper = objectMapper;
    }

    public Result addImagePlant() throws JsonParseException, JsonMappingException, IOException {
        ImagePlantRequest request = objectMapper.readValue(
                request().body().asJson().toString(), ImagePlantRequest.class);
        ImagePlantResponse response = imageS3.addImagePlant(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result updateImagePlant(String id) throws JsonParseException, JsonMappingException, IOException {
        ImagePlantRequest request = objectMapper.readValue(
                request().body().asJson().toString(), ImagePlantRequest.class);
        ImagePlantResponse response = imageS3.updateImagePlant(id, request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result deleteImagePlant(String id) {
        imageS3.deleteImagePlant(id);
        return status(204);
    }
}
