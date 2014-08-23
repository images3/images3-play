package com.images3.rest;

import java.io.IOException;
import java.util.List;

import org.gogoup.dddutils.pagination.PaginatedResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.images3.ImagePlantAddRequest;
import com.images3.ImagePlantResponse;
import com.images3.ImagePlantUpdateRequest;
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

    public Result addImagePlant() throws IOException {
        ImagePlantAddRequest request = objectMapper.readValue(
                request().body().asJson().toString(), ImagePlantAddRequest.class);
        ImagePlantResponse response = imageS3.addImagePlant(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result updateImagePlant(String id) throws IOException {
        ImagePlantUpdateRequest request = objectMapper.readValue(
                request().body().asJson().toString(), ImagePlantUpdateRequest.class);
        request = new ImagePlantUpdateRequest(id, request.getName(), request.getBucket());
        ImagePlantResponse response = imageS3.updateImagePlant(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result deleteImagePlant(String id) {
        imageS3.deleteImagePlant(id);
        return status(204);
    }
    
    public Result getImagePlant(String id) throws IOException {
        ImagePlantResponse response = imageS3.getImagePlant(id);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result getAllImagePlants(String page) throws IOException {
        PaginatedResult<List<ImagePlantResponse>> pages = imageS3.getAllImagePlants();
        List<ImagePlantResponse> result = pages.getResult(page);
        String pageCursor = (String) pages.getNextPageCursor();
        PaginatedResultResponse<List<ImagePlantResponse>> response = 
                new PaginatedResultResponse<List<ImagePlantResponse>>(null, pageCursor, result);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
}
