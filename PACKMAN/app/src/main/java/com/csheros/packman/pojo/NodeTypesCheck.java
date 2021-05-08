package com.csheros.packman.pojo;

import com.csheros.packman.engine.Creature;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class NodeTypesCheck {

    private Creature packMan;
    private List<Creature> evilCreatures;
    private List<Creature> pointCreatures;
    private List<Creature> masterPointCreatures;

    public NodeTypesCheck() {
        this.evilCreatures = new ArrayList<>();
        this.pointCreatures = new ArrayList<>();
        this.masterPointCreatures = new ArrayList<>();
    }

    public void addEvilCreature(Creature creature) {
        this.evilCreatures.add(creature);
    }

    public void addPointCreature(Creature creature) {
        this.pointCreatures.add(creature);
    }

    public void addMasterPointCreature(Creature creature) {
        this.masterPointCreatures.add(creature);
    }

    public boolean hasBackMan() {
        return packMan != null;
    }

    public boolean hasEvilCreature() {
        return !evilCreatures.isEmpty();
    }

    public boolean hasMasterPoint() {
        return !masterPointCreatures.isEmpty();
    }

    public boolean hasPoint() {
        return !pointCreatures.isEmpty();
    }

    public boolean hasEvilCreatureCanEatPackMan() {
        for (Creature evilCreature : getEvilCreatures()) {
            if (!evilCreature.isReversed())
                return true;
        }
        return false;
    }
}
