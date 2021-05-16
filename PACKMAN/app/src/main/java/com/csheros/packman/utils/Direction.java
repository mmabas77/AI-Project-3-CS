package com.csheros.packman.utils;

public enum Direction {
    UP, DOWN, RIGHT, LEFT, STAND_STILL;

    public static Direction getRandomDirection() {
        int rand = (int) (Math.random() * 4);
        return Direction.values()[rand];
    }
}
