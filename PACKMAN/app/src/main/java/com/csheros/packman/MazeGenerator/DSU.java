package com.csheros.packman.MazeGenerator;

import com.csheros.packman.ai.AlgoNode;

import java.util.Random;

public class DSU {
    private int n ;
    private int parent[];
    private int size [];
    private Random rand ;

    public DSU(int n ){
        this.n =n ;
        parent = new int[n];
        size = new int[n];
        rand =new Random();
        for(int i =0 ;i < n ;i++)
            parent[i] = i ;
        for(int i =0 ;i  <n ;i++)
            size[i] = 1;

    }

    public int parentOf(int node){
        return parent[node] = (parent[node]!= node) ?  parentOf(parent[node]) : node;
    }
    public boolean isSame(int u , int v ){
        return parentOf(u) == parentOf(v);
    }
    public void merge(int u ,int v ){
        u =parentOf(u) ;
        v =parentOf(v) ;
        if (isSame(u,v))
            return;
        //Not the best complexity of merging but randomization make it well :)
        int toss = rand.nextInt(2);
        if(toss==1){
            parent[u] =parent[v];
            size[v]+=size[u];
        }
        else{
            parent[v] =parent[u];
            size[u]+=size[v];
        }

    }
    public void rooted(){
        for(int i =0 ;i  <n ;i++)
            parentOf(i);
    }
    public int[] getParent() {
        return parent;
    }

    public int[] getSize() {
        return size;
    }
}
