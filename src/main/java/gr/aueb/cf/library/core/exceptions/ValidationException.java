package gr.aueb.cf.library.core.exceptions;

import org.springframework.validation.BindingResult;

public class ValidationException extends Exception {
    private BindingResult bindingResult;

    public ValidationException(BindingResult bindingResult) {
        super("Validation Error");
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
