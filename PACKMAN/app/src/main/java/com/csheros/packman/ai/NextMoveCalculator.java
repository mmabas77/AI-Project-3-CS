package com.csheros.packman.ai;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.engine.NodePosition;
import com.csheros.packman.utils.Direction;

public interface NextMoveCalculator {
    Direction getNextMoveDirection(
            NodePosition srcNodePosition,
            NodePosition targetNodePosition,
            NodeMap nodeMap
    );
}
