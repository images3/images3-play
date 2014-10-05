package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.DuplicateTemplateNameException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class DuplicateTemplateNameExceptionMapper extends
        PreciseExceptionMapper {

    public DuplicateTemplateNameExceptionMapper(Class<?> exceptionClass,
            ExceptionMapper successor) {
        super(exceptionClass, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        DuplicateTemplateNameException exp = (DuplicateTemplateNameException) t.getCause();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("name", exp.getName());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.DUPLICATE_TEMPALTE_NAME, 
                values, 
                "Template name, " + exp.getName() + " has been taken.");
        return Results.badRequest(Json.toJson(response));
    }

}
