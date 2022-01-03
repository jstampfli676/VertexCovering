package graph_algorithms;

import graph.*;

import java.util.ArrayList;

public class DFS {

    public static boolean connected(Graph g, Edge excluded) {
        ArrayList<Node> visited = new ArrayList<>();
        ArrayList<Node> nodes = g.getNodes();
        ArrayList<Edge> edges = g.getEdges();
        ArrayList<Node> toVisit = new ArrayList<>();
        toVisit.add(nodes.get(0));

        while (toVisit.size()>0) {
            Node expanded = toVisit.get(0);
            visited.add(expanded);
            toVisit.remove(0);
            for (int i = 0; i<edges.size(); i++) {
                if (edges.get(i).equals(excluded)) {
                    continue;//working
                }
                Node connected = edges.get(i).contains(expanded);
                if (connected != null && !visited.contains(connected) && !toVisit.contains(connected)) {
                    toVisit.add(connected);
                }
            }
        }
        //System.out.println(visited);
        //System.out.println(visited.size()+" "+nodes.size());
        if (visited.size() == nodes.size()) {
            return true;
        }
        return false;
    }
}
