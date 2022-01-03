package graph;

public class Node implements Cloneable{
    private int id;
    private boolean isColored;
    private int score;
    private int tieBreakScore;

    public Node(int id) {
        this.id = id;
    }

    public Node deepCopy() {
        Node n = new Node(this.id);
        n.isColored = this.isColored;
        n.score = this.score;
        n.tieBreakScore = this.tieBreakScore;
        return n;
    }

    public Object clone() {
        Node node;
        try {
            node = (Node) super.clone();
        } catch(CloneNotSupportedException e) {
            return null;
        }
        return node;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Node)) {
            return false;
        }
        Node n = (Node)o;
        return n.id==this.id;
    }

    public String toString() {
        return id + " " +score+" "+tieBreakScore;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTieBreakScore(int score) {
        this.tieBreakScore = score;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return this.score;
    }

    public int getTieBreakScore() {
        return tieBreakScore;
    }
}
