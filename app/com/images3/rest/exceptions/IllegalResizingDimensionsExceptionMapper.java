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

import com.images3.exceptions.IllegalResizingDimensionsException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class IllegalResizingDimensionsExceptionMapper extends
        PreciseExceptionMapper {

    public IllegalResizingDimensionsExceptionMapper(
            ExceptionMapper successor) {
        super(IllegalResizingDimensionsException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        IllegalResizingDimensionsException exception = (IllegalResizingDimensionsException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("minimum", exception.getMinimum());
        details.put("maximum", exception.getMaximum());
        details.put("unit", exception.getUnit().toString());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.ILLEGAL_RESIZING_DIMENSIONS, 
                details, 
                exception.getMessage());
        
        return Results.badRequest(Json.toJson(response));
    }

}
