package de.gol.controller.validation;

import de.gol.controller.BoardCommand;
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
            throw new MethodArgumentNotValidException("board","Board cannot be null");
        }

        if (cmd.getBoard().length == 0) {
            throw new MethodArgumentNotValidException("board","Board height cannot be 0");
        }

        if (cmd.getBoard()[0].length == 0) {
            throw new MethodArgumentNotValidException("board","Board width cannot be 0");
        }
    }
}