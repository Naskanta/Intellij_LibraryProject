package gr.aueb.cf.library.core.exceptions;

public class ObjectNotAuthorizedException extends GenericException{
    private final static String DEFAULT_CODE = "Object Not Authorized Exception";

    public ObjectNotAuthorizedException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }

}
