package com.example.ConnectFour;

import com.example.ConnectFour.Connect4MonteCarlo.Connect4MCTS;
import com.example.ConnectFour.connect4.Connect4Board;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Connect4MCTS mcts = new Connect4MCTS();

        System.out.println("Welcome to Connect Four!");
        System.out.println("Choose your mode:");
        System.out.println("1. Human Player vs MCTS AI");
        System.out.println("2. Random Player vs MCTS AI (100 games)");
        System.out.print("Enter your choice (1 or 2): ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                humanVsMcts(scanner, mcts, random);
                break;
            case 2:
                randomVsMcts(mcts, random, 100);
                break;
            default:
                System.out.println("Invalid choice. Please restart the program and choose 1 or 2.");
        }

        scanner.close();
    }

    private static void humanVsMcts(Scanner scanner, Connect4MCTS mcts, Random random) {
        Connect4Board board = new Connect4Board();
        int currentPlayer = random.nextBoolean() ? Connect4Board.PLAYER_1 : Connect4Board.PLAYER_2;
        char player1Symbol = 'X';
        char player2Symbol = 'O';

        System.out.println((currentPlayer == Connect4Board.PLAYER_1 ? "You start first!" : "Computer starts first!") +
                           " Player 1 is " + player1Symbol + " and Player 2 is " + player2Symbol);

        board.displayConnect4Board();

        while (board.checkConnect4Status() == Connect4Board.IN_PROGRESS) {
            if (currentPlayer == Connect4Board.PLAYER_1) {
                System.out.println("Your turn! Enter a column (0 to 6): ");
                int column = scanner.nextInt();

                if (column >= 0 && column < Connect4Board.COLUMNS && board.performConnect4Move(currentPlayer, column)) {
                    board.displayConnect4Board();
                } else {
                    System.out.println("Invalid move or column full. Try again.");
                    continue;
                }
            } else {
                System.out.println("Computer's turn:");
                board = mcts._findNextMove(board, Connect4Board.PLAYER_2);
                board.displayConnect4Board();
            }

            board.printStatus();
            currentPlayer = (currentPlayer == Connect4Board.PLAYER_1) ? Connect4Board.PLAYER_2 : Connect4Board.PLAYER_1;
        }

        displayResult(board);
    }

    private static void randomVsMcts(Connect4MCTS mcts, Random random, int numGames) {
        int mctsWins = 0, randomWins = 0, draws = 0;

        for (int game=1; game<=numGames; game++) {
            System.out.println("Starting game " + game);
            Connect4Board board = new Connect4Board();
            int currentPlayer = random.nextBoolean() ? Connect4Board.PLAYER_1 : Connect4Board.PLAYER_2;
            boolean gameInProgress = true;

            while (gameInProgress) {
                if (currentPlayer == Connect4Board.PLAYER_1) {
                    makeRandomMove(board, currentPlayer, random);
                } else {
                    board = mcts._findNextMove(board, currentPlayer);
                }

                int status = board.checkConnect4Status();
                if (status != Connect4Board.IN_PROGRESS) {
                    gameInProgress = false;
                    if (status == Connect4Board.PLAYER_1) {
                        randomWins++;
                    } else if (status == Connect4Board.PLAYER_2) {
                        mctsWins++;
                    } else {
                        draws++;
                    }
                }

                currentPlayer = (currentPlayer == Connect4Board.PLAYER_1) ? Connect4Board.PLAYER_2 : Connect4Board.PLAYER_1;
            }
        }

        System.out.println("Simulation complete!");
        System.out.println("Results after " + numGames + " games:");
        System.out.println("MCTS AI Wins: " + mctsWins);
        System.out.println("Random Player Wins: " + randomWins);
        System.out.println("Draws: " + draws);
    }

    private static void makeRandomMove(Connect4Board board, int currentPlayer, Random random) {
        while (true) {
            int column = random.nextInt(Connect4Board.COLUMNS);
            if (board.performConnect4Move(currentPlayer, column)) {
                break;
            }
        }
    }

    private static void displayResult(Connect4Board board) {
        int status = board.checkConnect4Status();
        if (status == Connect4Board.PLAYER_1) {
            System.out.println("Player 1 (Human) wins!");
        } else if (status == Connect4Board.PLAYER_2) {
            System.out.println("Player 2 (AI/MCTS) wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}
