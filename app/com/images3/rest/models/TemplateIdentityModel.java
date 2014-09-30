package com.images3.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class TemplateIdentityModel {

    TemplateIdentityModel(@JsonProperty("imagePlantId") String imagePlantId, 
            @JsonProperty("templateName") String templateName) {}
    
}
