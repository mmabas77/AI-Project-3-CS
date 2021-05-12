package com.csheros.packman.MazeGenerator;

import com.csheros.packman.ai.AlgoNode;

import java.util.Random;

public class DSU {
    private int parent[];
    private int size [];
    private Random rand ;
    public void DSU(int n ){
        for(int i =0 ;i < n ;i++)
            parent[i] = i ;
        for(int i =0 ;i  <n ;i++)
            size[i]=1;
        rand =new Random();
    }

    public int parentOf(int node){
        return parent[node]!= node ? parent[node] = parentOf(parent[node]) : node;
    }
    public boolean isSame(int u , int v ){
        return parentOf(u) ==parentOf(v);
    }
    public void merge(int u ,int v ){
        if (isSame(u,v))
            return;
        //I don't best complexity of merging but randomization make it well
        int toss = rand.nextInt(1);
        if(toss==1){
            parent[u] =parent[v];
            size[v]+=size[u];
        }
        else{
            parent[v] =parent[u];
            size[u]+=size[v];
        }

    }
}
