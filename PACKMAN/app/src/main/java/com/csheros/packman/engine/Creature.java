package com.csheros.packman.engine;

import com.csheros.packman.utils.Direction;

import lombok.Data;

@Data
public class Creature {

    /**
     * Parent Node
     */
    private Node node;
    private final Type type;
    private Direction currentDirection;

    /**
     * Specific for evil creatures
     */
    boolean isReversed;

    public Creature(Type type) {
        this.type = type;
        this.currentDirection = Direction.STAND_STILL;
        this.isReversed = false;
    }


    public NodePosition getNextPosition() {
        Direction nextDirection = getNextDirection();
        return NodePosition.calculateFromPositionWithDirection(
                getNode().getPosition(), nextDirection
        );
    }

    private Direction getNextDirection() {
        switch (this.getType()) {
            case PACK_MAN:
                return getNode().getNodeMap().getPackManDirection();
            case EVIL_CREATURE:
                // Todo : Implement this!
                return Direction.getRandomDirection();
            default:
                return Direction.STAND_STILL;
        }
    }


    public enum Type {
        PACK_MAN, EVIL_CREATURE, POINT, MASTER_POINT, BLOCK
    }
}
