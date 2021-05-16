package com.csheros.packman.engine;

import com.csheros.packman.ai.BFS;
import com.csheros.packman.ai.DFS;
import com.csheros.packman.utils.Direction;

public class EvilCreature extends Creature {
    public enum PathAlgo {
        RANDOM, BFS,DFS
    }

    private final PathAlgo pathAlgo;

    public EvilCreature(PathAlgo pathAlgo) {
        super(Type.EVIL_CREATURE);
        this.pathAlgo = pathAlgo;
    }

    @Override
    protected Direction getNextDirection() {
        switch (pathAlgo) {
            case BFS:
                setCurrentDirection(new BFS().getNextMoveDirection(
                        getNode().getPosition(),
                        getNode().getNodeMap().getPackManPosition(),
                        getNode().getNodeMap()
                ));
                break;
            case DFS:
                setCurrentDirection(new DFS().getNextMoveDirection(
                        getNode().getPosition(),
                        getNode().getNodeMap().getPackManPosition(),
                        getNode().getNodeMap()
                ));
                break;
            default:
                setCurrentDirection(Direction.getRandomDirection());
        }
        return getCurrentDirection();
    }
}
