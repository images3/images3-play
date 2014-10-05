package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.IllegalResizingDimensionsException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class IllegalResizingDimensionsExceptionMapper extends
        PreciseExceptionMapper {

    public IllegalResizingDimensionsExceptionMapper(Class<?> exceptionClass,
            ExceptionMapper successor) {
        super(exceptionClass, successor);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Result getResult(Throwable t) {
        IllegalResizingDimensionsException exp = (IllegalResizingDimensionsException) t.getCause();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("width", exp.getWidth());
        values.put("height", exp.getHeight());

        ErrorResponse response = new ErrorResponse(
                ErrorResponse.ILLEGAL_RESIZING_DIMENSIONS, 
                values, 
                exp.getMessage());
        
        return Results.badRequest(Json.toJson(response));
    }

}
