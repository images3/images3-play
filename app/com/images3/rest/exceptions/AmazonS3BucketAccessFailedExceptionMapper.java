package com.images3.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.images3.exceptions.AmazonS3BucketAccessFailedException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class AmazonS3BucketAccessFailedExceptionMapper extends
        PreciseExceptionMapper {

    public AmazonS3BucketAccessFailedExceptionMapper(
            ExceptionMapper successor) {
        super(AmazonS3BucketAccessFailedException.class, successor);
        
    }

    @Override
    protected Result getResult(Throwable t) {
        AmazonS3BucketAccessFailedException exception = (AmazonS3BucketAccessFailedException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("bucket", exception.getBucket());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.AMAZONS3_BUCKET_ACCESS_FAILED, 
                details, 
                exception.getMessage());
        return Results.unauthorized(Json.toJson(response));
    }

}
