
package com.csheros.packman.MazeGenerator ;
import com.csheros.packman.MazeGenerator.DSU;


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

import java.util.ArrayList;
import java.util.Collections;

public class Randomization {
    private int n;
    private int[] xx = new int[]{0, 0, -2, 2, 0};
    private int[] yy = new int[]{2, -2, 0, 0, 0};
    private int[] xx_2 = new int[]{0, 0, -1, 1, 0};
    private int[] yy_2 = new int[]{1, -1, 0, 0, 0};

    public Randomization(int n) {
        this.n = n;
    }

    private Randomization.Node GetNodeFromID(int x) {
        return new Randomization.Node(x / this.n, x % this.n);
    }

    private int GetIDFromNode(Randomization.Node s) {
        return this.n * s.x + s.y;
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
            Randomization.Edge currentEdge = (Randomization.Edge)edges.get(edges.size() - 1);
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

    boolean check(Randomization.Node node) {
        return node.x >= 0 && node.y >= 0 && node.x < this.n && node.y < this.n;
    }

    boolean check(int x, int y) {
        return x >= 0 && x < this.n && y >= 0 && y < this.n;
    }

    private boolean CheckNeighbors(DSU dsu, Randomization.Edge currentEdge) {
        Randomization.Node u = this.GetNodeFromID(currentEdge.u);
        Randomization.Node v = this.GetNodeFromID(currentEdge.v);
        boolean res = true;

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Randomization.Node new_u = new Randomization.Node(u.x + this.xx_2[i], u.y + this.yy_2[i]);
                Randomization.Node new_v = new Randomization.Node(v.x + this.xx_2[j], v.y + this.yy_2[j]);
                if (!new_u.equals(new_v) && this.check(new_u) && this.check(new_v)) {
                    res = res && !dsu.isSame(this.GetIDFromNode(new_u), this.GetIDFromNode(new_v));
                }
            }
        }

        return res;
    }

    void ConnectToNeighbors(char[][] result, DSU dsu, Randomization.Edge currentEdge) {
        Randomization.Node[] nodes = new Randomization.Node[]{this.GetNodeFromID(currentEdge.u), this.GetNodeFromID(currentEdge.v), this.GetNodeFromID(currentEdge.mid)};

        for(int i = 0; i < 3; ++i) {
            for(int k = 0; k < 4; ++k) {
                int new_x = nodes[i].x + this.xx_2[k];
                int new_y = nodes[i].y + this.yy_2[k];
                if (this.check(new_x, new_y) && result[new_x][new_y] == '.') {
                    dsu.merge(this.GetIDFromNode(nodes[i]), this.GetIDFromNode(new Randomization.Node(new_x, new_y)));
                }
            }
        }

    }

    public ArrayList<Randomization.Edge> GenerteEdges(int n) {
        boolean[][] used = new boolean[n * n][n * n];
        ArrayList<Randomization.Edge> result = new ArrayList();

        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                for(int k = 0; k < 4; ++k) {
                    int x = i + this.xx[k];
                    int y = j + this.yy[k];
                    if (this.check(x, y) && !used[this.GetIDFromNode(new Randomization.Node(i, j))][this.GetIDFromNode(new Randomization.Node(x, y))]) {
                        used[this.GetIDFromNode(new Randomization.Node(i, j))][this.GetIDFromNode(new Randomization.Node(x, y))] = true;
                        used[this.GetIDFromNode(new Randomization.Node(x, y))][this.GetIDFromNode(new Randomization.Node(i, j))] = true;
                        int mid_x = i + this.xx_2[k];
                        int mid_y = j + this.yy_2[k];
                        result.add(new Randomization.Edge(this.GetIDFromNode(new Randomization.Node(i, j)), this.GetIDFromNode(new Randomization.Node(x, y)), this.GetIDFromNode(new Randomization.Node(mid_x, mid_y))));
                    }
                }
            }
        }

        return result;
    }

    private class Edge {
        private int u;
        private int v;
        private int mid;

        public Edge(int u, int v, int mid) {
            this.u = u;
            this.v = v;
            this.mid = mid;
        }
    }

    private class Node {
        public int x;
        public int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Randomization.Node other) {
            return other.x == this.x && other.y == this.y;
        }
    }
}

