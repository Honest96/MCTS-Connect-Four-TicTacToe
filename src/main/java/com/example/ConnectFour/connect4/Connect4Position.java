package com.example.ConnectFour.connect4;

public class Connect4Position {
    private int row;
    private int column;

    public Connect4Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
