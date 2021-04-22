package com.csheros.packman.engine;

import com.csheros.packman.utils.Direction;
import com.csheros.packman.utils.Injector;

import lombok.AccessLevel;
import lombok.Setter;


@Setter
public class Node {


    public enum TYPE {
        PACK_MAN,
        POINT,
        MASTER_POINT,
        EVIL_CREATURE,
        BLOCK,
        EMPTY
    }

    public enum FrameState {
        LOCKED, UNLOCKED
    }

    /**
     * Default is POINT
     */
    private TYPE type;
    /**
     * Default is STAND_STILL
     */
    private Direction direction;
    /**
     * We get from the map
     */
    @Setter(AccessLevel.PRIVATE)
    private NodePosition position;
    /**
     * For calculating next direction of pack-man
     */
    @Setter(AccessLevel.PRIVATE)
    private NodeMap nodeMap;
    /**
     * Used to lock already changed object till other frame changes take place
     */
    private FrameState frameState;

    public Node(NodePosition position, NodeMap nodeMap) {
        this.type = TYPE.POINT;
        this.direction = Direction.STAND_STILL;
        this.position = position;
        this.nodeMap = nodeMap;
        this.frameState = FrameState.UNLOCKED;
    }

    public Direction calculateNextDirection() {
        switch (this.type) {
            case PACK_MAN:
                return nodeMap.getPackManDirection();
            case EVIL_CREATURE:
                return Injector.getNextMoveCalculator().getNextMoveDirection(
                        this.position, nodeMap.getPackManLastPosition(), nodeMap
                );
            default:
                return Direction.STAND_STILL;
        }
    }
}
