package br.com.lucasatolini.attornatus.service;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrorException extends Exception {
    private Map<String, String> fieldError;

    public ValidationErrorException() {
        this.fieldError = new HashMap<>();
    }
    public Map<String, String> getFieldError() {
        return fieldError;
    }

    public void setFieldError(Map<String, String> fieldError) {
        this.fieldError = fieldError;
    }
}
