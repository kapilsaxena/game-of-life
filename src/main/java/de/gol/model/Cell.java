package de.gol.model;

class Cell {
    private boolean state = false;
    private boolean newState;

    Cell(boolean state) {
        this.state = state;
    }

    void setNewState(boolean state) {
        newState = state;
    }

    void updateState() {
        state = newState;
    }

    boolean getState() {
        return state;
    }
}
