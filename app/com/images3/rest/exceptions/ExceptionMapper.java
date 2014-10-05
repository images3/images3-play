package com.images3.rest.exceptions;
import play.mvc.Result;
import play.mvc.Results;


public abstract class ExceptionMapper {

    private ExceptionMapper successor;
    
    public ExceptionMapper(ExceptionMapper successor) {
        this.successor = successor;
    }
    
    public Result toResult(Throwable t) {
        if (isMatch(t)) {
            return getResult(t);
        }
        if (null == successor) {
            return Results.internalServerError();
        }
        return successor.toResult(t);
    }
    
    protected abstract boolean isMatch(Throwable t);
    
    protected abstract Result getResult(Throwable t);
    
}
