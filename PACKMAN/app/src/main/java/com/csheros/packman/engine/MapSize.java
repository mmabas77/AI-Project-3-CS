package com.csheros.packman.engine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MapSize {
    private int width;
    private int height;

    public boolean insideMap(int col, int row) {
        boolean allowedCol = col >= 0 && col < width;
        boolean allowedRow = row >= 0 && row < height;
        return allowedCol && allowedRow;
    }
}
