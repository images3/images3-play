package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.DuplicateImagePlantNameException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class DuplicateImagePlantNameExceptionMapper extends
        PreciseExceptionMapper {

    public DuplicateImagePlantNameExceptionMapper(Class<?> exceptionClass,
            ExceptionMapper successor) {
        super(exceptionClass, successor);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Result getResult(Throwable t) {
        DuplicateImagePlantNameException exp = (DuplicateImagePlantNameException) t.getCause();
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("name", exp.getName());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.DUPLICATE_IMAGEPLANT_NAME, 
                details, 
                "ImagePlant name, " + exp.getName() + " has been taken.");
        return Results.badRequest(Json.toJson(response));
    }

}
