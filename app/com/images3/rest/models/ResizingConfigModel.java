package com.images3.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.images3.common.ResizingUnit;

public abstract class ResizingConfigModel {
   
    ResizingConfigModel(@JsonProperty("unit") ResizingUnit unit, 
            @JsonProperty("width") int width,
            @JsonProperty("height") int height,
            @JsonProperty("isKeepProportions") boolean isKeepProportions) {}
    
    @JsonProperty("isKeepProportions") abstract public boolean isKeepProportions();
}
