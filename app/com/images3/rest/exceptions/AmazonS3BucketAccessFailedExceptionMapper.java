/*******************************************************************************
 * Copyright 2014 Rui Sun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
