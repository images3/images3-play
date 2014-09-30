package com.images3.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AmazonS3BucketModel {
    
    AmazonS3BucketModel(@JsonProperty("accessKey") String accessKey, 
            @JsonProperty("secretKey") String secretKey,
            @JsonProperty("name") String name) {}
    
}
