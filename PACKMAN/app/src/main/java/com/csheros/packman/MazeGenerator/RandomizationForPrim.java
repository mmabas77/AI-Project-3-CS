package com.csheros.packman.MazeGenerator;

import com.csheros.packman.MazeGenerator.Util.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomizationForPrim extends GetMaze {

    Random rand;
    int xx[]={1 , -1 , 0 ,0 };
    int yy[]={0 , 0 , 1 ,-1 };
    int xx2[]={2 , -2 , 0 ,0 };
    int yy2[]={0 , 0 , 2 ,-2 };

    public RandomizationForPrim(int mazeHeight , int mazeWeight ){
        this.mazeHeight=mazeHeight;
        this.mazeWeight=mazeWeight;
        rand = new Random();

    }

    @Override
    public char[][] GetRandomMaze() {
        return generate();
    }


    private char[][]generate(){
        char[][]arr= new char[mazeHeight][mazeWeight];
        for(int i =0 ;i <mazeHeight ;i++)
            for(int j=0 ;j< mazeWeight ;j++)
                arr[i][j]='.';

        Node root =new Node (rand.nextInt(mazeHeight) , rand.nextInt(mazeWeight));

        ArrayList<Integer> set= new ArrayList<Integer>();
        set.add(Node.GetIDFromNode(root , mazeHeight,mazeWeight ) );

        while(!set.isEmpty()){
            Collections.shuffle(set);
            int cur_id =set.get(set.size()-1);
            set.remove(set.size()-1);
            Node cur_node = Node.GetNodeFromID(cur_id ,mazeHeight ,mazeWeight );

            for(Node child : cur_node.childs()){
                if(child.check(mazeHeight ,mazeWeight )){
                    if(arr[child.x][child.y]!='*')
                    {
                        set.add(Node.GetIDFromNode(child , mazeHeight,mazeWeight));
                        if(child.prev != null)
                            arr[child.prev.x][child.prev.y]='*';
                        arr[child.x][child.y]='*';
                    }
                }
            }
        }


        return arr;
    }


    private void print(char[][]arr){
        for(int i =0 ;i  <arr.length ;i++){
            for(int j =0 ;j < arr[0].length ;j+=1){
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

}
