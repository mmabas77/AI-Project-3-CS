package com.csheros.packman.MazeGenerator;

public abstract class GetMaze {
    int mazeHeight , mazeWeight;
    int squareLength;
    abstract public char[][] GetRandomMaze();
}
