package com.csheros.packman.engine;

import com.csheros.packman.R;
import com.csheros.packman.ai.BFS;
import com.csheros.packman.utils.Direction;

import lombok.Data;

@Data
public class Creature {

    /**
     * Parent Node
     */
    private Node node;
    private Node firstNode;
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

    protected Direction getNextDirection() {
        switch (this.getType()) {
            case PACK_MAN:
                currentDirection = getNode().getNodeMap().getPackManDirection();
                if (currentDirection == null)
                    currentDirection = Direction.STAND_STILL;
                break;
            default:
                currentDirection = Direction.STAND_STILL;
        }
        return currentDirection;
    }

    public int getImgAsset() {
        switch (this.getType()) {
            case PACK_MAN:
                if (currentDirection == Direction.UP)
                    return R.drawable.ic_packman_up;
                else if (currentDirection == Direction.DOWN)
                    return R.drawable.ic_packman_down;
                else if (currentDirection == Direction.LEFT)
                    return R.drawable.ic_packman_left;
                else
                    return R.drawable.ic_packman_right;
            case EVIL_CREATURE:
                return isReversed() ?
                        R.drawable.ic_gohat_reversed :
                        R.drawable.ic_gohat;
            case MASTER_POINT:
                return R.drawable.ic_dot_master;
            case POINT:
                return R.drawable.ic_dot;
            default:
                return R.drawable.square;
        }
    }

    public void reset() {
        this.firstNode.addCreature(this);
        this.currentDirection = Direction.STAND_STILL;
        this.isReversed = false;
    }

    public enum Type {
        PACK_MAN, EVIL_CREATURE, POINT, MASTER_POINT, BLOCK
    }
}
