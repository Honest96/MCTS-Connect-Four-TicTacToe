package com.example.ConnectFour.connect4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Connect4Board {
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public int[][] boardValues1 = new int[ROWS][COLUMNS];

    public static final int IN_PROGRESS = -1;
    public static final int DRAW = 0;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;

    // public static final int DEFAULT_BOARD_SIZE = COLUMNS;
    public static final int DEFAULT_COLUMNS = COLUMNS;
    public static final int DEFAULT_ROWS = ROWS;

    public Connect4Board() {
        for (int[] row : boardValues1) {
            Arrays.fill(row, 0);
        }
    }

    public Connect4Board(Connect4Board board) {
        this.boardValues1 = new int[ROWS][COLUMNS];
        for ( int row=0; row<ROWS; row++) {
            System.arraycopy(board.boardValues1[row], 0, this.boardValues1[row], 0, COLUMNS);
        }
    }

    public boolean performConnect4Move(int player, int column) {
        if (column < 0 || column >= COLUMNS) {
            throw new IllegalArgumentException("Invalid column index: " + column);
        }
        for (int row=ROWS-1; row>=0; row--) {
            if (boardValues1[row][column] == 0) {
                boardValues1[row][column] = player;
                return true;
            }
        }
        return false;
    }

    public int checkConnect4Status() {
        for (int row=0; row<ROWS; row++) {
            for (int column=0; column<COLUMNS; column++) {
                if (boardValues1[row][column] == 0) continue;
    
                int player = boardValues1[row][column];
    
                if (column+3 < COLUMNS &&
                    player == boardValues1[row][column+1] &&
                    player == boardValues1[row][column+2] &&
                    player == boardValues1[row][column+3]) {
                    return player;
                }
                if (row+3 < ROWS &&
                    player == boardValues1[row+1][column] &&
                    player == boardValues1[row+2][column] &&
                    player == boardValues1[row+3][column]) {
                    return player;
                }
                if (row+3 < ROWS && column+3 < COLUMNS &&
                    player == boardValues1[row+1][column+1] &&
                    player == boardValues1[row+2][column+2] &&
                    player == boardValues1[row+3][column+3]) {
                    return player;
                }
                if (row+3 < ROWS && column-3 >= 0 &&
                    player == boardValues1[row+1][column-1] &&
                    player == boardValues1[row+2][column-2] &&
                    player == boardValues1[row+3][column-3]) {
                    return player;
                }
            }
        }

        for (int[] row : boardValues1) {
            for (int cell : row) {
                if (cell == 0) {
                    return IN_PROGRESS;
                }
            }
        }
        return DRAW;
    }

    public List<Connect4Position> getEmptyPositions() {
        List<Connect4Position> emptyPositions = new ArrayList<>();
        for (int column=0; column<COLUMNS; column++) {
            for (int row=ROWS-1; row>=0; row--) {
                if (boardValues1[row][column] == 0) {
                    emptyPositions.add(new Connect4Position(row, column));
                    break;
                }
            }
        }
        return emptyPositions;
    }

    public void displayConnect4Board() {
        char[] symbols = {' ', 'X', 'O'};
        for (int row=0; row<ROWS; row++) {
            for (int column=0; column<COLUMNS; column++) { 
                System.out.print("|" + symbols[boardValues1[row][column]]);
            }
            System.out.println("|");
        }
    }
    

    public void printStatus() {
        int status = this.checkConnect4Status();
        if (status == PLAYER_1) {
            System.out.println("Player 1 (HUMAN) WINS!");
        } else if (status == PLAYER_2) {
            System.out.println("Player 2 (AI) WINS!");
        } else if (status == DRAW) {
            System.out.println("It's a DRAW!");
        } else {
            System.out.println("Game In Progress");
        }
    }

    public int[][] getConnect4BoardValues() {
        return boardValues1;
    }
}
