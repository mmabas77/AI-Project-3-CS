package com.csheros.packman.MazeGenerator.Util;

import java.util.ArrayList;

public class Node {
    public int x;
    public int y;
    public Node prev ;
    int xx2[]={2 , -2 , 0 ,0 };
    int yy2[]={0 , 0 , 2 ,-2 };
    int xx[]={1 , -1 , 0 ,0 };
    int yy[]={0 , 0 , 1 ,-1 };
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.prev=null;
    }
    public Node (int x ,int y,Node prev ){
        this(x , y );
        this.prev = prev;
    }

    public boolean equals(Node other) {
        return other.x == this.x && other.y == this.y;
    }


    public ArrayList<Node> childs(){
        ArrayList<Node> res = new ArrayList<Node>();
        for(int i =0 ;i < 4 ;i++){
            int new_x = x+xx2[i];
            int new_y = y+yy2[i];

            int mid_x = x+xx[i];
            int mid_y = y+yy[i];
            res.add(new Node(new_x , new_y , new Node(mid_x, mid_y)));
        }
        return res;
    }

    public static Node GetNodeFromID(int ID ,int mazeHeight , int mazeWeight) {
        return new Node(ID / mazeWeight, ID % mazeWeight);
    }

    public static int GetIDFromNode(Node node , int mazeHeight , int mazeWeight ) {
        return mazeWeight * node.x + node.y;
    }
    public boolean check(int n , int m ){
        return x >= 0 && x < n && y >=0 && y< m ;
    }
    public static boolean check(Node node , int mazeHeight , int mazeWeight) {
        return node.x >= 0 && node.y >= 0 && node.x < mazeHeight && node.y < mazeWeight;
    }

    public static boolean check(int x, int y , int mazeHeight , int mazeWeight) {
        return x >= 0 && x < mazeHeight && y >= 0 && y < mazeWeight;
    }

}
