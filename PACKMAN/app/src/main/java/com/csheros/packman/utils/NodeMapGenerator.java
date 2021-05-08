package com.csheros.packman.utils;

import com.csheros.packman.engine.MapSize;
import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.engine.NodePosition;

public interface NodeMapGenerator {

    static NodeMap createStaticNodeMap() {
        return new NodeMap(
                new MapSize(7, 7),
                new NodePosition(1, 4),
                new NodePosition[]{new NodePosition(1, 1)},
                new NodePosition[]{new NodePosition(1, 2)},
                new NodePosition[]{new NodePosition(1, 3)}
        );
    }

    static NodeMap createDynamicNodeMap(int level) {
        // Todo : Implement this
        return createStaticNodeMap();
    }

}
