package com.example.TicTacToe.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int[][] boardValues;
    int totalMoves;

    public static final int DEFAULT_BOARD_SIZE = 3;

    public static final int IN_PROGRESS = -1;
    public static final int DRAW = 0;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;

    public Board() {
        boardValues = new int[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
    }

    public Board(int boardSize) {
        boardValues = new int[boardSize][boardSize];
    }

    public Board(int[][] boardValues) {
        this.boardValues = boardValues;
    }

    public Board(int[][] boardValues, int totalMoves) {
        this.boardValues = boardValues;
        this.totalMoves = totalMoves;
    }

    public Board(Board board) {
        int boardLength = board.getBoardValues().length;
        this.boardValues = new int[boardLength][boardLength];
        int[][] boardValues = board.getBoardValues();
        int n = boardValues.length;
        for (int i=0; i<n; i++) {
            int m = boardValues[i].length;
            for (int j=0; j<m; j++) {
                this.boardValues[i][j] = boardValues[i][j];
            }
        }
    }

    public int performMove(int player, Position p) {
        if (boardValues[p.getRow()][p.getColumn()] != 0) {
            throw new IllegalArgumentException("Invalid move: Cell already occupied.");
        }
    
        this.totalMoves++;
        boardValues[p.getRow()][p.getColumn()] = player;
    
        int status = checkStatus();
        if (status == PLAYER_1) {
            System.out.println("Player 1 WINS!");
        } else if (status == PLAYER_2) {
            System.out.println("Player 2 WINS!");
        } else if (status == DRAW) {
            System.out.println("It's a DRAW!");
        }
    
        return status;
    }
    

    public int[][] getBoardValues() {
        return boardValues;
    }

    public void setBoardValues(int[][] boardValues) {
        this.boardValues = boardValues;
    }

    public int checkStatus() {
        int boardSize = boardValues.length;
        int maxIndex = boardSize-1;
        int[] diag1 = new int[boardSize];
        int[] diag2 = new int[boardSize];
        
        for (int i=0; i<boardSize; i++) {
            int[] row = boardValues[i];
            int[] col = new int[boardSize];
            for (int j=0; j<boardSize; j++) {
                col[j] = boardValues[j][i];
            }

            int checkColumnForWin = checkForWin(col);
            if(checkColumnForWin!=0)
                return checkColumnForWin;
            
            int checkRowForWin = checkForWin(row);
            if(checkRowForWin!=0)
                return checkRowForWin;
            
            diag1[i] = boardValues[i][i];
            diag2[i] = boardValues[maxIndex-i][i];
        }

        int checkDiagonalForWin = checkForWin(diag1);
        if(checkDiagonalForWin!=0)
            return checkDiagonalForWin;
        
        int checkDiagonalForWin2 = checkForWin(diag2);
        if(checkDiagonalForWin2!=0)
            return checkDiagonalForWin2;
        
        if (getEmptyPositions().size()>0)
            return IN_PROGRESS;
        else
            return DRAW;
    }

    private int checkForWin(int[] row) {
        boolean isEqual = true;
        int size = row.length;
        int previous = row[0];
        for (int i=0; i<size; i++) {
            if (previous != row[i]) {
                isEqual = false;
                break;
            }
            previous = row[i];
        }
        if(isEqual)
            return previous;
        else
            return 0;
    }

    public void displayBoard(char player1Symbol, char player2Symbol) {
        int size = this.boardValues.length;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (boardValues[i][j] == Board.PLAYER_1) {
                    System.out.print(player1Symbol + " ");
                } else if (boardValues[i][j] == Board.PLAYER_2) {
                    System.out.print(player2Symbol + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
    

    public List<Position> getEmptyPositions() {
        int size = this.boardValues.length;
        List<Position> emptyPositions = new ArrayList<>();
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (boardValues[i][j] == 0)
                    emptyPositions.add(new Position(i, j));
            }
        }
        return emptyPositions;
    }

    public void printStatus() {
        int status = this.checkStatus();
        if (status == PLAYER_1) {
            System.out.println("Player 1 WINS");
        } else if (status == PLAYER_2) {
            System.out.println("Player 2 WINS");
        } else if (status == DRAW) {
            System.out.println("It's a DRAW");
        } else {
            System.out.println("Game Still Playing");
        }
    }
}