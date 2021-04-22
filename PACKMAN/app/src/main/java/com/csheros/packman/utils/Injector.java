package com.csheros.packman.utils;

import com.csheros.packman.ai.NextMoveCalculator;

public interface Injector {
    static NextMoveCalculator getNextMoveCalculator(){
        return null;
    }
}
