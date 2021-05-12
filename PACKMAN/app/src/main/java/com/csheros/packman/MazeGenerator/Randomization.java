package com.company ;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class Randomization {

    private int n ;
    public Randomization(int n ){
        this.n =n ;
    }

    class Node {
        public int x , y ;
        public Node(int x ,int y ){
            this.x = x ;
            this.y = y ;
        }
        public boolean equals(Node other){
            return other.x ==x && other.y ==y ;
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
    public char[][]GetMap(){
        char result[][] = new char[n][n];
        for (int i =0 ;i  <n ;i++)
            for(int j= 0 ;j < n ;j++)
                    result[i][j] = '.';
        DSU dsu = new DSU(n*n);

        ArrayList<Edge>edges = GenerteEdges(n);
        for (Edge e : edges)
            System.out.println(e.u + " "+ e.v );
        while(edges.size()>0){
            Collections.shuffle(edges);
            Edge currentEdge = edges.get(edges.size()-1);
            edges.remove(edges.size()-1);
            if (CheckNeighbors(dsu , currentEdge)){
                result[GetNodeFromID(currentEdge.u).x][GetNodeFromID(currentEdge.u).y]='#';
                result[GetNodeFromID(currentEdge.v).x][GetNodeFromID(currentEdge.v).y]='#';
                dsu.merge(currentEdge.u , currentEdge.v);
            }
        }

        return result;
    }
    boolean check(Node node ){
        return(node.x >=0 && node.y>=0 && node.x < n && node.y < n  );
    }
    private boolean CheckNeighbors(DSU dsu, Edge currentEdge) {
        Node u = GetNodeFromID(currentEdge.u);
        Node v = GetNodeFromID(currentEdge.v);
        boolean res =true;
        for(int i =0 ;i  <5 ;i++){
            for(int j = 0 ; j <5 ;j++){
                Node new_u = new Node(u.x+xx[i] , u.y+yy[i] );
                Node new_v = new Node(v.x+xx[j] , v.y+yy[j] );
                if(new_u.equals(new_v) || !check(new_u) || !check(new_v))
                    continue;
                res = res && !dsu.isSame(GetIDFromNode(new_u) , GetIDFromNode(new_v));

            }
        }
        return res;

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
