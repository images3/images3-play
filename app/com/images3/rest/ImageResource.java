package com.images3.rest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.gogoup.dddutils.pagination.PaginatedResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.images3.ImageAddRequest;
import com.images3.ImageResponse;
import com.images3.ImageS3;
import com.images3.SimpleImageResponse;
import com.images3.common.ImageIdentity;
import com.images3.common.TemplateIdentity;

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
        ImageAddRequest request = new ImageAddRequest(imagePlantId, imageContent);
        ImageResponse response = imageS3.addImage(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result getImage(String imagePlantId, String imageId) throws IOException {
        ImageResponse response = imageS3.getImage(new ImageIdentity(imagePlantId, imageId));
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson); 
    }
    
    public Result getImageWithTemplate(String imagePlantId, String imageId, String templateName) throws IOException {
        ImageResponse response = imageS3.getImage(new ImageIdentity(imagePlantId, imageId), templateName);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson); 
    }
    
    public Result getImages(String imagePlantId, String page) throws IOException {
        PaginatedResult<List<SimpleImageResponse>> pages = imageS3.getImages(imagePlantId);
        return getPaginatedResultResponse(pages, page);
    }
    
    public Result getImagesByTemplate(String imagePlantId, String templateName, String page) throws IOException {
        PaginatedResult<List<SimpleImageResponse>> pages = 
                imageS3.getImages(new TemplateIdentity(imagePlantId, templateName));
        return getPaginatedResultResponse(pages, page);
    }
    
    public Result getVersioningImages(String imagePlantId, String imageId, String page) throws IOException {
        PaginatedResult<List<SimpleImageResponse>> pages = 
                imageS3.getVersioningImages(new ImageIdentity(imagePlantId, imageId));
        return getPaginatedResultResponse(pages, page);
    }
    
    private Result getPaginatedResultResponse(PaginatedResult<List<SimpleImageResponse>> pages, 
            String page) throws IOException {
        if (null == page 
                || page.trim().length() == 0) {
            page = (String) pages.getFirstPageCursor();
        }
        List<SimpleImageResponse> images = pages.getResult(page);
        page = (String) pages.getNextPageCursor();
        PaginatedResultResponse<List<SimpleImageResponse>> response = 
                new PaginatedResultResponse<List<SimpleImageResponse>>(null, page, images);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result getImageContent(String imagePlantId, String imageId) throws IOException {
        File content = imageS3.getImageContent(new ImageIdentity(imagePlantId, imageId));
        return ok(content, content.getName()); 
    }
    
    public Result getImageContentWithTemplate(String imagePlantId, String imageId, String templateName) throws IOException {
        File content = imageS3.getImageContent(new ImageIdentity(imagePlantId, imageId), templateName);
        return ok(content, content.getName()); 
    }
}
