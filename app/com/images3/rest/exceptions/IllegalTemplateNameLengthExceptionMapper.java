package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.IllegalTemplateNameLengthException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class IllegalTemplateNameLengthExceptionMapper extends
        PreciseExceptionMapper {

    public IllegalTemplateNameLengthExceptionMapper(
            ExceptionMapper successor) {
        super(IllegalTemplateNameLengthException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        IllegalTemplateNameLengthException exception = (IllegalTemplateNameLengthException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("name", exception.getName());
        details.put("minLength", exception.getMinLength());
        details.put("maxLength", exception.getMaxLength());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.ILLEGAL_TEMPLATE_NAME_LENGTH, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}
