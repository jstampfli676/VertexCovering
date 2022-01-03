package graph;

public class Edge {
    private int score;
    private Node start;
    private Node end;
    private boolean directed;
    private boolean bridge;

    public Edge() {

    }

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public Node contains(Node node) {
        if (start.equals(node)) {
            return end;
        }
        if (end.equals(node)) {
            return start;
        }
        return null;
    }

    public void isBridge() {
        this.bridge = true;
        this.score = 2;
    }

    public void notBridge() {
        this.bridge = false;
        this.score = 1;
    }

    public Edge deepCopy() {
        Edge e = new Edge();
        e.start = this.start.deepCopy();
        e.end = this.end.deepCopy();
        e.directed = this.directed;
        e.bridge = this.bridge;
        e.score = this.score;
        return e;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Edge)) {
            return false;
        }
        Edge e = (Edge) o;
        return (e.start.equals(this.start) && e.end.equals(this.end)) || (e.start.equals(this.end) && e.end.equals(this.start));
    }

    public String toString() {
        return start.getId()+" "+String.valueOf(end.getId())+" "+score;
    }

    public int getScore() {
        return this.score;
    }

    public Node getStart() {
        return this.start;
    }

    public Node getEnd() {
        return this.end;
    }
}
