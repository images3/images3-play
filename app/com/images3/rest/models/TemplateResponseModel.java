package com.images3.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class TemplateResponseModel {
    
    @JsonProperty("isArchived") abstract public boolean isArchived();
    @JsonProperty("isRemovable") abstract public boolean isRemovable();
    
}
