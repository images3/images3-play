package com.images3.rest;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.images3.ImageS3;
import com.images3.TemplateCreateRequest;
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
    
    public Result addTemplate() throws IOException {
        TemplateCreateRequest request = objectMapper.readValue(
                request().body().asJson().toString(), TemplateCreateRequest.class);
        TemplateResponse response = imageS3.addTemplate(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
    public Result updateTemplate() throws IOException {
        TemplateUpdateRequest request = objectMapper.readValue(
                request().body().asJson().toString(), TemplateUpdateRequest.class);
        TemplateResponse response = imageS3.updateTemplate(request);
        String respJson = objectMapper.writeValueAsString(response);
        return ok(respJson);
    }
    
}
