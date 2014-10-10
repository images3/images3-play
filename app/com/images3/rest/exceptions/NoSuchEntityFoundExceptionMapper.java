package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.NoSuchEntityFoundException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class NoSuchEntityFoundExceptionMapper extends PreciseExceptionMapper {

    public NoSuchEntityFoundExceptionMapper(
            ExceptionMapper successor) {
        super(NoSuchEntityFoundException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        NoSuchEntityFoundException exception = (NoSuchEntityFoundException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("entity", exception.getEntity());
        details.put("id", exception.getId());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.NO_SUCH_ENTITY, 
                details, 
                exception.getMessage());
        return Results.notFound(Json.toJson(response));
    }

}
