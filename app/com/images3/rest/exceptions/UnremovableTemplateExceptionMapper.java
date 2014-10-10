package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.UnremovableTemplateException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class UnremovableTemplateExceptionMapper extends PreciseExceptionMapper {

    public UnremovableTemplateExceptionMapper(
            ExceptionMapper successor) {
        super(UnremovableTemplateException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        UnremovableTemplateException exception = (UnremovableTemplateException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("id", exception.getId());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.UNREMOVABLE_TEMPLATE, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}
