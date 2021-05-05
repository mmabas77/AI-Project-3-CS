package com.csheros.packman.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.model.MapModel;

public class MapRepo {

    /**
     * Singleton Architecture
     */

    private static MapRepo instance;

    public static synchronized MapRepo getInstance(Application application) {
        if (instance == null)
            instance = new MapRepo(application);
        return instance;
    }

    /**
     * Constructor
     *
     * @param application
     */
    private final Application application;
    private final MapModel mapModel;

    private MapRepo(Application application) {
        this.application = application;
        mapModel = MapModel.getInstance(application);
    }

    /**
     * Main Logic
     */
    public void createNodeMap(int level) {
        mapModel.createNodeMap(level);
    }

    /**
     * Getters
     */
    public LiveData<NodeMap> getNodeMapLiveData() {
        return mapModel.getNodeMapLiveData();
    }
}
