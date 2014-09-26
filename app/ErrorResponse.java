import java.util.Collections;
import java.util.Map;


public class ErrorResponse {
    
    public static final int NO_SUCH_ENTITY = 400100;
    public static final int DUPLICATE_IMAGEPLANT_NAME = 400101;
    public static final int DUPLICATE_IMAGE_VERSION = 400102;
    public static final int DUPLICATE_TEMPALTE_NAME = 400103;
    public static final int ILLEGAL_IMAGEPLANT_NAME_LENGTH = 400104;
    public static final int ILLEGAL_RESIZING_DIMENSIONS = 400105;
    public static final int ILLEGAL_TEMPLATE_NAME = 400106;
    public static final int ILLEGAL_TEMPLATE_NAME_LENGTH = 400107;
    public static final int UNKNOWN_IMAGE_FORMAT = 400108;
    public static final int UNREMOVABLE_TEMPLATE = 400109;

    private int code;
    private Map<String, Object> values;
    private String message;
    
    public ErrorResponse(int code, Map<String, Object> values, String message) {
        this.code = code;
        this.values = Collections.unmodifiableMap(values);
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    public Map<String, Object> getValues() {
        return values;
    }
    public String getMessage() {
        return message;
    }
    
    
    
}
