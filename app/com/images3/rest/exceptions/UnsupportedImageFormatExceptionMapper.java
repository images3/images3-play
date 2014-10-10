package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.UnsupportedImageFormatException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class UnsupportedImageFormatExceptionMapper extends
        PreciseExceptionMapper {

    public UnsupportedImageFormatExceptionMapper(
            ExceptionMapper successor) {
        super(UnsupportedImageFormatException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        UnsupportedImageFormatException exception = (UnsupportedImageFormatException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.UNSUPPORTED_IMAGE_FORMAT, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}
