package com.csheros.packman.engine;

import com.csheros.packman.pojo.NodeTypesCheck;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Engine {

    /**
     * Will create engine for every new game - Main Data
     */
    private final NodeMap nodeMap;
    private final int frameRate;
    private final int masterPointScore;
    private final int masterPointValidTime;
    private final int evilCreatureScore;

    /**
     * Game Statistics
     */

    private boolean alive;
    private int score;
    private boolean reversed;

    /**
     * For using with master points timer
     */
    private int masterPointFrameCounter;

    public Engine(NodeMap nodeMap, int frameRate, int masterPointScore, int masterPointValidTime, int evilCreatureScore) {
        // From model
        this.nodeMap = nodeMap;
        this.frameRate = frameRate;
        this.masterPointScore = masterPointScore;
        this.masterPointValidTime = masterPointValidTime;
        this.evilCreatureScore = evilCreatureScore;
        // Default values
        this.alive = true;
        this.score = 0;
        this.reversed = false;
        this.masterPointFrameCounter = 0;
    }

    public void nextStateTransaction() {
        List<Node> allNodes = nodeMap.getAllNodes();
        List<Creature> allMovableCreatures = getAllMovableCreatures(allNodes);
        createNextFrame(allMovableCreatures);
        evaluateCollisions(allNodes, allMovableCreatures);
        evaluateMasterPointValidTime(allMovableCreatures);
    }

    private void evaluateMasterPointValidTime(List<Creature> allMovableCreatures) {
        if (!reversed)
            return;

        int passedSec = masterPointFrameCounter / frameRate;
        if (passedSec > masterPointValidTime) {
            unReverseCreatures(allMovableCreatures);
        }
    }

    private void reverseCreatures(List<Creature> allMovableCreatures) {
        for (Creature creature : allMovableCreatures) {
            if (creature != null)
                creature.setReversed(true);
        }
    }

    private void unReverseCreatures(List<Creature> allMovableCreatures) {
        for (Creature creature : allMovableCreatures) {
            creature.setReversed(false);
        }
    }

    private List<Creature> getAllMovableCreatures(List<Node> allNodes) {
        List<Creature> movableCreatures = new ArrayList<>();
        for (Node node : allNodes) {
            movableCreatures.addAll(node.getMovableCreatures());
        }
        return movableCreatures;
    }

    private void createNextFrame(List<Creature> movableCreatures) {
        for (Creature movableCreature : movableCreatures) {
            NodePosition nextPosition = movableCreature.getNextPosition();
            nodeMap.moveToPositionIfPossible(movableCreature, nextPosition);
        }
    }

    private void evaluateCollisions(List<Node> nodes, List<Creature> allMovableCreatures) {
        for (Node node : nodes) {
            if (node.hasCollision()) {
                evaluateCollision(node, allMovableCreatures);
            }
        }
    }

    private void evaluateCollision(Node node, List<Creature> allMovableCreatures) {
        NodeTypesCheck check = node.checkTypes();
        if (check.hasBackMan() && check.hasEvilCreature())
            packManCaught(node, check);
        if (check.hasBackMan() && check.hasMasterPoint())
            cachedMasterPoint(node, allMovableCreatures, check);
        if (check.hasBackMan() && check.hasPoint())
            cachedPoint(node, check);
    }

    private void cachedPoint(Node node, NodeTypesCheck check) {
        killCreatures(node, check.getPointCreatures(), 1);
    }

    private void cachedMasterPoint(Node node, List<Creature> allMovableCreatures, NodeTypesCheck check) {
        killCreatures(node, check.getMasterPointCreatures(), masterPointScore);
        reverseCreatures(allMovableCreatures);
    }

    private void packManCaught(Node node, NodeTypesCheck check) {
        if (!reversed) {
            packManDies(node, check);
        } else {
            killCreatures(node, check.getEvilCreatures(), evilCreatureScore);
        }
    }


    private void killCreatures(Node node, List<Creature> creatures, int points) {
        for (Creature creature : creatures) {
            score += points;
            node.removeCreature(creature);
        }
    }

    private void packManDies(Node node, NodeTypesCheck check) {
        Creature packMan = check.getPackMan();
        node.removeCreature(packMan);
        alive = false;
    }
}
