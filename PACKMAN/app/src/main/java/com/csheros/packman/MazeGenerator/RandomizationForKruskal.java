
package com.csheros.packman.MazeGenerator ;


import com.csheros.packman.MazeGenerator.Util.DSU;
import com.csheros.packman.MazeGenerator.Util.Edge;
import com.csheros.packman.MazeGenerator.Util.Node;

import java.util.ArrayList;
import java.util.Collections;

public class RandomizationForKruskal implements GetMaze{
    private int n;
    private int[] xx = new int[]{0, 0, -2, 2, 0};
    private int[] yy = new int[]{2, -2, 0, 0, 0};
    private int[] xx_2 = new int[]{0, 0, -1, 1, 0};
    private int[] yy_2 = new int[]{1, -1, 0, 0, 0};

    public RandomizationForKruskal(int n) {
        this.n = n;
    }

    private Node GetNodeFromID(int x) {
        return new Node(x / this.n, x % this.n);
    }

    private int GetIDFromNode(Node s) {
        return this.n * s.x + s.y;
    }


    @Override
    public char[][] GetRandomMaze(int mazeHeight, int mazeWeight) {
        return new char[0][];
    }

    public char[][] GetMap() {
        char[][] result = new char[this.n][this.n];

        for(int i = 0; i < this.n; ++i) {
            for(int j = 0; j < this.n; ++j) {
                result[i][j] = '*';
            }
        }

        DSU dsu = new DSU(this.n * this.n);
        ArrayList edges = this.GenerteEdges(this.n);

        while(edges.size() > 0) {
            Collections.shuffle(edges);
            Edge currentEdge = (Edge)edges.get(edges.size() - 1);
            edges.remove(edges.size() - 1);
            if (!dsu.isSame(currentEdge.u, currentEdge.v) && this.CheckNeighbors(dsu, currentEdge)) {
                result[this.GetNodeFromID(currentEdge.u).x][this.GetNodeFromID(currentEdge.u).y] = '.';
                result[this.GetNodeFromID(currentEdge.mid).x][this.GetNodeFromID(currentEdge.mid).y] = '.';
                result[this.GetNodeFromID(currentEdge.v).x][this.GetNodeFromID(currentEdge.v).y] = '.';
                dsu.merge(currentEdge.u, currentEdge.v);
                dsu.merge(currentEdge.u, currentEdge.mid);
                dsu.merge(currentEdge.v, currentEdge.mid);
                this.ConnectToNeighbors(result, dsu, currentEdge);
            }
        }

        return result;
    }

    void print(char[][] result) {
        for(int i = 0; i < this.n; ++i) {
            for(int j = 0; j < this.n; ++j) {
                System.out.print(result[i][j]);
            }

            System.out.println();
        }

    }

    boolean check(Node node) {
        return node.x >= 0 && node.y >= 0 && node.x < this.n && node.y < this.n;
    }

    boolean check(int x, int y) {
        return x >= 0 && x < this.n && y >= 0 && y < this.n;
    }

    private boolean CheckNeighbors(DSU dsu, Edge currentEdge) {
        Node u = this.GetNodeFromID(currentEdge.u);
        Node v = this.GetNodeFromID(currentEdge.v);
        boolean res = true;

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Node new_u = new Node(u.x + this.xx_2[i], u.y + this.yy_2[i]);
                Node new_v = new Node(v.x + this.xx_2[j], v.y + this.yy_2[j]);
                if (!new_u.equals(new_v) && this.check(new_u) && this.check(new_v)) {
                    res = res && !dsu.isSame(this.GetIDFromNode(new_u), this.GetIDFromNode(new_v));
                }
            }
        }

        return res;
    }

    void ConnectToNeighbors(char[][] result, DSU dsu, Edge currentEdge) {
        Node[] nodes = new Node[]{this.GetNodeFromID(currentEdge.u), this.GetNodeFromID(currentEdge.v), this.GetNodeFromID(currentEdge.mid)};

        for(int i = 0; i < 3; ++i) {
            for(int k = 0; k < 4; ++k) {
                int new_x = nodes[i].x + this.xx_2[k];
                int new_y = nodes[i].y + this.yy_2[k];
                if (this.check(new_x, new_y) && result[new_x][new_y] == '.') {
                    dsu.merge(this.GetIDFromNode(nodes[i]), this.GetIDFromNode(new Node(new_x, new_y)));
                }
            }
        }

    }

    public ArrayList<Edge> GenerteEdges(int n) {
        boolean[][] used = new boolean[n * n][n * n];
        ArrayList<Edge> result = new ArrayList();

        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                for(int k = 0; k < 4; ++k) {
                    int x = i + this.xx[k];
                    int y = j + this.yy[k];
                    if (this.check(x, y) && !used[this.GetIDFromNode(new Node(i, j))][this.GetIDFromNode(new Node(x, y))]) {
                        used[this.GetIDFromNode(new Node(i, j))][this.GetIDFromNode(new Node(x, y))] = true;
                        used[this.GetIDFromNode(new Node(x, y))][this.GetIDFromNode(new Node(i, j))] = true;
                        int mid_x = i + this.xx_2[k];
                        int mid_y = j + this.yy_2[k];
                        result.add(new Edge(this.GetIDFromNode(new Node(i, j)), this.GetIDFromNode(new Node(x, y)), this.GetIDFromNode(new Node(mid_x, mid_y))));
                    }
                }
            }
        }

        return result;
    }


}

