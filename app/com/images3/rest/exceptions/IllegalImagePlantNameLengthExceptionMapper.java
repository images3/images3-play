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

import com.images3.exceptions.IllegalImagePlantNameLengthException;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class IllegalImagePlantNameLengthExceptionMapper extends
        PreciseExceptionMapper {

    public IllegalImagePlantNameLengthExceptionMapper(
            ExceptionMapper successor) {
        super(IllegalImagePlantNameLengthException.class, successor);
    }

    @Override
    protected Result getResult(Throwable t) {
        IllegalImagePlantNameLengthException exception = (IllegalImagePlantNameLengthException) t;
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("name", exception.getName());
        details.put("minLength", exception.getMinLength());
        details.put("maxLength", exception.getMaxLength());
        ErrorResponse response = new ErrorResponse(
                ErrorResponse.ILLEGAL_IMAGEPLANT_NAME_LENGTH, 
                details, 
                exception.getMessage());
        return Results.badRequest(Json.toJson(response));
    }

}
