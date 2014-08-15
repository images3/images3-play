package com.images3.rest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.gogoup.dddutils.pagination.PaginatedResult;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    public Result addImagePlant() throws IOException {
        ImagePlantRequest request = objectMapper.readValue(
                request().body().asJson().toString(), ImagePlantRequest.class);
        ImagePlantResponse response = imageS3.addImagePlant(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result updateImagePlant(String id) throws IOException {
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
    
    public Result getImagePlant(String id) throws JsonProcessingException {
        ImagePlantResponse response = imageS3.getImagePlant(id);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result getAllImagePlants() throws IOException {
        String pageToken = request().queryString().get("page")[0];
        String cursor = session().get(pageToken);
        Object pageCursor = null;
        if (null != cursor) {
            pageCursor = objectMapper.readValue(cursor, Object.class);
        }
        PaginatedResult<List<ImagePlantResponse>> pages = imageS3.getAllImagePlants();
        if (null == pageCursor) {
            pageCursor = pages.getNextPageCursor();
        }
        List<ImagePlantResponse> result = pages.getResult(pageCursor);
        Object nextPageCursor = pages.getNextPageCursor();
        String next = UUID.randomUUID().toString();
        String nexPage = objectMapper.writeValueAsString(nextPageCursor);
        session().put(next, nexPage);
        PaginatedResponse<List<ImagePlantResponse>> response = 
                new PaginatedResponse<List<ImagePlantResponse>>(null, next, result);
        String respJson = objectMapper.writeValueAsString(response);
        System.out.println("HERE======>response: " + response.toString());
        return ok(respJson);
    }
    
}
