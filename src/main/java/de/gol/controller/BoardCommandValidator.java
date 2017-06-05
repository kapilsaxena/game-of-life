package de.gol.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardCommandValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return BoardCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardCommand cmd = (BoardCommand) target;

        if (cmd.getBoard() == null) {
            errors.rejectValue("board", "1", "Board cannot be null");
            return;
        }

        if (cmd.getBoard().length == 0) {
            errors.rejectValue("board", "2", "Board height cannot be 0");
            return;
        }

        if (cmd.getBoard()[0].length == 0) {
            errors.rejectValue("board", "3", "Board width cannot be 0");
            return;
        }

    }
}