package com.csheros.packman.pojo;

import com.csheros.packman.engine.Engine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameState {
    private Engine engine;
    private boolean evenFrameCount;
    private boolean packManDied;
    private boolean atePoint;
    private boolean ateMasterPoint;
    private boolean ateEvilCreature;
}
