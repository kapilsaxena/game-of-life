package de.gol.controller.validation;

public class ValidationErrorDTO {
    private String message;
    private String field;

    public ValidationErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
