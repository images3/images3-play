package com.images3.rest.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.gogoup.dddutils.pagination.PaginatedResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.images3.ImagePlantAddRequest;
import com.images3.ImagePlantResponse;
import com.images3.ImagePlantUpdateRequest;
import com.images3.ImageReportQueryRequest;
import com.images3.ImageReportResponse;
import com.images3.ImageS3;
import com.images3.common.ImageMetricsType;
import com.images3.common.TimeInterval;
import com.images3.rest.models.PaginatedResultModel;

import play.mvc.Controller;
import play.mvc.Result;

public class ImagePlantController extends Controller {

    private ImageS3 imageS3;
    private ObjectMapper objectMapper;
    
    @Inject
    public ImagePlantController(ImageS3 imageS3, ObjectMapper objectMapper) {
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
        if (null == page 
                || page.trim().length() == 0) {
            page = (String) pages.getFirstPageCursor();
        }
        List<ImagePlantResponse> result = pages.getResult(page);
        String nextPage = (String) pages.getNextPageCursor();
        String prevPage = (String) pages.getPrevPageCursor();
        PaginatedResultModel<List<ImagePlantResponse>> response = 
                new PaginatedResultModel<List<ImagePlantResponse>>(prevPage, nextPage, result);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result getImageReport(String id, String templateName, Long startTime,
            Long length, String timeUnit, String types) throws JsonProcessingException {
        if (null != templateName 
                && templateName.trim().length() == 0) {
            templateName = null;
        }
        TimeInterval interval = 
                new TimeInterval(new Date(startTime), length, TimeUnit.valueOf(timeUnit));
        ImageReportQueryRequest request = 
                new ImageReportQueryRequest(
                        id, templateName, interval, getImageReportTypes(types));
        ImageReportResponse response = imageS3.getImageReport(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    private ImageMetricsType[] getImageReportTypes(String typeString) {
        String [] types = typeString.split(",");
        ImageMetricsType[] reportTypes = new ImageMetricsType[types.length];
        int index = 0;
        for (String type: types) {
            reportTypes[index++] = ImageMetricsType.valueOf(type.trim());
        }
        return reportTypes;
    }
    
}
