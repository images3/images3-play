package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.IllegalImagePlantNameLengthException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class IllegalImagePlantNameLengthExceptionMapper extends
        PreciseExceptionMapper {

    public IllegalImagePlantNameLengthExceptionMapper(
            ExceptionMapper successor) {
        super(IllegalImagePlantNameLengthException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        IllegalImagePlantNameLengthException exception = (IllegalImagePlantNameLengthException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("name", exception.getName());
        details.put("minLength", exception.getMinLength());
        details.put("maxLength", exception.getMaxLength());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.ILLEGAL_IMAGEPLANT_NAME_LENGTH, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}
