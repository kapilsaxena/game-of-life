package de.gol.controller;

public class BoardCommand {
    private boolean[][] board;

    public static BoardCommand build(boolean[][] board) {
        BoardCommand boardCommand = new BoardCommand();
        boardCommand.board = board;
        return boardCommand;
    }

    public boolean[][] getBoard() {
        return board;
    }

    public void setBoard(boolean[][] board) {
        this.board = board;
    }
}
