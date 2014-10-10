package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.DuplicateImageVersionException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class DuplicateImageVersionExceptionMapper extends
        PreciseExceptionMapper {

    public DuplicateImageVersionExceptionMapper(
            ExceptionMapper successor) {
        super(DuplicateImageVersionException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        DuplicateImageVersionException exception = (DuplicateImageVersionException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("version", exception.getVersion());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.DUPLICATE_IMAGE_VERSION, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}
