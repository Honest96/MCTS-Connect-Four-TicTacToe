package com.example.ConnectFour.connect4Tree;

// import com.example.ConnectFour.connect4Tree.Connect4Node;

public class Connect4Tree {
    private Connect4Node root;

    public Connect4Tree() {
        root = new Connect4Node();
    }

    public Connect4Tree(Connect4Node root) {
        this.root = root;
    }

    public Connect4Node getConnect4Root() {
        return root;
    }

    public void setConnect4Root(Connect4Node root) {
        this.root = root;
    }

    public void addConnect4Child(Connect4Node parent, Connect4Node child) {
        parent.getConnect4ChildArray().add(child);
    }
}
