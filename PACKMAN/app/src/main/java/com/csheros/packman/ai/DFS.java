package com.csheros.packman.ai;

import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.engine.NodePosition;
import com.csheros.packman.utils.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.Data;

import static java.lang.Math.min;

public class DFS implements NextMoveCalculator{


    private int memoization[][];


    int DFS(AlgoNode node , NodePosition targetNodePosition , int path ) {
        if(node.equals(targetNodePosition))
            return path ;

        int shortestPath = memoization[node.getState().getRow()][node.getState().getCol()];
        if(shortestPath !=-1)
            return shortestPath;
        shortestPath= Integer.MAX_VALUE;
        for(AlgoNode nextNode :node.getChildren()){
            shortestPath = min (shortestPath , DFS(nextNode , targetNodePosition , path +1 ));
        }
        return memoization[node.getState().getRow()][node.getState().getCol()] = shortestPath ;
    }


    @Override
    public Direction getNextMoveDirection(NodePosition srcNodePosition, NodePosition targetNodePosition, NodeMap nodeMap) {
        AlgoNode root = new AlgoNode(nodeMap, srcNodePosition);
        memoization = new int[nodeMap.getMapSize().getHeight()][nodeMap.getMapSize().getWidth()];

        for(int i = 0 ; i < memoization.length ;i++) {
            for(int j = 0 ;j < memoization[0].length ;j++){
                memoization[i][j]=-1;
            }
        }

        int shortestPath = DFS(root , targetNodePosition , 0);

        Direction result = Direction.STAND_STILL ;
        int minResult = Integer.MAX_VALUE;
        for(AlgoNode child : root.getChildren()) {
            if(minResult > memoization[child.getState().getRow()][child.getState().getCol()]){
                minResult = memoization[child.getState().getRow()][child.getState().getCol()];
                result = child.getAction();
            }

        }
        return result;
    }


}
