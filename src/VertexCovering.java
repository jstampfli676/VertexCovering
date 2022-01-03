import graph.*;

import java.util.ArrayList;

public class VertexCovering {

    private static ArrayList<ArrayList<Node>> coloredCombinations = new ArrayList<>();

    public static ArrayList<Node> generateMinVertexCovering(Graph input) {
        Graph g = input.deepCopy();
        //Graph g = input;
        ArrayList<Node> nodes = g.getNodes();
        ArrayList<Edge> edges = g.getEdges();

        ArrayList<Node> answer = new ArrayList<>();

        while (edges.size()>0) {
            int maxScore = -1;
            int maxIndex = -1;
            int minTBS = Integer.MAX_VALUE;
            for (int i = 0; i<nodes.size(); i++) {
                int curScore = nodes.get(i).getScore();
                int curTBS = nodes.get(i).getTieBreakScore();
                if (curScore>maxScore) {
                    maxScore = curScore;
                    maxIndex = i;
                    minTBS = curTBS;
                } else if (curScore == maxScore) {
                    if (curTBS < minTBS) {
                        maxIndex = i;
                        minTBS = curTBS;
                    }
                }
            }
            answer.add(nodes.get(maxIndex));
            g.removeNode(nodes.get(maxIndex));
            //System.out.println("updated graph"+"\n"+g+"\n");
            nodes = g.getNodes();
            edges = g.getEdges();
        }
        return answer;
    }

    public static boolean checkVertexCovering(Graph input, ArrayList<Node> answer) {
        ArrayList<Edge> edges = input.getEdges();

        for (int i = 0; i<edges.size(); i++) {
            Edge curEdge = edges.get(i);
            boolean pass = false;
            for (int x = 0; x<answer.size(); x++) {
                Node curNode = answer.get(x);
                if (curEdge.contains(curNode)!=null) {
                    pass = true;
                    break;
                }
            }
            if (!pass) {
                return false;
            }
        }

        /*for (int i = 0; i<nodes.size(); i++) {
            if (answer.contains(nodes.get(i))) {
                continue;
            }
            boolean pass = false;
            for (int x = 0; x<edges.size(); x++) {
                Node connected = edges.get(x).contains(nodes.get(i));
                if (answer.contains(connected)) {
                    pass = true;
                }
            }
            if (!pass) {
                return false;
            }
        }*/
        return true;
    }

    public static boolean existVertexCovering(Graph input, int coveringSize) {
        coloredCombinations.clear();
        helper(input.getNodes(), new ArrayList<Node>(), 0, coveringSize);
        //System.out.println("all combinations: "+coloredCombinations);
        for (int i = 0; i<coloredCombinations.size(); i++) {
            if (checkVertexCovering(input, coloredCombinations.get(i))) {
                //System.out.println("the smaller covering: " + coloredCombinations.get(i));
                return true;
            }
        }
        return false;
    }

    private static void helper(ArrayList<Node> graph, ArrayList<Node> combination, int index, int size) {
        if (combination.size()==size) {
            coloredCombinations.add(combination);
        } else if (index<graph.size()) {
            ArrayList<Node> temp = deepCopy(combination);
            temp.add(graph.get(index));
            helper(graph, temp, index + 1, size);
            helper(graph, combination, index + 1, size);
        }
    }

    private static ArrayList<Node> deepCopy(ArrayList<Node> input) {
        ArrayList<Node> copy = new ArrayList<>();
        for (int i = 0; i<input.size(); i++) {
            copy.add(input.get(i).deepCopy());
        }
        return copy;
    }

    public static void main(String[] args) {
        /*Graph g = new Graph(5,5);
        System.out.println(g);
        System.out.println(generateMinVertexCovering(g));*/

        /*Graph g = new Graph();

        for (int i = 0; i<6; i++) {
            g.addNode(new Node(i));
        }

        ArrayList<Node> nodes = g.getNodes();

        g.addEdge(new Edge(nodes.get(0), nodes.get(3)));
        g.addEdge(new Edge(nodes.get(1), nodes.get(2)));
        g.addEdge(new Edge(nodes.get(1), nodes.get(3)));
        g.addEdge(new Edge(nodes.get(2), nodes.get(0)));
        g.addEdge(new Edge(nodes.get(3), nodes.get(2)));
        g.addEdge(new Edge(nodes.get(4), nodes.get(1)));
        g.addEdge(new Edge(nodes.get(5), nodes.get(4)));
        g.addEdge(new Edge(nodes.get(5), nodes.get(2)));
        g.recalculateScores();

        System.out.println(g);

        ArrayList<Node> minVertexCovering = generateMinVertexCovering(g);
        System.out.println(minVertexCovering);*/

        double trials = 10000;
        double goodCount = 0;
        double badCount = 0;
        for (int i = 0; i<=trials; i++) {
            Graph g = new Graph(21, 21);//capped at roughly 20 when using np algo for testing
            ArrayList<Node> vertexCovering = generateMinVertexCovering(g);
            if (!checkVertexCovering(g, vertexCovering) || existVertexCovering(g, vertexCovering.size()-1)) {
                /*System.out.println(g);
                System.out.println(vertexCovering);
                break;*/
                badCount++;
            } else {
                goodCount++;
            }
            if (i%(trials/100)==0) {
                System.out.println(i/trials);
            }
        }
        System.out.println("percentage correct answer: " +goodCount/(trials));
    }
}
