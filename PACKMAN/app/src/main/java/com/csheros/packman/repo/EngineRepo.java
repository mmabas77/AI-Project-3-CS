package com.csheros.packman.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.model.EngineModel;
import com.csheros.packman.pojo.GameState;

public class EngineRepo {

    /**
     * Singleton Architecture
     */
    private static EngineRepo instance;

    public synchronized static EngineRepo getInstance(Application application) {
        if (instance == null)
            instance = new EngineRepo(application);
        return instance;
    }

    /**
     * Constructor
     */
    private final Application application;
    private final EngineModel engineModel;

    private EngineRepo(Application application) {
        this.application = application;
        this.engineModel = EngineModel.getInstance(application);
    }

    /**
     * Main Logic
     */

    public void startGame(NodeMap nodeMap, int frameRate, int masterPointScore, int masterPointValidTime, int evilCreatureScore) {
        engineModel.startGame(nodeMap, frameRate, masterPointScore, masterPointValidTime, evilCreatureScore);
    }

    public void stopGame() {
        engineModel.stopGame();
    }

    /**
     * Live Data Getters
     */
    public LiveData<GameState> getGameStateLiveData() {
        return engineModel.getGameStateMutableLiveData();
    }
}
