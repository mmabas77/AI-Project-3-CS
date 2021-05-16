package com.csheros.packman.engine;

import com.csheros.packman.utils.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class NodeMap {

    /**
     * Used to calculate evil creatures path
     */
    private NodePosition packManPosition;
    private Direction packManDirection;
    private final List<Node[]> nodesMap;
    private MapSize mapSize;


    public NodeMap(List<Node[]> nodesMap) {
        this.nodesMap = nodesMap;
        this.packManDirection = Direction.STAND_STILL;
    }


    public List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<>();
        for (Node[] nodesArr : getNodesMap()) {
            nodes.addAll(Arrays.asList(nodesArr));
        }
        return nodes;
    }

    public boolean canMoveToPosition(NodePosition nodePosition) {
        boolean allowedInSize = mapSize
                .insideMap(nodePosition.getCol(), nodePosition.getRow());
        if (!allowedInSize)
            return false;
        boolean hasBlock = getNodeByPosition(nodePosition).hasBlock();

        return !hasBlock;
    }

    public void moveToPositionIfPossible(Creature creature, NodePosition nodePosition) {
        if (!canMoveToPosition(nodePosition))
            return;
        creature.getNode().removeCreature(creature);
        getNodeByPosition(nodePosition).addCreature(creature);
        if (creature.getType() == Creature.Type.PACK_MAN)
            this.packManPosition = nodePosition;
    }


    private Node getNodeByPosition(NodePosition packManPosition) {
        return this.nodesMap
                .get(packManPosition.getRow())[packManPosition.getCol()];
    }

    public NodePosition getPackManPosition() {
        if (this.packManPosition == null)
            for (Node node : getAllNodes()) {
                if (node.checkTypes().hasBackMan())
                    this.packManPosition = node.getPosition();
            }
        return packManPosition;
    }

    private int evilCreaturesCount = -1;

    public synchronized int getEvilCreaturesCount() {
        if (evilCreaturesCount == -1) {
            int count = 0;
            for (Node node : getAllNodes()) {
                for (Creature creature : node.getCreatures()) {
                    if (creature.getType() == Creature.Type.EVIL_CREATURE)
                        count++;
                }
            }
            evilCreaturesCount = count;
        }
        return evilCreaturesCount;
    }
}
