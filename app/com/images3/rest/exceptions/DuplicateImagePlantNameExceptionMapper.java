package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.DuplicateImagePlantNameException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class DuplicateImagePlantNameExceptionMapper extends
        PreciseExceptionMapper {

    public DuplicateImagePlantNameExceptionMapper(
            ExceptionMapper successor) {
        super(DuplicateImagePlantNameException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        DuplicateImagePlantNameException exception = (DuplicateImagePlantNameException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("name", exception.getName());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.DUPLICATE_IMAGEPLANT_NAME, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}
