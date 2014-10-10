package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.IllegalTemplateNameException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class IllegalTemplateNameExceptionMapper extends PreciseExceptionMapper {

    public IllegalTemplateNameExceptionMapper(
            ExceptionMapper successor) {
        super(IllegalTemplateNameException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        IllegalTemplateNameException exception = (IllegalTemplateNameException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("name", exception.getName());
        details.put("pattern", exception.getPattern());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.ILLEGAL_TEMPLATE_NAME, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}
