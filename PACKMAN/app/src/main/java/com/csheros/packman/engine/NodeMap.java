package com.csheros.packman.engine;

import com.csheros.packman.utils.Direction;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class NodeMap {

    // Todo : Create evaluator & frame calculator for the map then make the model

    @Getter
    @Setter
    private NodePosition packManLastPosition;
    @Getter
    @Setter
    private Direction packManDirection;

    @Getter
    private List<Node[]> nodesMap;

    public NodeMap(
            MapSize mapSize,
            NodePosition packManPosition,
            NodePosition[] evilCreaturesPositions,
            NodePosition[] blocksPositions,
            NodePosition[] masterPointPositions
    ) {
        this.nodesMap = new ArrayList<>();
        this.packManLastPosition = packManPosition;
        this.packManDirection = Direction.STAND_STILL;

        constructMap(mapSize);
        fillWithNodes(nodesMap);
        setPackManPosition(packManPosition);
        setEvilCreaturesPositions(evilCreaturesPositions);
        setBlocksPositions(blocksPositions);
        setMasterPointPositions(masterPointPositions);
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
                row[j] = new Node(new NodePosition(i, j), this);
            }
        }
    }

    private void setPackManPosition(NodePosition packManPosition) {
        Node packManNode = getNodeByPosition(packManPosition);
        packManNode.setType(Node.TYPE.PACK_MAN);
    }

    private void setEvilCreaturesPositions(NodePosition[] evilCreaturesPositions) {
        for (NodePosition evilCreaturePosition :
                evilCreaturesPositions) {
            Node evilCreatureNode = getNodeByPosition(evilCreaturePosition);
            evilCreatureNode.setType(Node.TYPE.EVIL_CREATURE);
        }
    }

    private void setBlocksPositions(NodePosition[] blocksPositions) {
        for (NodePosition blockPosition :
                blocksPositions) {
            Node blockNode = getNodeByPosition(blockPosition);
            blockNode.setType(Node.TYPE.BLOCK);
        }
    }

    private void setMasterPointPositions(NodePosition[] masterPointPositions) {
        for (NodePosition masterPointPosition :
                masterPointPositions) {
            Node masterPointNode = getNodeByPosition(masterPointPosition);
            masterPointNode.setType(Node.TYPE.MASTER_POINT);
        }
    }

    private Node getNodeByPosition(NodePosition packManPosition) {
        return this.nodesMap
                .get(packManPosition.getRow())[packManPosition.getCol()];
    }
}
