package com.images3.rest;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.images3.ImageRequest;
import com.images3.ImageResponse;
import com.images3.ImageS3;


import play.mvc.Controller;
import play.mvc.Result;

public class ImageResource extends Controller {

    private ImageS3 imageS3;
    private ObjectMapper objectMapper;
    
    @Inject
    public ImageResource(ImageS3 imageS3, ObjectMapper objectMapper) {
        this.imageS3 = imageS3;
        this.objectMapper = objectMapper;
    }
    
    public Result addImage(String imagePlantId) throws IOException {
        File imageContent = request().body().asRaw().asFile();
        ImageRequest request = new ImageRequest(imagePlantId, imageContent);
        ImageResponse response = imageS3.addImage(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
}
