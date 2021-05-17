
package com.csheros.packman.MazeGenerator ;


import com.csheros.packman.MazeGenerator.Util.DSU;
import com.csheros.packman.MazeGenerator.Util.Edge;
import com.csheros.packman.MazeGenerator.Util.Node;

import java.util.ArrayList;
import java.util.Collections;

public class RandomizationForKruskal extends GetMaze{
    private int[] xx = new int[]{0, 0, -2, 2, 0};
    private int[] yy = new int[]{2, -2, 0, 0, 0};
    private int[] xx_2 = new int[]{0, 0, -1, 1, 0};
    private int[] yy_2 = new int[]{1, -1, 0, 0, 0};

    public RandomizationForKruskal(int mazeHeight , int mazeWeight) {
        this.mazeHeight = mazeHeight;
        this.mazeWeight = mazeWeight;
    }
    @Override
    public char[][] GetRandomMaze() {
        return GetMap() ;
    }

    private char[][] GetMap() {
        char[][] result = new char[mazeHeight][mazeWeight];

        for(int i = 0; i < mazeHeight; ++i) {
            for(int j = 0; j < mazeWeight; ++j) {
                result[i][j] = '.';
            }
        }

        DSU dsu = new DSU(mazeHeight * mazeWeight);
        ArrayList edges = this.GenerteEdges();

        while(edges.size() > 0) {
            Collections.shuffle(edges);
            Edge currentEdge = (Edge)edges.get(edges.size() - 1);
            edges.remove(edges.size() - 1);
            if (!dsu.isSame(currentEdge.u, currentEdge.v) && this.CheckNeighbors(dsu, currentEdge)) {
                result[Node.GetNodeFromID(currentEdge.u , mazeHeight ,mazeWeight).x][Node.GetNodeFromID(currentEdge.u,mazeHeight ,mazeWeight).y] = '*';
                result[Node.GetNodeFromID(currentEdge.mid,mazeHeight ,mazeWeight).x][Node.GetNodeFromID(currentEdge.mid,mazeHeight ,mazeWeight).y] = '*';
                result[Node.GetNodeFromID(currentEdge.v,mazeHeight ,mazeWeight).x][Node.GetNodeFromID(currentEdge.v,mazeHeight ,mazeWeight).y] = '*';
                dsu.merge(currentEdge.u, currentEdge.v);
                dsu.merge(currentEdge.u, currentEdge.mid);
                dsu.merge(currentEdge.v, currentEdge.mid);
                this.ConnectToNeighbors(result, dsu, currentEdge);
            }
        }

        return result;
    }

    void print(char[][] result) {
        for(int i = 0; i < mazeHeight; ++i) {
            for(int j = 0; j < mazeWeight; ++j) {
                System.out.print(result[i][j]);
            }
            System.out.println();
        }
    }


    private boolean CheckNeighbors(DSU dsu, Edge currentEdge) {
        Node u = Node.GetNodeFromID(currentEdge.u,mazeHeight ,mazeWeight);
        Node v = Node.GetNodeFromID(currentEdge.v,mazeHeight ,mazeWeight);
        boolean res = true;

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Node new_u = new Node(u.x + this.xx_2[i], u.y + this.yy_2[i]);
                Node new_v = new Node(v.x + this.xx_2[j], v.y + this.yy_2[j]);
                if (!new_u.equals(new_v) && Node.check(new_u , mazeHeight ,mazeWeight) && Node.check(new_v, mazeHeight ,mazeWeight)) {
                    res = res && !dsu.isSame(Node.GetIDFromNode(new_u , mazeHeight ,mazeWeight), Node.GetIDFromNode(new_v, mazeHeight ,mazeWeight));
                }
            }
        }

        return res;
    }

    private void ConnectToNeighbors(char[][] result, DSU dsu, Edge currentEdge) {
        Node[] nodes = new Node[]{Node.GetNodeFromID(currentEdge.u,mazeHeight ,mazeWeight), Node.GetNodeFromID(currentEdge.v,mazeHeight ,mazeWeight), Node.GetNodeFromID(currentEdge.mid,mazeHeight ,mazeWeight)};

        for(int i = 0; i < 3; ++i) {
            for(int k = 0; k < 4; ++k) {
                int new_x = nodes[i].x + this.xx_2[k];
                int new_y = nodes[i].y + this.yy_2[k];
                if (Node.check(new_x, new_y, mazeHeight ,mazeWeight) && result[new_x][new_y] == '.') {
                    dsu.merge(Node.GetIDFromNode(nodes[i], mazeHeight ,mazeWeight), Node.GetIDFromNode(new Node(new_x, new_y), mazeHeight ,mazeWeight ) );
                }
            }
        }
    }

    private ArrayList<Edge> GenerteEdges() {
        boolean[][] used = new boolean[mazeHeight][mazeWeight];
        ArrayList<Edge> result = new ArrayList();

        for(int i = 0; i < mazeHeight; ++i) {
            for(int j = 0; j < mazeWeight; ++j) {
                for(int k = 0; k < 4; ++k) {
                    int x = i + this.xx[k];
                    int y = j + this.yy[k];
                    if (Node.check(x, y, mazeHeight ,mazeWeight) && !used[Node.GetIDFromNode(new Node(i, j), mazeHeight ,mazeWeight)][Node.GetIDFromNode(new Node(x, y), mazeHeight ,mazeWeight)]) {
                        used[Node.GetIDFromNode(new Node(i, j), mazeHeight ,mazeWeight)][Node.GetIDFromNode(new Node(x, y), mazeHeight ,mazeWeight)] = true;
                        used[Node.GetIDFromNode(new Node(x, y), mazeHeight ,mazeWeight)][Node.GetIDFromNode(new Node(i, j), mazeHeight ,mazeWeight)] = true;
                        int mid_x = i + this.xx_2[k];
                        int mid_y = j + this.yy_2[k];
                        result.add(new Edge(Node.GetIDFromNode(new Node(i, j), mazeHeight ,mazeWeight), Node.GetIDFromNode(new Node(x, y), mazeHeight ,mazeWeight), Node.GetIDFromNode(new Node(mid_x, mid_y), mazeHeight ,mazeWeight)));
                    }
                }
            }
        }

        return result;
    }


}

