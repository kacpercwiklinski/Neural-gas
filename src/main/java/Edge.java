import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Edge {

    Node pointA;
    Node pointB;
    float age;

    public Edge(Node pointA, Node pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public List<Node> getPoints(){
        List<Node> points = new ArrayList<Node>();
        points.add(this.pointA);
        points.add(this.pointB);
        return points;
    }

    public void increaseAge(){
        this.age += 1;
    }

    public PVector getPointA() {
        return pointA;
    }

    public void setPointA(Node pointA) {
        this.pointA = pointA;
    }

    public PVector getPointB() {
        return pointB;
    }

    public void setPointB(Node pointB) {
        this.pointB = pointB;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }
}
