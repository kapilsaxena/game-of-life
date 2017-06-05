package de.gol.controller;

import de.gol.app.GameOfLifeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.* ;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameOfLifeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest(classes= GameOfLifeApplication.class)
@ActiveProfiles("integrationTest")
public class GameOfLifeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateGame() {
        ResponseEntity responseEntity = this.restTemplate.postForEntity("/gol/?height=10&width=20", "", BoardCommand.class);
        BoardCommand body = (BoardCommand)responseEntity.getBody();
        assertThat(body.getBoard().length).isEqualTo(10);
        assertThat(body.getBoard()[0].length).isEqualTo(20);
    }

    @Test
    public void testCreateGameMissingHeight() {
        ResponseEntity responseEntity = this.restTemplate.postForEntity("/gol/?width=20", "", BoardCommand.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateGameMissingWidth() {
        ResponseEntity responseEntity = this.restTemplate.postForEntity("/gol/?height=20", "", BoardCommand.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testNextMoveSuccessCase() {
        boolean[][] board = new boolean[5][3];
        board[1][1] = true;
        board[2][1] = true;
        board[3][1] = true;

        HttpEntity<BoardCommand> requestEntity = new HttpEntity<>(BoardCommand.build(board));

        ResponseEntity responseEntity = this.restTemplate.exchange("/gol/next", HttpMethod.PUT, requestEntity, BoardCommand.class);
        BoardCommand body = (BoardCommand)responseEntity.getBody();
        boolean[][] bodyBoard = body.getBoard();
        assertThat(bodyBoard.length).isEqualTo(5);
        assertThat(bodyBoard[0].length).isEqualTo(3);
        assertThat(bodyBoard[2][0]).isEqualTo(true);
        assertThat(bodyBoard[2][1]).isEqualTo(true);
        assertThat(bodyBoard[2][2]).isEqualTo(true);
    }

    @Test
    public void testNextMoveNullBoard() {
        HttpEntity<BoardCommand> requestEntity = new HttpEntity<>(BoardCommand.build(null));

        ResponseEntity responseEntity = this.restTemplate.exchange("/gol/next", HttpMethod.PUT, requestEntity, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isEqualTo("{\"message\":\"Board cannot be null\",\"field\":\"board\"}");
    }

    @Test
    public void testNextMoveInvalidWidth() {
        boolean[][] invalidBoard = new boolean[0][1];
        HttpEntity<BoardCommand> requestEntity = new HttpEntity<>(BoardCommand.build(invalidBoard));

        ResponseEntity responseEntity = this.restTemplate.exchange("/gol/next", HttpMethod.PUT, requestEntity, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isEqualTo("{\"message\":\"Board height cannot be 0\",\"field\":\"board\"}");
    }

    @Test
    public void testNextMoveInvalidHeight() {
        boolean[][] invalidBoard = new boolean[1][0];
        HttpEntity<BoardCommand> requestEntity = new HttpEntity<>(BoardCommand.build(invalidBoard));

        ResponseEntity responseEntity = this.restTemplate.exchange("/gol/next", HttpMethod.PUT, requestEntity, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isEqualTo("{\"message\":\"Board width cannot be 0\",\"field\":\"board\"}");
    }
}