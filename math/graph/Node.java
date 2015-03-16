package math.graph;

import math.graph.Edge.Direction;

public class Node {
    private int threshold = 5;
    private int trueSize;
    private Edge edges[] = new Edge[threshold];
    private String name;

    public Node(String name) {
        this.name = name;
    }

    public Node addEdge(Edge e, Object key) {
        if (e == null) {
            System.out.println("Got a null edge to add");
            return this;
        }
        if (!checkCaller(key)) {
            System.out.println("The Edge to add was already initialized...");
            return this;
        }
        if (trueSize >= edges.length) {
            int oldSize = edges.length;
            System.arraycopy(edges, 0, edges = new Edge[oldSize + threshold], 0, oldSize);
        }
        edges[trueSize++] = e;
        return this;
    }

    public Edge[] getEdges(Object key) {
        if (checkCaller(key))
            return edges;
        return null;
    }
    
    // Checks for access in order to allow calls only from Edge (preventing inconsistencies)
    private static boolean checkCaller(Object key) {
        return Edge.verifyKey(key);
    }

    public static void main(String[] args) {
        Node n = new Node("A");
        Node g = new Node("B");
        Edge e = new Edge(n, g, Direction.BOTH);
        System.out.println(n.trueSize);
    }

}

class Edge {

    enum Direction {
        A_TO_B, B_TO_A, BOTH;
    }
    private Direction direction;
    private Node nodeA;
    private Node nodeB;

    private static final Object key = new Object();

    public Edge(Node nodeA, Node nodeB, Direction direction) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.direction = direction;

        this.nodeA.addEdge(this, key);
        this.nodeB.addEdge(this, key);
    }

    public static boolean verifyKey(Object key) {
        return Edge.key == key;
    }

    public static Edge getConnection(Node a, Node b) {
        Edge[] edges = a.getEdges(key);
        for (Edge e : edges) {
            if ((e.nodeA == a && e.nodeB == b) || (e.nodeA == b && e.nodeB == a)) {
                return e;
            }
        }
        return null;
    }
}