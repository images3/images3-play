package com.images3.rest;

import java.util.LinkedHashMap;
import java.util.Map;

public class PaginatedResultResponse<T> {

    private Map<String, String> page;
    private T results;
    
    public PaginatedResultResponse(String prev, String next, T results) {
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
