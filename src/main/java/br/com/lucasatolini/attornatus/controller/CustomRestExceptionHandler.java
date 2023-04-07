package br.com.lucasatolini.attornatus.controller;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * MethodArgumentNotValidException – This exception is thrown when an argument annotated with @Valid failed validation
     * @param ex the exception to handle
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return a error response
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = new ArrayList<String>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        String message = "Please, check field errors.";

        CustomRestException error = new CustomRestException(HttpStatus.BAD_REQUEST, message, errors);
        return super.handleExceptionInternal(ex, error, headers, error.getStatus(), request);
    }

    /**
     * Catch-all type of logic that deals with all other exceptions that don't have specific handlers
     * @param ex the exception to handle
     * @param request the current request
     * @return a error response
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex,
                                            WebRequest request) {

        CustomRestException error = new CustomRestException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");

        return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
    }

    /**
     * This exception reports the result of constraint violations
     * @param ex the exception to handle
     * @param request the current request
     * @return a error response
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex,
                                                            WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String classSimpleName = violation.getRootBeanClass().getSimpleName();
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.add(classSimpleName + " " + field + ": " + message);
        }

        String message = "Please, check field errors.";

        CustomRestException error = new CustomRestException(HttpStatus.BAD_REQUEST, message, errors);

        return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
    }

    /**
     * This exception reports entity not found.
     * @param ex the exception to handle
     * @param request the current request
     * @return a error response
     */
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex,
                                                            WebRequest request) {

        String message = "Error - Entity not found.";

        CustomRestException error = new CustomRestException(HttpStatus.BAD_REQUEST, message, ex.getMessage());

        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<Object> handleIOException(IOException ex, WebRequest request) {
        String message = "Error format";

        CustomRestException error = new CustomRestException(HttpStatus.BAD_REQUEST, message, ex.getMessage());

        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }
}
