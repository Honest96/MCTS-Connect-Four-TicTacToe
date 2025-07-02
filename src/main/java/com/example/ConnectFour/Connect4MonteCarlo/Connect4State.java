package com.example.ConnectFour.Connect4MonteCarlo;

import java.util.ArrayList;
import java.util.List;

import com.example.ConnectFour.connect4.Connect4Board;
import com.example.ConnectFour.connect4.Connect4Position;
// import com.example.TicTacToe.tictactoe.Position;

public class Connect4State {
    private Connect4Board board;
    private int playerNum;
    private int visitCount;
    private double winScore;

    public Connect4State() {
        board = new Connect4Board();
    }

    public Connect4State(Connect4State state) {
        this.board = new Connect4Board(state.getConnect4Board());
        this.playerNum = state.getConnect4PlayerNum();
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
    }

    public Connect4State(Connect4Board board) {
        this.board = new Connect4Board(board);
    }

    public Connect4Board getConnect4Board() {
        return board;
    }

    public void setConnect4Board(Connect4Board board) {
        this.board = board;
    }

    public int getConnect4PlayerNum() {
        return playerNum;
    }

    public int getConnect4Opponent() {
        return 3 - playerNum;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public double getWinScore() {
        return winScore;
    }

    public void setWinScore(double winScore) {
        this.winScore = winScore;
    }

    public List<Connect4State>getAllConnect4PossibleStates() {
        List<Connect4State> possibleStates = new ArrayList<>();
        List<Connect4Position> availablePositions = board.getEmptyPositions();
        availablePositions.forEach(p -> {
            Connect4State newState = new Connect4State(board);
            newState.setConnect4PlayerNum(3 - this.playerNum);
            newState.getConnect4Board().performConnect4Move(newState.getConnect4PlayerNum(), p.getColumn());
            possibleStates.add(newState);
        });
        return possibleStates;
    }

    public void incrementVisit() {
        this.visitCount++;
    }

    public void addScore(double score) {
        if (this.winScore != Integer.MIN_VALUE)
            this.winScore += score;
    }

    public void randomPlay() {
        List<Connect4Position> availablePositions = board.getEmptyPositions();
        int totalPossibilities = availablePositions.size();
        int selectRandom = (int) (Math.random() * totalPossibilities);
        board.performConnect4Move(playerNum, availablePositions.get(selectRandom).getColumn());
    }

    public void togglePlayer() {
        this.playerNum = (this.playerNum == Connect4Board.PLAYER_1) ? Connect4Board.PLAYER_2 : Connect4Board.PLAYER_1;
    }
    
    public void setConnect4PlayerNum(int playerNum) {
        if (playerNum != Connect4Board.PLAYER_1 && playerNum != Connect4Board.PLAYER_2) {
            throw new IllegalArgumentException("Invalid player number assigned.");
        }
        this.playerNum = playerNum;
    }

    public int getBestMoveColumn() {
        List<Connect4Position> availableMoves = this.board.getEmptyPositions();
        
        if (availableMoves.isEmpty()) {
            throw new IllegalStateException("No valid moves available");
        }

        Connect4Position bestMove = availableMoves.get(0);
        double bestScore = Double.NEGATIVE_INFINITY;
    
        for (Connect4Position move : availableMoves) {
            int column = move.getColumn();
            double moveScore = evaluateMove(column);
    
            if (moveScore > bestScore) {
                bestScore = moveScore;
                bestMove = move;
            }
        }
    
        return bestMove.getColumn();
    }
    
    private double evaluateMove(int column) {
        return Math.random();
    }
    
    
    
}
