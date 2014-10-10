package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.UnachievableTemplateException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class UnachievableTemplateExceptionMapper extends PreciseExceptionMapper {

    public UnachievableTemplateExceptionMapper(
            ExceptionMapper successor) {
        super(UnachievableTemplateException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        UnachievableTemplateException exception = (UnachievableTemplateException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("id", exception.getId());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.UNARCHIVABLE_TEMPLATE, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}
