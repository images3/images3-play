import play.mvc.Result;
import play.mvc.Results;


public abstract class ExceptionHandler {

    private ExceptionHandler successor;
    
    public ExceptionHandler(ExceptionHandler successor) {
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
