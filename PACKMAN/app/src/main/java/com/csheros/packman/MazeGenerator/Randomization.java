package com.csheros.packman.MazeGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class Randomization {

    private static int n ;


     class Node {
        public int x , y ;
        public Node(int x ,int y ){
            this.x = x ;
            this.y = y ;
        }

    }
    class Edge {
        public int u , v ;
        public Edge(int u ,int v ){
            this.u = u ;
            this.v =v ;
        }
    }


    int xx[]={0 ,0 , -1 ,1 , 0 };
    int yy[]={1 ,-1 , 0 ,0  ,0};
    Node GetNodeFromID (int x ){
        return new Node(x/n , x%n);
    }
    int GetIDFromNode(Node s){
        return n * s.x + s.y;
    }

    public ArrayList<Edge> GenerteEdges(int n)
    {
        boolean used [][]= new boolean [n*n][n*n];
        ArrayList<Edge>result =new ArrayList<Edge>();
//        Random rand = new Random();
        for(int i =0 ;i < n ;i++)
        {
            for(int j =0 ;j  <n ;j++) {

                for (int k =0 ;k < 4 ;k++){
                    int x = i + xx[k];
                    int y = j + yy[k];
                    if(x >=0 && x <n && y >=0 && y < n && !used[GetIDFromNode(new Node(i ,j ))][GetIDFromNode(new Node(x , y ))]){
                        used[GetIDFromNode(new Node(i ,j ))][GetIDFromNode(new Node(x , y ))]=true ;
                        used[GetIDFromNode(new Node(x , y ))][GetIDFromNode(new Node(i ,j ))]=true ;
                        result.add(new Edge(GetIDFromNode(new Node(i, j )) ,GetIDFromNode(new Node(x , y ))));
                    }
                }
            }
        }
        return result;
    }

}
