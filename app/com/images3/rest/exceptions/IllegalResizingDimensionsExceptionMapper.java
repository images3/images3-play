package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.IllegalResizingDimensionsException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class IllegalResizingDimensionsExceptionMapper extends
        PreciseExceptionMapper {

    public IllegalResizingDimensionsExceptionMapper(
            ExceptionMapper successor) {
        super(IllegalResizingDimensionsException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        IllegalResizingDimensionsException exception = (IllegalResizingDimensionsException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("minimum", exception.getMinimum());
        details.put("maximum", exception.getMaximum());
        details.put("unit", exception.getUnit().toString());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.ILLEGAL_RESIZING_DIMENSIONS, 
                details, 
                exception.getMessage());
        
        return Results.badRequest(Json.toJson(response));
    }

}
