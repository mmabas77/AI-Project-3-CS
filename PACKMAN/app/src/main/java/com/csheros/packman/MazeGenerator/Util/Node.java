package com.csheros.packman.MazeGenerator.Util;

public class Node {
    public int x;
    public int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Node other) {
        return other.x == this.x && other.y == this.y;
    }

    public static Node GetNodeFromID(int ID ,int mazeHeight , int mazeWeight) {
        return new Node(ID / mazeWeight, ID % mazeWeight);
    }

    public static int GetIDFromNode(Node node , int mazeHeight , int mazeWeight ) {
        return mazeWeight * node.x + node.y;
    }

    public static boolean check(Node node , int mazeHeight , int mazeWeight) {
        return node.x >= 0 && node.y >= 0 && node.x < mazeHeight && node.y < mazeWeight;
    }

    public static boolean check(int x, int y , int mazeHeight , int mazeWeight) {
        return x >= 0 && x < mazeHeight && y >= 0 && y < mazeWeight;
    }

}
