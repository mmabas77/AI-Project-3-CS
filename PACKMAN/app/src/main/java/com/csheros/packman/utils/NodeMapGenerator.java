package com.csheros.packman.utils;

import com.csheros.packman.engine.Creature;
import com.csheros.packman.engine.MapSize;
import com.csheros.packman.engine.Node;
import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.engine.NodePosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface NodeMapGenerator {

    static NodeMap createStaticNodeMap() {
        return new NodeMap(
                new MapSize(7, 7),
                new NodePosition(1, 4),
                new NodePosition[]{
                        new NodePosition(1, 1),
                        new NodePosition(5, 1)
                },
                new NodePosition[]{
                        new NodePosition(1, 2),
                        new NodePosition(2, 2),
                        new NodePosition(2, 3),

                },
                new NodePosition[]{new NodePosition(1, 3)}
        );
    }

    static NodeMap createDynamicNodeMap(int level) {
        // Todo : Implement this
        return createFromTxt(
                        "............|............\n" +
                        "..........--|--..........\n" +
                        "...|........x........|...\n" +
                        "........----|----........\n" +
                        "...|.................|...\n" +
                        "............|............\n" +
                        "......---.0.|.0.--.......\n" +
                        "............|............\n" +
                        "..........--|--..........\n" +
                        "...|.................|...\n" +
                        "........----|----........\n" +
                        "..0|........p........|0..\n" +
                        "............|............\n" +
                        "......---...|...--......."

        );
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
        nodeMap.setMapSize(new MapSize(nodes.get(0).length, nodes.size()));
        return nodeMap;
    }

    /**
     * Use these symbols for defining creatures
     * . for POINT
     * 0 for Master Point
     * x for evil creature
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
                    nodes[i].addCreature(new Creature(Creature.Type.EVIL_CREATURE));
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
