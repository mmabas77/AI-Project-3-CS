package com.csheros.packman.MazeGenerator;

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
}
