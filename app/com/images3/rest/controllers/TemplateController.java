/*******************************************************************************
 * Copyright 2014 Rui Sun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
    
    public Result getTemplates(String id, String page, String archived) throws IOException {
        PaginatedResult<List<TemplateResponse>> pages = null;
        if (null == archived || archived.trim().length() == 0) {
            pages = imageS3.getAllTemplates(id);
        } else if ("false".equalsIgnoreCase(archived)) {
            pages = imageS3.getActiveTempaltes(id);
        } else if ("true".equalsIgnoreCase(archived)) {
            pages = imageS3.getArchivedTemplates(id);
        } else {
            return internalServerError("'archived' must be true or false.");
        }
        if (page.equalsIgnoreCase("ALL")) {
            List<TemplateResponse> templates = pages.getAllResults();
            PaginatedResultModel<List<TemplateResponse>> response = 
                    new PaginatedResultModel<List<TemplateResponse>>(null, null, templates);
            String respJson = objectMapper.writeValueAsString(response);
            return ok(respJson);
        }
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
