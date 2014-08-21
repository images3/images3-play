package com.images3.rest;

import java.util.LinkedHashMap;
import java.util.Map;

public class PaginatedResultResponse<T> {

    private Map<String, String> paging;
    private T results;
    
    public PaginatedResultResponse(String prev, String next, T results) {
        this.paging = new LinkedHashMap<String, String>(2);
        if (null != prev) {
            this.paging.put("prev", prev);
        }
        if (null != next) {
            this.paging.put("next", next);
        }
        this.results = results;
    }

    public Map<String, String> getPaging() {
        return paging;
    }

    public T getResults() {
        return results;
    }
    
}
