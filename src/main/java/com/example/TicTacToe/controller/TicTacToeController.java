package com.example.TicTacToe.controller;

import org.springframework.web.bind.annotation.*;
import com.example.TicTacToe.montecarlo.MonteCarloTreeSearch;
import com.example.TicTacToe.tictactoe.Board;
import com.example.TicTacToe.tictactoe.Position;

import java.util.Random;

@RestController
@RequestMapping("/api/tic-tac-toe")
@CrossOrigin(origins = "http://localhost:4200")
public class TicTacToeController {

    private Board board;
    private MonteCarloTreeSearch mcts;
    private int currentPlayer;

    public TicTacToeController() {
        this.board = new Board();
        this.mcts = new MonteCarloTreeSearch();
        this.currentPlayer = new Random().nextBoolean() ? Board.PLAYER_1 : Board.PLAYER_2;
    }

    @PostMapping("/start")
    public String startGame() {
        this.board = new Board();
        this.currentPlayer = new Random().nextBoolean() ? Board.PLAYER_1 : Board.PLAYER_2;

        if (currentPlayer == Board.PLAYER_2) {
            board = mcts.findNextMove(board, currentPlayer);
            currentPlayer = Board.PLAYER_1;
        }
        return "{\"status\":\"success\", \"message\":\"Game started!\"}";
    }

    @PostMapping("/move")
    public String makeMove(@RequestParam int row, @RequestParam int col) {
        if (board.getBoardValues()[row][col] != 0) {
            return "{\"status\":\"invalid\", \"message\":\"Cell already occupied.\"}";
        }

        board.performMove(currentPlayer, new Position(row, col));
        currentPlayer = (currentPlayer == Board.PLAYER_1) ? Board.PLAYER_2 : Board.PLAYER_1;

        if (board.checkStatus() != Board.IN_PROGRESS) {
            return "{\"status\":\"gameOver\", \"result\":" + board.checkStatus() + "}";
        }

        if (currentPlayer == Board.PLAYER_2) {
            board = mcts.findNextMove(board, currentPlayer);
            currentPlayer = Board.PLAYER_1;
        }

        return "{\"status\":\"success\", \"message\":\"Your Turn.\", \"currentPlayer\":" + currentPlayer + "}";
    }

    @PostMapping("/aiMove")
    public int[][] makeAIMove(@RequestBody Board board, @RequestParam int player) {
        return mcts.findNextMove(board, player).getBoardValues();
    }

    @GetMapping("/board")
    public int[][] getBoard() {
        return board.getBoardValues();
    }

    @GetMapping("/status")
    public String getStatus() {
        int status = board.checkStatus();
        String message = "";
        if (status == Board.PLAYER_1) {
            message = "Player 1 WINS!";
        } else if (status == Board.PLAYER_2) {
            message = "Player 2 WINS!";
        } else if (status == Board.DRAW) {
            message = "It's a draw!";
        } else {
            message = "Game Still Playing";
        }
        return "{\"status\":" + status + ", \"message\":\"" + message + "\"}";
    }
}
