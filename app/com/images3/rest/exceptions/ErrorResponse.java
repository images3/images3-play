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
import java.util.Collections;
import java.util.Map;


public class ErrorResponse {
    
    public static final int NO_SUCH_ENTITY = 400100;
    public static final int DUPLICATE_IMAGEPLANT_NAME = 400101;
    public static final int DUPLICATE_IMAGE_VERSION = 400102;
    public static final int DUPLICATE_TEMPALTE_NAME = 400103;
    public static final int ILLEGAL_IMAGEPLANT_NAME_LENGTH = 400104;
    public static final int ILLEGAL_RESIZING_DIMENSIONS = 400105;
    public static final int ILLEGAL_TEMPLATE_NAME = 400106;
    public static final int ILLEGAL_TEMPLATE_NAME_LENGTH = 400107;
    public static final int UNSUPPORTED_IMAGE_FORMAT = 400108;
    public static final int UNREMOVABLE_TEMPLATE = 400109;
    public static final int AMAZONS3_BUCKET_ACCESS_FAILED = 400110;
    public static final int UNARCHIVABLE_TEMPLATE = 400111;
    public static final int ILLEGAL_IMAGE_VERSIONG = 400112;

    private int code;
    private Map<String, Object> details;
    private String message;
    
    public ErrorResponse(int code, Map<String, Object> details, String message) {
        this.code = code;
        this.details = Collections.unmodifiableMap(details);
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    public Map<String, Object> getDetails() {
        return details;
    }
    public String getMessage() {
        return message;
    }
    
    
    
}
