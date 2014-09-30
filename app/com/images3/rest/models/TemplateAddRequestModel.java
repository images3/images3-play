package com.images3.rest.models;

import com.images3.common.ResizingConfig;

public class TemplateAddRequestModel {

    private String name;
    private ResizingConfig resizingConfig;
    
    public String getName() {
        return name;
    }
    public ResizingConfig getResizingConfig() {
        return resizingConfig;
    }
    
}
