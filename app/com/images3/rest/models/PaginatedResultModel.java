package com.images3.rest.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class PaginatedResultModel<T> {

    private Map<String, String> page;
    private T results;
    
    public PaginatedResultModel(String prev, String next, T results) {
        this.page = new LinkedHashMap<String, String>(2);
        this.page.put("prev", prev);
        this.page.put("next", next);
        this.results = results;
    }

    public Map<String, String> getPage() {
        return page;
    }

    public T getResults() {
        return results;
    }
    
}
