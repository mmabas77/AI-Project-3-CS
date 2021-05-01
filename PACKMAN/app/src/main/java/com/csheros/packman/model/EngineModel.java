package com.csheros.packman.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.csheros.packman.engine.Engine;
import com.csheros.packman.engine.NodeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EngineModel {

    /**
     * Singleton Architecture
     */

    private static EngineModel instance;


    public static EngineModel getInstance(Application application) {
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
    }

    /**
     * Live Data
     */
    @Getter
    private MutableLiveData<Engine> engineLiveData;

    /**
     * Start game
     *
     * @param nodeMap
     * @param frameRate
     * @param masterPointScore
     * @param masterPointValidTime
     * @param evilCreatureScore
     */
    private Worker worker;

    public void startGame(NodeMap nodeMap, int frameRate, int masterPointScore, int masterPointValidTime, int evilCreatureScore) {
        stopGame();
        Engine engine = new Engine(nodeMap, frameRate, masterPointScore, masterPointValidTime, evilCreatureScore);
        worker = new Worker(engine, engineLiveData);
        worker.start();
    }

    public void stopGame() {
        if (worker != null)
            worker.interrupt();
    }

    @AllArgsConstructor
    private static class Worker extends Thread {
        private final Engine engine;
        private final MutableLiveData<Engine> engineLiveData;

        @Override
        public void run() {
            while (!isInterrupted()) {
                engine.nextStateTransaction();
                engineLiveData.postValue(engine);
                int frameRate = engine.getFrameRate();
                try {
                    sleep(1000 / frameRate);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }
    }
}
