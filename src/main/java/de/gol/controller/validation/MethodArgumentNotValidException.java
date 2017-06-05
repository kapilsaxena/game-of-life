package de.gol.controller.validation;

public class MethodArgumentNotValidException extends RuntimeException {
    private String field;

    public MethodArgumentNotValidException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
