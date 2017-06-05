package de.gol.controller;

import de.gol.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class GameOfLifeController {

    private static final Logger logger = LoggerFactory.getLogger(GameOfLifeController.class);
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

    @RequestMapping(value = "/newGame", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity next(@RequestParam("height") int height, @RequestParam("width") int width) {
        return ResponseEntity.ok(BoardCommand.build(new Board(height,width).toBoolean()));
    }

    @RequestMapping(value = "/next", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity next(@Valid @RequestBody BoardCommand boardCommand, BindingResult result) {
        if (result.hasErrors()) {
            return buildBadRequestResponse(result);
        }
        Board board = new Board(boardCommand.getBoard()).doNext(); // Build board from state and do next move
        return ResponseEntity.ok(BoardCommand.build(board.toBoolean()));
    }

//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Oops something went wrong, please try again later")
//    @ExceptionHandler(Exception.class)
//    public void internalServerExceptionHandler(Exception ex) {
//        logger.error(ex.getMessage(), ex);
//    }

    private ResponseEntity<List<String>> buildBadRequestResponse(BindingResult result) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList()));
    }

}
