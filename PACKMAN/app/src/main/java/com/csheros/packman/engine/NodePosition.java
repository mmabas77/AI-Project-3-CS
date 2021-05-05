package com.csheros.packman.engine;

import com.csheros.packman.utils.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NodePosition {
    private int row;
    private int col;

    public static NodePosition calculateFromPositionWithDirection(
            NodePosition oldPosition, Direction direction
    ) {
        int oldRow = oldPosition.getRow();
        int oldCol = oldPosition.getCol();
        switch (direction) {
            case UP:
                oldRow--;
                break;
            case DOWN:
                oldRow++;
                break;
            case LEFT:
                oldCol--;
                break;
            case RIGHT:
                oldCol++;
                break;
        }
        return new NodePosition(oldRow, oldCol);
    }
}
