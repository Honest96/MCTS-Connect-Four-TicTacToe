package com.example.ConnectFour.connect4Tree;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.ConnectFour.Connect4MonteCarlo.Connect4State;

public class Connect4Node {
    private Connect4State state;
    private Connect4Node parent;
    private List<Connect4Node> childArray;

    public Connect4Node() {
        this.state = new Connect4State();
        this.childArray = new ArrayList<>();
    }

    public Connect4Node(Connect4State connect4State) {
        this.state = connect4State;
        this.childArray = new ArrayList<>();
    }

    public Connect4Node(Connect4State state, Connect4Node connect4Parent) {
        this.state = state;
        this.parent = connect4Parent;
    }

    public Connect4Node(Connect4Node node) {
        this.state = new Connect4State(node.getConnect4State());
        this.parent = node.getConnect4Parent();
        this.childArray = new ArrayList<>();
        for (Connect4Node child : node.getConnect4ChildArray()) {
            this.childArray.add(new Connect4Node(child));
        }
    }

    public Connect4State getConnect4State() {
        return state;
    }

    public void setConnect4State(Connect4State state) {
        this.state = state;
    }

    public Connect4Node getConnect4Parent() {
        return parent;
    }

    public void setConnect4Parent(Connect4Node parent) {
        this.parent = parent;
    }

    public List<Connect4Node> getConnect4ChildArray() {
        return childArray;
    }

    public void setConnect4ChildArray(List<Connect4Node> childArray) {
        this.childArray = childArray;
    }

    public Connect4Node getConnect4RandomChildNode() {
        int numOfPossibleMoves = this.childArray.size();
        int selectRandom = (int) (Math.random() * numOfPossibleMoves);
        return this.childArray.get(selectRandom);
    }

    public Connect4Node getConnect4ChildWithMaxScore() {
        return Collections.max(this.childArray, Comparator.comparing(c -> {
            return c.getConnect4State().getWinScore();
        }));
    }
}
