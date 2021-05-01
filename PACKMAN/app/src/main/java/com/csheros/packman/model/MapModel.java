package com.csheros.packman.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.utils.NodeMapGenerator;

import lombok.Getter;

public class MapModel {

    /**
     * Singleton Architecture
     */

    private static MapModel instance;

    public static synchronized MapModel getInstance(Application application) {
        if (instance == null)
            instance = new MapModel(application);
        return instance;
    }

    /**
     * Constructor
     *
     * @param application
     */

    private Application application;

    private MapModel(Application application) {
        this.application = application;
    }

    /**
     * Live Data
     */
    @Getter
    private MutableLiveData<NodeMap> nodeMapLiveData;

    /**
     * Main Logic
     */
    public void createNodeMap(int level) {
        nodeMapLiveData.postValue(NodeMapGenerator.createDynamicNodeMap(level));
    }

}
