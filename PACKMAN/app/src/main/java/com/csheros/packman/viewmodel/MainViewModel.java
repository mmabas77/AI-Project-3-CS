package com.csheros.packman.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.repo.MapRepo;

import org.jetbrains.annotations.NotNull;

public class MainViewModel extends AndroidViewModel {

    private final MapRepo mapRepo;

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
        mapRepo = MapRepo.getInstance(application);
    }

    public void createNodeMap(int level) {
        mapRepo.createNodeMap(level);
    }

    public LiveData<NodeMap> getNodeMapLiveData() {
        return mapRepo.getNodeMapLiveData();
    }

}