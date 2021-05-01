package com.csheros.packman.engine;


import com.csheros.packman.pojo.NodeTypesCheck;

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
        boolean manyCreatures = creatures.size() > 1;
        if (manyCreatures) {
            Creature.Type firstType = creatures.get(0).getType();
            for (Creature creature : creatures) {
                if (creature.getType() != firstType)
                    return true;
            }
        }
        return false;
    }

    public List<Creature> getMovableCreatures() {
        List<Creature> movableCreatures = new ArrayList<>();
        for (Creature creature : getCreatures()) {
            if (creature.getType() == Creature.Type.PACK_MAN ||
                    creature.getType() == Creature.Type.EVIL_CREATURE) {
                movableCreatures.add(creature);
            }
        }
        return movableCreatures;
    }

    private void clearCreatures() {
        for (Creature creature : creatures) {
            creature.setNode(null);
        }
        creatures.clear();
    }

    public void removeCreature(Creature creature) {
        getCreatures().remove(creature);
    }


    public NodeTypesCheck checkTypes() {
        NodeTypesCheck check = new NodeTypesCheck();

        for (Creature creature : creatures) {
            if (creature.getType() == Creature.Type.PACK_MAN) {
                check.setPackMan(creature);
            } else if (creature.getType() == Creature.Type.POINT)
                check.addPointCreature(creature);
            else if (creature.getType() == Creature.Type.MASTER_POINT)
                check.addMasterPointCreature(creature);
            else if (creature.getType() == Creature.Type.EVIL_CREATURE)
                check.addEvilCreature(creature);
        }

        return check;
    }

}
