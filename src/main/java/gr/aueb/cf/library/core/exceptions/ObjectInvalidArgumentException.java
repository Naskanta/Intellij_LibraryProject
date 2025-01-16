package gr.aueb.cf.library.core.exceptions;

public class ObjectInvalidArgumentException extends GenericException{
    private static final String DEFAULT_CODE = "Invalid Argument";

    public ObjectInvalidArgumentException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }

}
