import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node {

    PVector vector;
    PVector s1;
    float s1Dist = 0f;
    PVector s2;
    float s2Dist = 0f;
    List<Edge> edges;
    float error = 0f;

    public Node(PVector vector, List<Edge> edges) {
        this.vector = vector;
        this.edges = edges;
    }

    public List<Edge> getS1Edges(){
        return this.edges.stream().filter(edge -> edge.pointA == this.s1 || edge.pointB == this.s1).collect(Collectors.toList());
    }

    public List<PVector> getS1Neighbours(){
        List<PVector> neighbours = new ArrayList<>();
        neighbours.addAll(this.edges.stream().filter(edge -> edge.pointA == this.s1).map(edge -> edge.pointA).collect(Collectors.toList()));
        neighbours.addAll(this.edges.stream().filter(edge -> edge.pointB == this.s1).map(edge -> edge.pointB).collect(Collectors.toList()));
        return neighbours;
    }

    public float getError() {
        return error;
    }

    public void setError(float error) {
        this.error = error;
    }

    public PVector getVector() {
        return vector;
    }

    public void setVector(PVector vector) {
        this.vector = vector;
    }

    public PVector getS1() {
        return s1;
    }

    public void setS1(PVector s1) {
        this.s1 = s1;
    }

    public PVector getS2() {
        return s2;
    }

    public void setS2(PVector s2) {
        this.s2 = s2;
    }

    public float getS1Dist() {
        return s1Dist;
    }

    public void setS1Dist(float s1Dist) {
        this.s1Dist = s1Dist;
    }

    public float getS2Dist() {
        return s2Dist;
    }

    public void setS2Dist(float s2Dist) {
        this.s2Dist = s2Dist;
    }
}
