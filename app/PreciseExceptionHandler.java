
public abstract class PreciseExceptionHandler extends ExceptionHandler {

    private Class<?> exceptionClass;
    
    public PreciseExceptionHandler(Class<?> exceptionClass) {
        this(exceptionClass, null);
    }
    
    public PreciseExceptionHandler(Class<?> exceptionClass, ExceptionHandler successor) {
        super(successor);
        this.exceptionClass = exceptionClass;
    }

    @Override
    protected boolean isMatch(Throwable t) {
        return (exceptionClass.equals(t.getCause().getClass()));
    }
}
