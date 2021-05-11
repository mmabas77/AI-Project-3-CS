package com.csheros.packman.ai;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.engine.NodePosition;
import com.csheros.packman.utils.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.Data;

public class DFS implements NextMoveCalculator {

    @Override
    public Direction getNextMoveDirection(NodePosition srcNodePosition, NodePosition targetNodePosition, NodeMap nodeMap) {
        // Node Holders
        Stack<DFSNode> DFS_Stack = new Stack<>();
        List<DFSNode> visitedNodes = new ArrayList<>();
        // Add root to stack
        DFSNode root = new DFSNode(nodeMap, srcNodePosition);
        DFS_Stack.push(root);
        // Loop on stack to get direction
        while (!DFS_Stack.isEmpty()) {
            // Get current node
            DFSNode currentNode = DFS_Stack.pop();
            // Check goal
            if (currentNode.getState().equals(targetNodePosition)) {
                return currentNode.getPathDirection();
            }
            // Add to visited
            visitedNodes.add(currentNode);
            // Add children to stack if not visited
            List<DFSNode> children = currentNode.getChildren();
            for (DFSNode child : children) {
                if (!visitedNodes.contains(child))
                    DFS_Stack.push(child);
            }
        }
        return Direction.STAND_STILL;
    }

    @Data
    class DFSNode {
        // Extra
        private NodeMap nodeMap;
        // Path Data
        private DFSNode parent;
        // Self Data
        private int depth; // For path cost also (step cost = 1)
        private Direction action;
        private NodePosition state;

        /**
         * @param nodeMap
         * @param state
         */
        public DFSNode(NodeMap nodeMap, NodePosition state) {
            this.nodeMap = nodeMap;
            // Root Generator
            this.parent = null;
            this.depth = 0;
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
        public DFSNode(
                NodeMap nodeMap,
                NodePosition state,
                DFSNode parent,
                Direction action
        ) {
            this.nodeMap = nodeMap;
            // Root Generator
            this.parent = parent;
            this.depth = parent.depth + 1;
            this.action = action;
            // Received Position
            this.state = state;
        }

        public List<DFSNode> getChildren() {
            List<DFSNode> allowedNodes = new ArrayList<>(4);

            NodePosition upPosition = NodePosition
                    .calculateFromPositionWithDirection(this.state, Direction.UP);
            if (nodeMap.canMoveToPosition(upPosition))
                allowedNodes.add(new DFSNode(nodeMap, upPosition, this, Direction.UP));

            NodePosition downPosition = NodePosition
                    .calculateFromPositionWithDirection(this.state, Direction.DOWN);
            if (nodeMap.canMoveToPosition(downPosition))
                allowedNodes.add(new DFSNode(nodeMap, downPosition, this, Direction.DOWN));

            NodePosition rightPosition = NodePosition
                    .calculateFromPositionWithDirection(this.state, Direction.RIGHT);
            if (nodeMap.canMoveToPosition(rightPosition))
                allowedNodes.add(new DFSNode(nodeMap, rightPosition, this, Direction.RIGHT));


            NodePosition leftPosition = NodePosition
                    .calculateFromPositionWithDirection(this.state, Direction.LEFT);
            if (nodeMap.canMoveToPosition(leftPosition))
                allowedNodes.add(new DFSNode(nodeMap, leftPosition, this, Direction.LEFT));

            return allowedNodes;
        }

        public Direction getPathDirection() {
            List<DFSNode> pathNodes = new ArrayList<>();
            DFSNode current = this;
            while (current.parent != null) {
                pathNodes.add(current);
                current = current.parent;
            }
            return pathNodes.get(pathNodes.size() - 1).getAction();
        }
    }

}
