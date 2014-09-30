package com.images3.rest.controllers;

import java.io.IOException;
import java.util.List;

import org.gogoup.dddutils.pagination.PaginatedResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.images3.ImageS3;
import com.images3.TemplateAddRequest;
import com.images3.common.TemplateIdentity;
import com.images3.rest.models.PaginatedResultModel;
import com.images3.rest.models.TemplateAddRequestModel;
import com.images3.TemplateResponse;

import play.mvc.Controller;
import play.mvc.Result;

public class TemplateController extends Controller {

    private ImageS3 imageS3;
    private ObjectMapper objectMapper;
    
    @Inject
    public TemplateController(ImageS3 imageS3, ObjectMapper objectMapper) {
        this.imageS3 = imageS3;
        this.objectMapper = objectMapper;
    }
    
    public Result addTemplate(String imagePlantId) throws IOException {
        TemplateAddRequestModel requestModel = objectMapper.readValue(
                request().body().asJson().toString(), TemplateAddRequestModel.class);
        TemplateAddRequest request = new TemplateAddRequest(
                new TemplateIdentity(imagePlantId, requestModel.getName()), 
                requestModel.getResizingConfig());
        TemplateResponse response = imageS3.addTemplate(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result archiveTemplate(String imagePlantId, String name) throws IOException {
        boolean isArchived = request().body().asJson().get("isArchived").asBoolean();
        TemplateIdentity id = new TemplateIdentity(imagePlantId, name);
        TemplateResponse response = imageS3.archiveTemplate(id, isArchived);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result getTemplate(String imagePlantId, String name) throws IOException {
        TemplateResponse response = imageS3.getTemplate(new TemplateIdentity(imagePlantId, name));
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result deleteTemplate(String imagePlantId, String name) {
        imageS3.deleteTemplate(new TemplateIdentity(imagePlantId, name));
        return status(204);
    }
    
    public Result getTemplates(String id, String page) throws IOException {
        PaginatedResult<List<TemplateResponse>> pages = imageS3.getAllTemplates(id);
        return getPaginatedResultResponse(pages, page);
    }
    
    public Result getActiveTemplates(String id, String page) throws IOException {
        PaginatedResult<List<TemplateResponse>> pages = imageS3.getActiveTempaltes(id);
        return getPaginatedResultResponse(pages, page);
    }
    
    public Result getArchivedTemplates(String id, String page) throws IOException {
        PaginatedResult<List<TemplateResponse>> pages = imageS3.getArchivedTemplates(id);
        return getPaginatedResultResponse(pages, page);
    }
    
    private Result getPaginatedResultResponse(PaginatedResult<List<TemplateResponse>> pages, 
            String page) throws IOException {
        if (null == page 
                || page.trim().length() == 0) {
            page = (String) pages.getFirstPageCursor();
        }
        List<TemplateResponse> templates = pages.getResult(page);
        String nextPage = (String) pages.getNextPageCursor();
        String prevPage = (String) pages.getPrevPageCursor();
        PaginatedResultModel<List<TemplateResponse>> response = 
                new PaginatedResultModel<List<TemplateResponse>>(prevPage, nextPage, templates);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
}
