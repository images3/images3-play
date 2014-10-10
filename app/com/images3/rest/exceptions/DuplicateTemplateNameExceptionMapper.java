package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.DuplicateTemplateNameException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class DuplicateTemplateNameExceptionMapper extends
        PreciseExceptionMapper {

    public DuplicateTemplateNameExceptionMapper(
            ExceptionMapper successor) {
        super(DuplicateTemplateNameException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        DuplicateTemplateNameException exception = (DuplicateTemplateNameException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("name", exception.getName());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.DUPLICATE_TEMPALTE_NAME, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}