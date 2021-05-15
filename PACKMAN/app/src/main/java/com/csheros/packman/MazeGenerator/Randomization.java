<<<<<<< HEAD
package com.csheros.packman.MazeGenerator ;
=======
package com.company ;
import com.csheros.packman.MazeGenerator.DSU;
>>>>>>> 11e798e8e05ccbcb895203475f26bb565cd09b3b

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


    private int xx[]={0 ,0 , -2 ,2 , 0 };
    private int yy[]={2 ,-2 , 0 ,0 ,  0};
    private int xx_2[]={0 ,0 , -1 ,1 , 0 };
    private int yy_2[]={1 ,-1 , 0 ,0 ,  0};


    private class Node {
        public int x , y ;
        public Node(int x ,int y ){
            this.x = x ;
            this.y = y ;
        }
        public boolean equals(Node other){
            return other.x ==x && other.y ==y ;
        }
    }
    private class Edge {
        private int u , v ;
        private int mid ;
        public Edge(int u ,int v ,int mid){
            this.u = u ;
            this.v = v ;
            this.mid = mid;
        }

    }

    public Randomization(int n ){
        this.n =n ;
    }

    private Node GetNodeFromID (int x ){
        return new Node(x/n , x%n);
    }
    private  int GetIDFromNode(Node s){
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
            if (!dsu.isSame(currentEdge.u , currentEdge.v ) && result[GetNodeFromID(currentEdge.mid).x][GetNodeFromID(currentEdge.mid).y]=='.'){

                result[GetNodeFromID(currentEdge.u).x][GetNodeFromID(currentEdge.u).y]='*';
                result[GetNodeFromID(currentEdge.mid).x][GetNodeFromID(currentEdge.mid).y]='*';
                result[GetNodeFromID(currentEdge.v).x][GetNodeFromID(currentEdge.v).y]='*';
                dsu.merge(currentEdge.u , currentEdge.v);
//                for(int i =0 ;i  <n ;i+=1){
//                    for(int j =0 ;j<n ;j+=1){
//                        System.out.print(result[i][j]);
//                    }
//                    System.out.println();
//                }

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
                Node new_u = new Node(u.x+xx_2[i] , u.y+yy_2[i] );
                Node new_v = new Node(v.x+xx_2[j] , v.y+yy_2[j] );
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
                    if(x >=0 && x < n && y >=0 && y < n && !used[GetIDFromNode(new Node(i ,j ))][GetIDFromNode(new Node(x , y ))]){
                        used[GetIDFromNode(new Node(i ,j ))][GetIDFromNode(new Node(x , y ))]=true ;
                        used[GetIDFromNode(new Node(x , y ))][GetIDFromNode(new Node(i ,j ))]=true ;
                        int mid_x = i+xx_2[k] ,mid_y = j+yy_2[k];

                        result.add(new Edge(GetIDFromNode(new Node(i, j )) ,GetIDFromNode(new Node(x , y ))  , GetIDFromNode(new Node(mid_x ,mid_y))));
                    }
                }
            }
        }
        return result;
    }

}