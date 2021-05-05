package com.csheros.packman.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.csheros.packman.engine.Engine;
import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.pojo.GameState;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EngineModel {

    /**
     * Singleton Architecture
     */

    private static EngineModel instance;


    public synchronized static EngineModel getInstance(Application application) {
        if (instance == null)
            instance = new EngineModel(application);
        return instance;
    }

    /**
     * Constructor
     */

    private final Application application;

    private EngineModel(Application application) {
        this.application = application;
        gameStateMutableLiveData = new MutableLiveData<>();
    }

    /**
     * Live Data
     */

    @Getter
    private final MutableLiveData<GameState> gameStateMutableLiveData;

    /**
     * Start game
     */
    private Worker worker;

    public void startGame(NodeMap nodeMap, int frameRate, int masterPointScore, int masterPointValidTime, int evilCreatureScore) {
        stopGame();
        Engine engine = new Engine(nodeMap, frameRate, masterPointScore, masterPointValidTime, evilCreatureScore);
        worker = new Worker(engine, gameStateMutableLiveData);
        worker.start();
    }

    public void stopGame() {
        if (worker != null)
            worker.interrupt();
    }

    @AllArgsConstructor
    private static class Worker extends Thread {
        private final Engine engine;
        private final MutableLiveData<GameState> gameStateMutableLiveData;

        @Override
        public void run() {
            int frameRate = engine.getFrameRate();
            long sleepTime = 1000 / frameRate;
            while (!isInterrupted()) {
                GameState gameState = engine.nextStateTransaction();
                gameStateMutableLiveData.postValue(gameState);
                try {
                    sleep(sleepTime);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }
    }
}
