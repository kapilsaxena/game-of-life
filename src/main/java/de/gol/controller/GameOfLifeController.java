package de.gol.controller;

import de.gol.controller.validation.BoardCommandValidator;
import de.gol.controller.validation.MethodArgumentNotValidException;
import de.gol.controller.validation.ValidationErrorDTO;
import de.gol.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping({"", "/v1"})
public class GameOfLifeController {

    private final BoardCommandValidator boardCommandValidator;

    @Autowired
    public GameOfLifeController(BoardCommandValidator boardCommandValidator) {
        this.boardCommandValidator = boardCommandValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // Setting custom validator for BoardCommand.class
        binder.setValidator(boardCommandValidator);
    }

    @GetMapping(value = "/newGame", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity next(@RequestParam("height") int height, @RequestParam("width") int width) {
        return ResponseEntity.ok(BoardCommand.build(new Board(height, width).toBoolean()));
    }

    @PostMapping(value = "/next", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity next(@Valid @RequestBody BoardCommand boardCommand, BindingResult result) {
        Board board = new Board(boardCommand.getBoard()).doNext(); // Build board from state and do next move
        return ResponseEntity.ok(BoardCommand.build(board.toBoolean()));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ValidationErrorDTO internalServerExceptionHandler(MethodArgumentNotValidException ex) {
        return new ValidationErrorDTO(ex.getField(), ex.getMessage());
    }

}
