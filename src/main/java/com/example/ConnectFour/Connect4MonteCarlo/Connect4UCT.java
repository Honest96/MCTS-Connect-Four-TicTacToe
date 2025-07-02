package com.example.ConnectFour.Connect4MonteCarlo;

import java.util.Collections;
import java.util.Comparator;

import com.example.ConnectFour.connect4Tree.Connect4Node;

public class Connect4UCT {
    public static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        return (nodeWinScore / (double) nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
    }

    public static Connect4Node findBestNodeWithUCTConnect4(Connect4Node node) {
        int parentVisit = node.getConnect4State().getVisitCount();
        return Collections.max(
            node.getConnect4ChildArray(),
            Comparator.comparing(c -> uctValue(parentVisit, c.getConnect4State().getWinScore(), c.getConnect4State().getVisitCount()))
        );
    }
}
