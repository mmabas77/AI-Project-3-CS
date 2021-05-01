package com.csheros.packman.engine;

import lombok.Data;

@Data
public class MapSize {
    private int width;
    private int height;

    public boolean insideMap(int col, int row) {
        return col < width && row < height;
    }
}
