package com.csheros.packman.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.repo.MapRepo;

import org.jetbrains.annotations.NotNull;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> currentLevelLiveData;
    private final MapRepo mapRepo;

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
        mapRepo = MapRepo.getInstance(application);
        currentLevelLiveData = new MutableLiveData<>(1);
    }

    public void createNodeMap(int level) {
        mapRepo.createNodeMap(level);
    }

    public LiveData<NodeMap> getNodeMapLiveData() {
        return mapRepo.getNodeMapLiveData();
    }

    public LiveData<Integer> getCurrentLevelLiveData() {
        return currentLevelLiveData;
    }
}