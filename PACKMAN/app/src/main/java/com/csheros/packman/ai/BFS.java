package com.csheros.packman.ai;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.engine.NodePosition;
import com.csheros.packman.utils.Direction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import lombok.Data;

public class BFS implements NextMoveCalculator {

    @Override
    public Direction getNextMoveDirection(NodePosition srcNodePosition, NodePosition targetNodePosition, NodeMap nodeMap) {

        // Node Holders
        LinkedList<DFSNode> DFS_Queue = new LinkedList<>();
        List<DFSNode> visitedNodes = new ArrayList<>();
        // Add root to stack & visited
        DFSNode root = new DFSNode(nodeMap, srcNodePosition);
        DFS_Queue.add(root);
        visitedNodes.add(root);
        // Loop on stack to get direction
        while (!DFS_Queue.isEmpty()) {
            // Get current node
            DFSNode currentNode = DFS_Queue.removeFirst();
            // Check goal
            if (currentNode.getState().equals(targetNodePosition)) {
                return currentNode.getPathDirection();
            }
            // Add children to stack if not visited
            List<DFSNode> children = currentNode.getChildren();
            for (DFSNode child : children) {
                if (!visitedNodes.contains(child)) {
                    DFS_Queue.addLast(child);
                    // Add to visited
                    visitedNodes.add(child);
                }
            }
        }
        return Direction.STAND_STILL;
    }

    @Data
    static class DFSNode {
        // Extra
        private NodeMap nodeMap;
        // Path Data
        private DFSNode parent;
        // Self Data
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
            Stack<DFSNode> pathNodes = new Stack<>();
            DFSNode current = this;
            while (current.parent != null) {
                pathNodes.push(current);
                current = current.parent;
            }
            if (pathNodes.size() > 0) {
                System.out.println("Path ...");
                for (DFSNode pathNode : pathNodes) {
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

            DFSNode node = (DFSNode) o;
            return node.getState().equals(this.getState());
        }
    }

}
