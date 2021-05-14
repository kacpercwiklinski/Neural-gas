import processing.core.PVector;

public class Edge {

    final Node pointA;
    final Node pointB;
    float age;

    public Edge(Node pointA, Node pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public Node getOther(Node node) {
        if (pointA == node) {
            return pointB;
        } else {
            return pointA;
        }
    }

    public void increaseAge() {
        this.age += 1;
    }

    public PVector getPointA() {
        return pointA;
    }

    public PVector getPointB() {
        return pointB;
    }


    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }
}
