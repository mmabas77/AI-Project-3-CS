package com.csheros.packman.engine;

import com.csheros.packman.pojo.GameState;
import com.csheros.packman.pojo.NodeTypesCheck;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * Next Game State
     */
    private GameState nextState;

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
        this.masterPointFrameCounter = 1;
    }

    public GameState nextStateTransaction() {
        this.nextState = new GameState(this);
        List<Node> allNodes = nodeMap.getAllNodes();
        List<Creature> allMovableCreatures = getAllMovableCreatures(allNodes);
        createAndEvaluateFrame(allNodes, allMovableCreatures);
        evaluateMasterPointValidTime(allMovableCreatures);
        evaluateGameFinished(nodeMap.getAllNodes());
        return this.nextState;
    }

    private void createAndEvaluateFrame(List<Node> allNodes, List<Creature> allMovableCreatures) {
        Creature packMan = getPackMan(allMovableCreatures);
        if (packMan != null) {
            allMovableCreatures.remove(packMan);
            createNextFrame(Collections.singletonList(packMan));
            evaluateCollisions(allNodes, allMovableCreatures);
        }
        createNextFrame(allMovableCreatures);
        allMovableCreatures.add(packMan);
        evaluateCollisions(allNodes, allMovableCreatures);
    }

    private Creature getPackMan(List<Creature> allMovableCreatures) {
        for (Creature creature : allMovableCreatures) {
            if (creature.getType() == Creature.Type.PACK_MAN)
                return creature;
        }
        return null;
    }

    private void evaluateGameFinished(List<Node> allNodes) {
        for (Node node : allNodes) {
            if (node.hasPoints())
                return;
        }
        nextState.setGameFinished(true);
    }

    private void evaluateMasterPointValidTime(List<Creature> allMovableCreatures) {
        if (!reversed)
            return;
        int passedSec = (masterPointFrameCounter + 1) / frameRate;
        if (passedSec >= masterPointValidTime) {
            unReverseCreatures(allMovableCreatures);
        }
        masterPointFrameCounter = (masterPointFrameCounter + 1) %
                (masterPointValidTime * frameRate);
    }

    private void reverseCreatures(List<Creature> allMovableCreatures) {
        reversed = true;
        for (Creature creature : allMovableCreatures) {
            if (creature != null)
                creature.setReversed(true);
        }
        this.nextState.setReverseCreatures(true);
    }

    private void unReverseCreatures(List<Creature> allMovableCreatures) {
        reversed = false;
        for (Creature creature : allMovableCreatures) {
            if (creature != null)
                creature.setReversed(false);
        }
        this.nextState.setUnReverseCreatures(true);
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
        this.nextState.setAtePoint(true);
    }

    private void cachedMasterPoint(Node node, List<Creature> allMovableCreatures, NodeTypesCheck check) {
        killCreatures(node, check.getMasterPointCreatures(), masterPointScore);
        reverseCreatures(allMovableCreatures);
        this.nextState.setAteMasterPoint(true);
    }

    private void packManCaught(Node node, NodeTypesCheck check) {
        if (check.hasEvilCreatureCanEatPackMan()) {
            packManDies(node, check);
        } else {
            killCreatures(node, check.getEvilCreatures(), evilCreatureScore);
            for (Creature evilCreature : check.getEvilCreatures()) {
                evilCreature.reset();
            }
            this.nextState.setAteEvilCreature(true);
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
        this.nextState.setPackManDied(true);
    }
}
