package com.csheros.packman.pojo;

import com.csheros.packman.engine.Engine;

import lombok.Data;

@Data
public class GameState {
    private Engine engine;
    private boolean evenFrameCount,
            packManDied,
            atePoint,
            ateMasterPoint,
            ateEvilCreature,
            reverseCreatures,unReverseCreatures;

    public GameState(Engine engine) {
        this.engine = engine;
    }
}
