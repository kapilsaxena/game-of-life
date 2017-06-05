package de.gol.model;


import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class Board {
    private Cell[][] grid;
    private int height = 3;
    private int width = 3;


    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = initByHeightAndWidth(height, width);
    }


    public Board(boolean[][] gridState) {
        height = gridState.length;
        width = gridState[0].length;
        this.grid = initByState(gridState);
    }

    private Cell[][] initByState(boolean[][] gridState) {
        Cell[][] grid = new Cell[height][width];
        range(0, height).forEach(h -> range(0, width).forEach(w -> {
            grid[h][w] = new Cell(false);
            if (gridState[h][w]) {
                grid[h][w].setNewState(true);
                grid[h][w].updateState();
            }
        }));
        return grid;
    }

    private Cell[][] initByHeightAndWidth(int height, int width) {
        Cell[][] grid = new Cell[height][width];
        range(0, height)
                .forEach(h -> range(0, width)
                        .forEach(w -> grid[h][w] = new Cell(false)));
        return grid;
    }

    public boolean[][] toBoolean() {
        boolean[][] booleanGrid = new boolean[height][width];

        range(0, height).forEach(h -> range(0, width).forEach(w -> {
            if (grid[h][w].getState()) {
                booleanGrid[h][w] = true;
            }
        }));
        return booleanGrid;
    }

    public int getSize() {
        return width;
    }

    private int neighboursCountAt(int row, int col) {
        int sum = 0;
        // Positions numbered as phone dial
        if (row != 0 && col != 0) {    //1
            if (isAlive(row - 1, col - 1)) {
                sum++;
            }
        }

        if (row != 0) {
            if (isAlive(row - 1, col)) { //2
                sum++;
            }
        }

        if (row != 0 && col != width - 1) {//3
            if (isAlive(row - 1, col + 1)) {
                sum++;
            }
        }
        if (col != 0) {
            if (isAlive(row, col - 1)) { //4
                sum++;
            }
        }
        //self
        if (col != width - 1) {
            if (isAlive(row, col + 1)) { //6
                sum++;
            }
        }

        if (row != height - 1 && col != 0) {
            if (isAlive(row + 1, col - 1)) { //7
                sum++;
            }
        }

        if (row != height - 1) {
            if (isAlive(row + 1, col)) { //8
                sum++;
            }
        }

        if (row != height - 1 && col != width - 1) {
            if (isAlive(row + 1, col + 1)) { //9
                sum++;
            }
        }

        return sum;
    }

    private boolean isAlive(int row, int col) {
        return grid[row][col].getState();
    }

    public Board doNext() {
        prepare();
        commit();
        return this;
    }

    /**
     * Assigns new state to individual Cells
     * according to GoF rules
     */
    private void prepare() {
        range(0, grid.length).forEach(h -> range(0, grid[h].length).forEach(w -> {
            int nr = neighboursCountAt(h, w);
            if (nr < 2) {
                grid[h][w].setNewState(false);
            }  //underpop
            else if (nr > 3) {
                grid[h][w].setNewState(false);
            } //overcrowd
            else if (nr == 3) {
                grid[h][w].setNewState(true);
            } //born
            else if (nr == 2) {
                grid[h][w].setNewState(grid[h][w].getState());
            } // stay same
        }));
    }

    /**
     * Updates Cell state based on newState
     */
    private void commit() {
        stream(grid).forEach(row -> stream(row).forEach(Cell::updateState));
    }
}