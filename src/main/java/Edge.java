import processing.core.PVector;

public class Edge {

    PVector pointA;
    PVector pointB;
    float age;

    public Edge(PVector pointA, PVector pointB, float age) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.age = age;
    }

    public PVector getPointA() {
        return pointA;
    }

    public void setPointA(PVector pointA) {
        this.pointA = pointA;
    }

    public PVector getPointB() {
        return pointB;
    }

    public void setPointB(PVector pointB) {
        this.pointB = pointB;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }
}
