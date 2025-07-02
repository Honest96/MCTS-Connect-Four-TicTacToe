package com.example.TicTacToe;

import com.example.TicTacToe.montecarlo.MonteCarloTreeSearch;
import com.example.TicTacToe.tictactoe.Board;
import com.example.TicTacToe.tictactoe.Position;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();

        System.out.println("Welcome to Tic Tac Toe!!!");
        System.out.println("Choose your mode:");
        System.out.println("1. Human Player vs AI (MCTS)");
        System.out.println("2. Run Simulation: Random Player vs AI (MCTS) (100 games)");
        System.out.println("3. Run Simulation: AI (MCTS) vs AI (MCTS) (100 games)");
        System.out.print("Enter your choice (1, 2, or 3): ");

        int choice = scanner.nextInt();

        if (choice == 1) {
            humanVsMCTS(scanner, mcts, random);
        } else if (choice == 2) {
            randomVsMCTSSimulation(mcts, random, 100);
        } else if (choice == 3) {
            MCTSvsMCTSSimulation(mcts, random, 100);
        } else {
            System.out.println("Invalid choice. Please RESTART the program and CHOOSE 1, 2, or 3.");
        }
        scanner.close();
    }

    private static void humanVsMCTS(Scanner scanner, MonteCarloTreeSearch mcts, Random random) {
        Board board = new Board();
        int currentPlayer = random.nextBoolean() ? Board.PLAYER_1 : Board.PLAYER_2;
        char player1Symbol = 'X';
        char player2Symbol = 'O';
        System.out.println((currentPlayer == Board.PLAYER_1 ? "You start first!" : "Computer starts first!") + " Player 1 is " + player1Symbol + " and Player 2 is " + player2Symbol);
        board.displayBoard(player1Symbol, player2Symbol);
    
        while (board.checkStatus() == Board.IN_PROGRESS) {
            if (currentPlayer == Board.PLAYER_1) {
                System.out.println("Your turn! Enter row and column (0, 1, or 2): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
    
                if (row >= 0 && row < Board.DEFAULT_BOARD_SIZE && col >= 0 && col < Board.DEFAULT_BOARD_SIZE
                        && board.getBoardValues()[row][col] == 0) {
                    board.performMove(Board.PLAYER_1, new Position(row, col));
                } else {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }
            } else {
                System.out.println("AI (MCTS) is making a move...");
                board = mcts.findNextMove(board, Board.PLAYER_2);
            }
            board.displayBoard(player1Symbol, player2Symbol);
            int status = board.checkStatus();
    
            if (status != Board.IN_PROGRESS) { 
                displayResults(board); 
                break;
            }
            currentPlayer = (currentPlayer == Board.PLAYER_1) ? Board.PLAYER_2 : Board.PLAYER_1;
        }
    }

    private static void randomVsMCTSSimulation(MonteCarloTreeSearch mcts, Random random, int numGames) {
        int mctsWins = 0;
        int randomWins = 0;
        int draws = 0;

        for (int game=1; game<=numGames; game++) {
            Board board = new Board();
            int currentPlayer = random.nextBoolean() ? Board.PLAYER_1 : Board.PLAYER_2;
            boolean gameInProgress = true;

            while (gameInProgress) {
                if (currentPlayer == Board.PLAYER_1) {
                    makeRandomMove(board, currentPlayer, random);
                } else {
                    board = mcts.findNextMove(board, currentPlayer);
                }

                int status = board.checkStatus();
                if (status != Board.IN_PROGRESS) {
                    gameInProgress = false;
                    if (status == Board.PLAYER_1) {
                        randomWins++;
                    } else if (status == Board.PLAYER_2) {
                        mctsWins++;
                    } else {
                        draws++;
                    }
                }
                currentPlayer = (currentPlayer == Board.PLAYER_1) ? Board.PLAYER_2 : Board.PLAYER_1;
            }
        }
        System.out.println("\nSimulation complete!");
        System.out.println("Results after " + numGames + " games:");
        System.out.println("AI (MCTS) Wins: " + mctsWins);
        System.out.println("Random Player WINS: " + randomWins);
        System.out.println("DRAWS: " + draws);
    }

    private static void MCTSvsMCTSSimulation(MonteCarloTreeSearch mcts, Random random, int numGames) {
        int mcts1Wins = 0;
        int mcts2Wins = 0;
        int draws = 0;

        for (int game=1; game<=numGames; game++) {
            Board board = new Board();
            int currentPlayer = random.nextBoolean() ? Board.PLAYER_1 : Board.PLAYER_2;
            boolean gameInProgress = true;

            while (gameInProgress) {
                board = mcts.findNextMove(board, currentPlayer);

                int status = board.checkStatus();
                if (status != Board.IN_PROGRESS) {
                    gameInProgress = false;
                    if (status == Board.PLAYER_1) {
                        mcts1Wins++;
                    } else if (status == Board.PLAYER_2) {
                        mcts2Wins++;
                    } else {
                        draws++;
                    }
                }
                currentPlayer = (currentPlayer == Board.PLAYER_1) ? Board.PLAYER_2 : Board.PLAYER_1;
            }
        }
        System.out.println("\nAI vs AI Simulation complete!");
        System.out.println("Results after " + numGames + " games:");
        System.out.println("AI (MCTS) 1 WINS: " + mcts1Wins);
        System.out.println("AI (MCTS) 2 WINS: " + mcts2Wins);
        System.out.println("Draws: " + draws);
    }

    private static void makeRandomMove(Board board, int currentPlayer, Random random) {
        while (true) {
            int row = random.nextInt(Board.DEFAULT_BOARD_SIZE);
            int col = random.nextInt(Board.DEFAULT_BOARD_SIZE);

            if (board.getBoardValues()[row][col] == 0) {
                board.performMove(currentPlayer, new Position(row, col));
                break;
            }
        }
    }

    private static void displayResults(Board board) {
        int status = board.checkStatus();
        if (status == Board.PLAYER_1) {
            System.out.println("Player 1 WINS!");
        } else if (status == Board.PLAYER_2) {
            System.out.println("Player 2 WINS!");
        } else {
            System.out.println("It's a DRAW!");
        }
    }
}