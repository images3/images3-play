package com.images3.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.images3.common.AmazonS3Bucket;
import com.images3.common.ResizingConfig;

public abstract class ImagePlantAddRequestModel {

    ImagePlantAddRequestModel(@JsonProperty("name") String name, 
            @JsonProperty("bucket") AmazonS3Bucket bucket,
            @JsonProperty("resizingConfig") ResizingConfig resizingConfig) {}
    
}
