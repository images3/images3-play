package com.images3.rest;

import java.io.IOException;
import java.util.List;

import org.gogoup.dddutils.pagination.PaginatedResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.images3.ImageS3;
import com.images3.TemplateAddRequest;
import com.images3.common.TemplateIdentity;
import com.images3.TemplateResponse;
import com.images3.TemplateUpdateRequest;

import play.mvc.Controller;
import play.mvc.Result;

public class TemplateResource extends Controller {

    private ImageS3 imageS3;
    private ObjectMapper objectMapper;
    
    @Inject
    public TemplateResource(ImageS3 imageS3, ObjectMapper objectMapper) {
        this.imageS3 = imageS3;
        this.objectMapper = objectMapper;
    }
    
    public Result addTemplate(String imagePlantId, String name) throws IOException {
        TemplateAddRequest request = objectMapper.readValue(
                request().body().asJson().toString(), TemplateAddRequest.class);
        request = new TemplateAddRequest(
                new TemplateIdentity(imagePlantId, name),
                request.getResizingConfig()
                );
        TemplateResponse response = imageS3.addTemplate(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result updateTemplate(String imagePlantId, String name) throws IOException {
        TemplateUpdateRequest request = objectMapper.readValue(
                request().body().asJson().toString(), TemplateUpdateRequest.class);
        request = new TemplateUpdateRequest(
                new TemplateIdentity(imagePlantId, name),
                request.isArchived()
                );
        TemplateResponse response = imageS3.updateTemplate(request);
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
        page = (String) pages.getNextPageCursor();
        PaginatedResultResponse<List<TemplateResponse>> response = 
                new PaginatedResultResponse<List<TemplateResponse>>(null, page, templates);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
}
