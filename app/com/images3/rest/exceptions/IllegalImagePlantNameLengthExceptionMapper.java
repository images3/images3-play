package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.IllegalImagePlantNameLengthException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class IllegalImagePlantNameLengthExceptionMapper extends
        PreciseExceptionMapper {

    public IllegalImagePlantNameLengthExceptionMapper(Class<?> exceptionClass,
            ExceptionMapper successor) {
        super(exceptionClass, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        IllegalImagePlantNameLengthException exp = (IllegalImagePlantNameLengthException) t.getCause();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("name", exp.getName());
        values.put("minLength", exp.getMinLength());
        values.put("maxLength", exp.getMaxLength());
        String message = "Length of ImagePlant name need to be greater equal than " + exp.getMinLength()
                + " and less equal than " + exp.getMaxLength();
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.ILLEGAL_IMAGEPLANT_NAME_LENGTH, 
                values, 
                message);
        return Results.badRequest(Json.toJson(response));
    }

}
