package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.DuplicateImageVersionException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class DuplicateImageVersionExceptionMapper extends
        PreciseExceptionMapper {

    public DuplicateImageVersionExceptionMapper(Class<?> exceptionClass,
            ExceptionMapper successor) {
        super(exceptionClass, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        DuplicateImageVersionException exp = (DuplicateImageVersionException) t.getCause();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("templateName", exp.getTemplateName());
        values.put("originalImageId", exp.getOriginalImageId());
        String message = "Image, " + exp.getOriginalImageId() + 
                " has already have a version of " + exp.getTemplateName() + " exist.";
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.DUPLICATE_IMAGE_VERSION, 
                values, 
                message);
        return Results.badRequest(Json.toJson(response));
    }

}
