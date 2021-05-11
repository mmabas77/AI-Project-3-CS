package com.csheros.packman.ai;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.engine.NodePosition;
import com.csheros.packman.utils.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.Data;

@Data
public class AlgoNode {
    // Extra
    private NodeMap nodeMap;
    // Path Data
    private AlgoNode parent;
    // Self Data
    private Direction action;
    private NodePosition state;

    /**
     * @param nodeMap
     * @param state
     */
    public AlgoNode(NodeMap nodeMap, NodePosition state) {
        this.nodeMap = nodeMap;
        // Root Generator
        this.parent = null;
        this.action = Direction.STAND_STILL;
        // Received Position
        this.state = state;
    }

    /**
     * @param nodeMap
     * @param state
     * @param parent
     * @param action
     */
    public AlgoNode(
            NodeMap nodeMap,
            NodePosition state,
            AlgoNode parent,
            Direction action
    ) {
        this.nodeMap = nodeMap;
        // Root Generator
        this.parent = parent;
        this.action = action;
        // Received Position
        this.state = state;
    }

    public List<AlgoNode> getChildren() {
        List<AlgoNode> allowedNodes = new ArrayList<>(4);

        NodePosition upPosition = NodePosition
                .calculateFromPositionWithDirection(this.state, Direction.UP);
        if (nodeMap.canMoveToPosition(upPosition))
            allowedNodes.add(new AlgoNode(nodeMap, upPosition, this, Direction.UP));

        NodePosition downPosition = NodePosition
                .calculateFromPositionWithDirection(this.state, Direction.DOWN);
        if (nodeMap.canMoveToPosition(downPosition))
            allowedNodes.add(new AlgoNode(nodeMap, downPosition, this, Direction.DOWN));

        NodePosition rightPosition = NodePosition
                .calculateFromPositionWithDirection(this.state, Direction.RIGHT);
        if (nodeMap.canMoveToPosition(rightPosition))
            allowedNodes.add(new AlgoNode(nodeMap, rightPosition, this, Direction.RIGHT));


        NodePosition leftPosition = NodePosition
                .calculateFromPositionWithDirection(this.state, Direction.LEFT);
        if (nodeMap.canMoveToPosition(leftPosition))
            allowedNodes.add(new AlgoNode(nodeMap, leftPosition, this, Direction.LEFT));

        return allowedNodes;
    }

    public Direction getPathDirection() {
        Stack<AlgoNode> pathNodes = new Stack<>();
        AlgoNode current = this;
        while (current.parent != null) {
            pathNodes.push(current);
            current = current.parent;
        }
        if (pathNodes.size() > 0) {
            System.out.println("Path ...");
            for (AlgoNode pathNode : pathNodes) {
                System.out.println("Row:" + pathNode.getState().getRow() +
                        "  Col:" + pathNode.getState().getCol() +
                        " Direction:" + pathNode.getAction());
            }
            return pathNodes.pop().getAction();
        }
        return Direction.STAND_STILL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlgoNode node = (AlgoNode) o;
        return node.getState().equals(this.getState());
    }
}
