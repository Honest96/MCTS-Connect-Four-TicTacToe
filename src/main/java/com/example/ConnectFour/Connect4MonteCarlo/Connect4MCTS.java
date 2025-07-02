package com.example.ConnectFour.Connect4MonteCarlo;

import java.util.List;
import java.util.Random;
import com.example.ConnectFour.connect4Tree.Connect4Node;
import com.example.ConnectFour.connect4Tree.Connect4Tree;
import com.example.ConnectFour.connect4.Connect4Board;
import com.example.ConnectFour.connect4.Connect4Position;

public class Connect4MCTS {
    public static final int WIN_SCORE = 10;
    private int _level;
    private int _opponent;
    private static final Random RANDOM = new Random();

    public Connect4MCTS() {
        this._level = 5;
    }

    public int getConnect4Level() {
        return _level;
    }

    public void setConnect4Level(int level) {
        this._level = level;
    }

    private int getMillisForCurrentLevel() {
        return 2 * (_level - 1) + 1;
    }

    public Connect4Board _findNextMove(Connect4Board _board, int playerNum) {
        long _start = System.currentTimeMillis();
        long _end = _start + 1000 * getMillisForCurrentLevel();

        Connect4Board boardCopy = new Connect4Board(_board);
        _opponent = 3 - playerNum;

        Connect4Tree _tree = new Connect4Tree();
        Connect4Node _rootNode = _tree.getConnect4Root();
        _rootNode.getConnect4State().setConnect4Board(boardCopy);
        _rootNode.getConnect4State().setConnect4PlayerNum(_opponent);

        while (System.currentTimeMillis() < _end) {
            Connect4Node _promisingNode = selectPromisingNode(_rootNode);
            if (_promisingNode.getConnect4State().getConnect4Board().checkConnect4Status() == Connect4Board.IN_PROGRESS) {
                expandNode(_promisingNode);
            }

            Connect4Node _nodeToExplore = _promisingNode;
            if (!_promisingNode.getConnect4ChildArray().isEmpty()) {
                _nodeToExplore = _promisingNode.getConnect4RandomChildNode();
            }

            int _playoutResult = simulateRandomPlayout(_nodeToExplore);
            backPropogation(_nodeToExplore, _playoutResult);
        }

        Connect4Node _winnerNode = _rootNode.getConnect4ChildWithMaxScore();
        int aiMoveColumn = _winnerNode.getConnect4State().getBestMoveColumn();

        if (aiMoveColumn < 0 || aiMoveColumn >= Connect4Board.COLUMNS) {
            throw new IllegalStateException("AI selected an invalid column: " + aiMoveColumn);
        }

        boardCopy.performConnect4Move(playerNum, aiMoveColumn);
        return boardCopy;
    }

        private void expandNode(Connect4Node _node) {
        List<Connect4State> _possibleStates = _node.getConnect4State().getAllConnect4PossibleStates();
        _possibleStates.forEach(state -> {
            Connect4Node _newNode = new Connect4Node(_node);
            _newNode.setConnect4Parent(_node);
            _newNode.getConnect4State().setConnect4PlayerNum(_node.getConnect4State().getConnect4Opponent());
            _node.getConnect4ChildArray().add(_newNode);
        });
    }

    private Connect4Node selectPromisingNode(Connect4Node _rootNode) {
        Connect4Node _node = _rootNode;
        while (!_node.getConnect4ChildArray().isEmpty()) {
            Connect4Node bestChild = findBestNodeWithUCT(_node);
            if (bestChild == _node) {
                break;
            }
            _node = bestChild;
        }
        return _node;
    }

    private Connect4Node findBestNodeWithUCT(Connect4Node node) {
        double maxUCT = Double.NEGATIVE_INFINITY;
        Connect4Node bestNode = node;

        for (Connect4Node child : node.getConnect4ChildArray()) {
            double winRate = (double) child.getConnect4State().getWinScore() / (child.getConnect4State().getVisitCount() + 1);
            double exploration = Math.sqrt(Math.log(node.getConnect4State().getVisitCount() + 1) / (child.getConnect4State().getVisitCount() + 1));

            double uctValue = winRate + 1.4 * exploration;

            if (uctValue > maxUCT) {
                maxUCT = uctValue;
                bestNode = child;
            }
        }
        return bestNode;
    }
    
    private int simulateRandomPlayout(Connect4Node node) {
        Connect4Board boardCopy = new Connect4Board(node.getConnect4State().getConnect4Board());
        int currentPlayer = node.getConnect4State().getConnect4PlayerNum();
        int status = boardCopy.checkConnect4Status();

        while (status == Connect4Board.IN_PROGRESS) {
            List<Connect4Position> availableMoves = boardCopy.getEmptyPositions();
            int bestMoveColumn = -1;

            //Check if AI can win immediately
            for (Connect4Position move : availableMoves) {
                Connect4Board testBoard = new Connect4Board(boardCopy);
                testBoard.performConnect4Move(currentPlayer, move.getColumn());
                if (testBoard.checkConnect4Status() == currentPlayer) {
                    bestMoveColumn = move.getColumn();
                    break;
                }
            }

            //Block opponent's winning move
            if (bestMoveColumn == -1) {
                int opponent = 3 - currentPlayer;
                for (Connect4Position move : availableMoves) {
                    Connect4Board testBoard = new Connect4Board(boardCopy);
                    testBoard.performConnect4Move(opponent, move.getColumn());
                    if (testBoard.checkConnect4Status() == opponent) {
                        bestMoveColumn = move.getColumn();
                        break;
                    }
                }
            }

            //Prioritise central moves if no clear advantage
            if (bestMoveColumn == -1) {
                bestMoveColumn = availableMoves.get(0).getColumn();
                for (Connect4Position move : availableMoves) {
                    if (Math.abs(move.getColumn() - 3) < Math.abs(bestMoveColumn - 3)) {
                        bestMoveColumn = move.getColumn();
                    }
                }
            }

            boardCopy.performConnect4Move(currentPlayer, bestMoveColumn);
            currentPlayer = 3 - currentPlayer;
            status = boardCopy.checkConnect4Status();
        }

        return status;
    }

    private void backPropogation(Connect4Node node, int winner) {
        while (node != null) {
            node.getConnect4State().incrementVisit();
            if (node.getConnect4State().getConnect4PlayerNum() == winner) {
                node.getConnect4State().addScore(WIN_SCORE);
            }
            node = node.getConnect4Parent();
        }
    }
}