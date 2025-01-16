package gr.aueb.cf.library.core.exceptions;

public class ObjectAlreadyExistsException extends GenericException {
    private static final String DEFAULT_CODE = "AlreadyExists";

    public ObjectAlreadyExistsException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
