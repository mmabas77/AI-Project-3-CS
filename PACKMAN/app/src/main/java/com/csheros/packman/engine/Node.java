package com.csheros.packman.engine;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Node {

    private final NodeMap nodeMap;
    private final NodePosition position;
    private final List<Creature> creatures;

    public Node(NodeMap nodeMap, NodePosition position) {
        this.nodeMap = nodeMap;
        this.position = position;
        creatures = new ArrayList<>();
    }

    public void replaceCreatures(Creature creature) {
        clearCreatures();
        addCreature(creature);
    }

    public void addCreature(Creature creature) {
        this.creatures.add(creature);
        creature.setNode(this);
    }

    public boolean hasCollision() {
        return creatures.size() > 1;
    }

    private void clearCreatures() {
        for (Creature creature : creatures) {
            creature.setNode(null);
        }
        creatures.clear();
    }
}
