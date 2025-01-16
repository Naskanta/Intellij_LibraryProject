package gr.aueb.cf.library.core.exceptions;

public class ObjectNotFoundException extends GenericException{
    private static final String DEFAULT_CODE = "Object Not Found";

    public ObjectNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
