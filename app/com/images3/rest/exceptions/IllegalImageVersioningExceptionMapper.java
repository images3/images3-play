package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.IllegalImageVersioningException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class IllegalImageVersioningExceptionMapper extends
        PreciseExceptionMapper {

    public IllegalImageVersioningExceptionMapper(
            ExceptionMapper successor) {
        super(IllegalImageVersioningException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        IllegalImageVersioningException exception = (IllegalImageVersioningException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("version", exception.getVersion());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.ILLEGAL_IMAGE_VERSIONG, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}
