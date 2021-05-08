package com.csheros.packman.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.pojo.GameState;
import com.csheros.packman.repo.EngineRepo;
import com.csheros.packman.repo.MapRepo;

import org.jetbrains.annotations.NotNull;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> currentLevelLiveData;
    private final MapRepo mapRepo;
    private final EngineRepo engineRepo;

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
        mapRepo = MapRepo.getInstance(application);
        engineRepo = EngineRepo.getInstance(application);

        currentLevelLiveData = new MutableLiveData<>(1);
    }


    // ----- ----- Game State Generator ----- ----- //
    public void startGame(NodeMap nodeMap, int frameRate, int masterPointScore, int masterPointValidTime, int evilCreatureScore) {
        engineRepo.startGame(nodeMap, frameRate, masterPointScore, masterPointValidTime, evilCreatureScore);
    }

    public void stopGame() {
        engineRepo.stopGame();
    }

    public LiveData<GameState> getGameStateLiveData() {
        return engineRepo.getGameStateLiveData();
    }

    // ----- ----- Node Map Generation ----- ----- //
    public void createNodeMap(int level) {
        mapRepo.createNodeMap(level);
    }

    public LiveData<NodeMap> getNodeMapLiveData() {
        return mapRepo.getNodeMapLiveData();
    }

    // ----- -----Level Data ----- ----- //
    public LiveData<Integer> getCurrentLevelLiveData() {
        return currentLevelLiveData;
    }
}