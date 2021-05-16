package com.csheros.packman.utils;

import com.csheros.packman.engine.Creature;
import com.csheros.packman.engine.EvilCreature;
import com.csheros.packman.engine.MapSize;
import com.csheros.packman.engine.Node;
import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.engine.NodePosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface NodeMapGenerator {

    String LEVEL_1 =
            "...----.....|.....----...\n" +
                    "..........--|--..........\n" +
                    "...|........b........|...\n" +
                    "...|....----|----....|...\n" +
                    "...|.................|...\n" +
                    "............|............\n" +
                    "......---.0.|.0.--.......\n" +
                    "...|........|........|...\n" +
                    "..........--|--..........\n" +
                    "...|.................|...\n" +
                    "...|....----|----....|...\n" +
                    "..0|........p........|0..\n" +
                    "............|............\n" +
                    "......---...|...---......";

    String LEVEL_2 =
            "...----.....|.....----...\n" +
                    "..........--|--..........\n" +
                    "...|........b........|...\n" +
                    "...|....----|----....|...\n" +
                    "...|.................|...\n" +
                    "............|............\n" +
                    "......---.0.|.0.--.......\n" +
                    "...|........|........|...\n" +
                    "..........--|--..........\n" +
                    "...|.................|...\n" +
                    "...|....----|----....|...\n" +
                    "..0|........p........|0..\n" +
                    "............|............\n" +
                    "x.....---...|...---.....b";

    String LEVEL_3 =
            "...----.....|.....----...\n" +
                    "..........--|--..........\n" +
                    "...|........b........|...\n" +
                    "...|....----|----....|...\n" +
                    "...|.................|...\n" +
                    "............|............\n" +
                    "......---.0.|.0.--.......\n" +
                    "...|........|........|...\n" +
                    "..........--|--..........\n" +
                    "...|.................|...\n" +
                    "...|....----|----....|...\n" +
                    "..0|........p........|0..\n" +
                    "............|............\n" +
                    "b.....---...|...---.....b";

    static NodeMap createDynamicNodeMap(int level) {
        switch (level) {
            case 1:
                return createFromTxt(LEVEL_1);
            case 2:
                return createFromTxt(LEVEL_2);
            default:
                return createFromTxt(LEVEL_3);
        }
    }

    static NodeMap createFromMazeTxt(String txt) {
        char[] mazeCharArray = txt.toCharArray();
        int numOfAgents = txt.length() / 300;
        int numOfMasterPoints = numOfAgents * 4;
        // Add agents
        int agentsIndexStart = txt.length() / 4;
        int firstDotForAgents = txt.indexOf('.', agentsIndexStart);
        for (int i = 0; i < numOfAgents; i++) {
            mazeCharArray[firstDotForAgents++] = 'x';
        }

        // Add MasterPoints
        int masterPointsIndexStart = txt.length() / numOfMasterPoints;
        int firstDotForMasterPoints = txt.indexOf('.', masterPointsIndexStart);
        for (int i = 0; i < numOfMasterPoints; i++) {
            mazeCharArray[firstDotForMasterPoints] = '0';
            firstDotForMasterPoints += masterPointsIndexStart;
        }

        int packManIndexStart = txt.length() * 3 / 4;
        int firstDotForPackMan = txt.indexOf('.', packManIndexStart);
        mazeCharArray[firstDotForPackMan] = 'p';

        return createFromTxt(String.valueOf(mazeCharArray));
    }

    static NodeMap createFromTxt(String txt) {
        List<Node[]> nodes = new ArrayList<>();
        NodeMap nodeMap = new NodeMap(nodes);
        Scanner scanner = new Scanner(txt);
        int row = 0;
        while (scanner.hasNextLine()) {
            String lineStr = scanner.nextLine();
            nodes.add(getCreaturesNodesFromLine(lineStr, nodeMap, row++));
        }
        nodeMap.setMapSize(new MapSize(nodes.get(0).length,
                nodes.size()));
        return nodeMap;
    }

    /**
     * Use these symbols for defining creatures
     * . for POINT
     * 0 for Master Point
     * x or r for (random) evil creature
     * b for (BFS) evil creature
     * d for (DFS) evil creature)
     * - or | for BLOCK
     * p for Pack-Man
     */
    static Node[] getCreaturesNodesFromLine(String lineStr, NodeMap nodeMap, int row) {
        char[] chars = lineStr.toCharArray();
        Node[] nodes = new Node[chars.length];
        for (int i = 0; i < chars.length; i++) {
            nodes[i] = new Node(nodeMap, new NodePosition(row, i));
            switch (chars[i]) {
                case '0':
                    nodes[i].addCreature(new Creature(Creature.Type.MASTER_POINT));
                    break;
                case '|':
                case '-':
                    nodes[i].addCreature(new Creature(Creature.Type.BLOCK));
                    break;
                case 'x':
                case 'r':
                    nodes[i].addCreature(new EvilCreature(EvilCreature.PathAlgo.RANDOM));
                    break;
                case 'b':
                    nodes[i].addCreature(new EvilCreature(EvilCreature.PathAlgo.BFS));
                    break;
                case 'd':
                    // Todo : Fix stack exception in this case
                    nodes[i].addCreature(new EvilCreature(EvilCreature.PathAlgo.DFS));
                    break;
                case 'p':
                    nodes[i].addCreature(new Creature(Creature.Type.PACK_MAN));
                    break;
                default:
                    nodes[i].addCreature(new Creature(Creature.Type.POINT));
            }

        }
        return nodes;
    }
}
