package com.images3.rest.exceptions;

public abstract class PreciseExceptionMapper extends ExceptionMapper {

    private Class<?> exceptionClass;
    
    public PreciseExceptionMapper(Class<?> exceptionClass) {
        this(exceptionClass, null);
    }
    
    public PreciseExceptionMapper(Class<?> exceptionClass, ExceptionMapper successor) {
        super(successor);
        this.exceptionClass = exceptionClass;
    }

    @Override
    protected boolean isMatch(Throwable t) {
        return (exceptionClass.equals(t.getClass()));
    }
}
