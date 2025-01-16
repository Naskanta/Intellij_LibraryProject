package gr.aueb.cf.library.core;

import gr.aueb.cf.library.core.exceptions.*;
import gr.aueb.cf.library.dto.ResponseMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String, String> errors = new HashMap<>();
        for(FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(ObjectNotFoundException e) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(ObjectAlreadyExistsException e) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ObjectInvalidArgumentException.class)
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(ObjectInvalidArgumentException e) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotAuthorizedException.class)
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(ObjectNotAuthorizedException e) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(ServerException e) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
