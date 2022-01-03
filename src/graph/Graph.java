package graph;

import java.util.ArrayList;
import graph_algorithms.*;

public class Graph {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;


    public Graph() {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
    }

    public Graph(int minNodes, int maxNodes) {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        generateGraph(minNodes, maxNodes);
    }

    public void generateGraph(int minNodes, int maxNodes) {
        int numNodes = (int)(Math.random() * (maxNodes-minNodes+1) + minNodes);
        for (int i = 0; i<numNodes; i++) {
            this.addNode(new Node(i));
        }

        int connectedVal = 1;
        for (int x = 0; x<nodes.size(); x++) {
            int numEdges = (int)(Math.random() * (numNodes-1) + connectedVal);//can be manipulated to generate more sparse or dense graphs
            for (int i = 0; i<numEdges; i++) {
                int endNode = (int)(Math.random() * (nodes.size()));
                while (endNode == x) {
                    endNode = (int)(Math.random() * (nodes.size()));
                }
                this.addEdge(new Edge(nodes.get(x), nodes.get(endNode)));
            }
        }
        recalculateScores();
    }

    public void recalculateScores() {
        findBridges();
        calculateNodeScores();
        calculateNodeTieBreakScores();
    }

    private void findBridges() {
        for (int i = 0; i<edges.size(); i++) {
            if (!DFS.connected(this, edges.get(i))) {
                edges.get(i).isBridge();
            } else {
                edges.get(i).notBridge();
            }
        }
    }

    private void calculateNodeScores() {
        for (int i = 0; i<nodes.size(); i++) {
            int score = 0;
            for (int x = 0; x<edges.size(); x++) {
                if (edges.get(x).contains(nodes.get(i))!=null) {
                    score+=edges.get(x).getScore();
                }
            }
            nodes.get(i).setScore(score);
        }
    }

    private void calculateNodeTieBreakScores() {
        for (int i = 0; i<nodes.size(); i++) {
            int score = 0;
            Node curNode = nodes.get(i);
            for (int x = 0; x<edges.size(); x++) {
                Node adjacent = edges.get(x).contains(curNode);
                if (adjacent!=null) {
                    score+=adjacent.getScore();
                }
            }
            curNode.setTieBreakScore(score);
        }
    }

    public void addNode(Node node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
        }
    }

    public void removeNode(Node node) {
        if (nodes.contains(node)) {
            nodes.remove(node);

            int i = 0;
            while (i < edges.size()) {
                if (edges.get(i).contains(node)!=null) {
                    edges.remove(i);
                    i--;
                }
                i++;
            }

            /*for (int i = 0; i<edges.size(); i++) {
                if (edges.get(i).contains(node)!=null) {
                    edges.remove(i);
                }
            }*/
            recalculateScores();
        }
    }

    public void addEdge(Edge edge) {
        if (!edges.contains(edge)) {
            edges.add(edge);
        }
    }

    public void removeEdge(Edge edge) {
        if (edges.contains(edge)) {
            edges.remove(edge);
            recalculateScores();
        }
    }

    public Graph deepCopy() {//something slightly wrong with this method, when changing the variables of a node it doesnt change
        //the variables of the node contained within each edge for graphs created via this method
        Graph copy = new Graph();
        for (int i = 0; i<this.nodes.size(); i++) {
            copy.nodes.add(this.nodes.get(i).deepCopy());
        }
        ArrayList<Node> nodes = copy.getNodes();
        for (int i = 0; i<this.edges.size(); i++) {
            //copy.edges.add(this.edges.get(i).deepCopy());
            copy.addEdge(new Edge(nodes.get(edges.get(i).getStart().getId()), nodes.get(edges.get(i).getEnd().getId())));
        }
        return copy;
    }

    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public String toString() {
        String output = String.valueOf(nodes) + "\n" + String.valueOf(edges);
        return output;
    }
}
