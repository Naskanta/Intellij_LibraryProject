package gr.aueb.cf.library.core.exceptions;

import lombok.Getter;

@Getter
public class ServerException extends Exception {
    private final String code;

    public ServerException(String code, String message) {
        super(message);
        this.code = code;
    }
}
