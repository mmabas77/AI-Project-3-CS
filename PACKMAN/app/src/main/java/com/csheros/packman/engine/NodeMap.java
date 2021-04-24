package com.csheros.packman.engine;

import com.csheros.packman.utils.Direction;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class NodeMap {

    // Todo : Create evaluator & frame calculator for the map then make the model

    /**
     * Used to calculate evil creatures path
     */
    private NodePosition packManNextPosition;
    private Direction packManDirection;
    private final List<Node[]> nodesMap;

    public NodeMap(
            MapSize mapSize,
            NodePosition packManPosition,
            NodePosition[] evilCreaturesPositions,
            NodePosition[] blocksPositions,
            NodePosition[] masterPointPositions
    ) {
        this.nodesMap = new ArrayList<>();
        this.packManDirection = Direction.STAND_STILL;

        constructMap(mapSize);
        fillWithNodes(nodesMap);
        setPackManPosition(packManPosition);
        setEvilCreaturesPositions(evilCreaturesPositions);
        setBlocksPositions(blocksPositions);
        setMasterPointPositions(masterPointPositions);
    }


    public boolean canMoveToPosition(Creature creature , NodePosition nodePosition){
        // Todo : Implement this!
        return true;
    }

    private void constructMap(MapSize mapSize) {
        for (int i = 0; i < mapSize.getHeight(); i++) {
            nodesMap.add(new Node[mapSize.getWidth()]);
        }
    }

    private void fillWithNodes(List<Node[]> nodesMap) {
        for (int i = 0; i < nodesMap.size(); i++) {
            Node[] row = nodesMap.get(i);
            for (int j = 0; j < row.length; j++) {
                row[j] = new Node(this, new NodePosition(i, j));
                row[j].replaceCreatures(new Creature(Creature.Type.POINT));
            }
        }
    }

    private void setPackManPosition(NodePosition packManPosition) {
        Node packManNode = getNodeByPosition(packManPosition);
        packManNode.replaceCreatures(new Creature(Creature.Type.PACK_MAN));
    }

    private void setEvilCreaturesPositions(NodePosition[] evilCreaturesPositions) {
        for (NodePosition evilCreaturePosition :
                evilCreaturesPositions) {
            Node evilCreatureNode = getNodeByPosition(evilCreaturePosition);
            evilCreatureNode.replaceCreatures(new Creature(Creature.Type.EVIL_CREATURE));
        }
    }

    private void setBlocksPositions(NodePosition[] blocksPositions) {
        for (NodePosition blockPosition :
                blocksPositions) {
            Node blockNode = getNodeByPosition(blockPosition);
            blockNode.replaceCreatures(new Creature(Creature.Type.BLOCK));
        }
    }

    private void setMasterPointPositions(NodePosition[] masterPointPositions) {
        for (NodePosition masterPointPosition :
                masterPointPositions) {
            Node masterPointNode = getNodeByPosition(masterPointPosition);
            masterPointNode.replaceCreatures(new Creature(Creature.Type.MASTER_POINT));
        }
    }

    private Node getNodeByPosition(NodePosition packManPosition) {
        return this.nodesMap
                .get(packManPosition.getRow())[packManPosition.getCol()];
    }
}
