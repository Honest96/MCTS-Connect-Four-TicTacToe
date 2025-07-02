package com.example.ConnectFour.Connect4Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ConnectFour.Connect4MonteCarlo.Connect4MCTS;
import com.example.ConnectFour.connect4.Connect4Board;
import com.example.ConnectFour.connect4.Connect4Position;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/connect-four")
@CrossOrigin(origins = "http://localhost:4201")
public class Connect4Controller {
    
    private Connect4Board board;
    private Connect4MCTS mcts;
    private int currentPlayer;
    private Random random = new Random();

    public Connect4Controller() {
        this.board = new Connect4Board();
        this.mcts = new Connect4MCTS();
        this.currentPlayer = random.nextBoolean() ? Connect4Board.PLAYER_1 : Connect4Board.PLAYER_2;
    }

    @PostMapping("/start")
    public Map<String, Object> startGame() {
    this.board = new Connect4Board();
    this.currentPlayer = random.nextBoolean() ? Connect4Board.PLAYER_1 : Connect4Board.PLAYER_2;

    if (currentPlayer == Connect4Board.PLAYER_2) {
        board = mcts._findNextMove(board, currentPlayer);
        currentPlayer = Connect4Board.PLAYER_1;
    }

    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("message", "Game has started!");
    response.put("currentPlayer", currentPlayer);
    response.put("board", board.getConnect4BoardValues());

    return response;
    }

    @PostMapping("/move")
    public Map<String, Object> playerMove(@RequestParam int column) {
    if (column < 0 || column >= Connect4Board.COLUMNS) {
        return createErrorResponse("Invalid column. Pick between 0 and 6.");
    }
    if (!board.performConnect4Move(currentPlayer, column)) {
        return createErrorResponse("Column is full! Try a different move.");
    }

    int status = board.checkConnect4Status();
    if (status != Connect4Board.IN_PROGRESS) {
        return getGameResponse(getGameOverResponse(status));
    }

    currentPlayer = Connect4Board.PLAYER_2;

    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("message", "AI is making a move...");
    response.put("currentPlayer", currentPlayer);
    response.put("board", board.getConnect4BoardValues());

    return response;
    }


@PostMapping("/ai-move")
public Map<String, Object> aiMove() {
    if (currentPlayer != Connect4Board.PLAYER_2) {
        return createErrorResponse("Not AI's turn.");
    }

    board = mcts._findNextMove(board, currentPlayer);
    int status = board.checkConnect4Status();

    currentPlayer = Connect4Board.PLAYER_1;

    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("message", status == Connect4Board.IN_PROGRESS ? "Your turn!" : getGameOverResponse(status));
    response.put("currentPlayer", currentPlayer);
    response.put("board", board.getConnect4BoardValues());

    return response;
}

    @GetMapping("/board")
    public int[][] getConnect4Board() {
        return board.getConnect4BoardValues();
    }

    private Map<String, Object> getGameResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        response.put("currentPlayer", currentPlayer);
        return response;
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", message);
        return response;
    }

    private String getGameOverResponse(int status) {
        if (status == Connect4Board.PLAYER_1) {
            return "HUMAN PLAYER WINS!";
        } else if (status == Connect4Board.PLAYER_2) {
            return "AI WINS!";
        } else {
            return "It's a DRAW!";
        }
    }
}
