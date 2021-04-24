import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class NeuralGas {
    PApplet pApplet;
    List<PVector> referenceVectors;
    List<Edge> edges;
    List<Node> dataNodes;

    public NeuralGas(PApplet pApplet, List<Node> dataNodes) {
        this.pApplet = pApplet;
        this.dataNodes = dataNodes;
        referenceVectors = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void initializeNeuralGas() {
        PVector a = new PVector((float) Math.random() * Main.WIDTH, (float) Math.random() * Main.HEIGHT);
        PVector b = new PVector((float) Math.random() * Main.WIDTH, (float) Math.random() * Main.HEIGHT);
        Edge edge = new Edge(a, b, 0);
        this.edges.add(edge);
        referenceVectors.add(a);
        referenceVectors.add(b);
    }

    public void determineWinners() {
        for (Node inVec : this.dataNodes) {
            inVec.setS1(referenceVectors.stream().min((vec1,vec2) -> inVec.getVector().dist(vec1) < inVec.getVector().dist(vec2) ? 1 : -1).get());
            inVec.setS1Dist(inVec.getVector().dist(inVec.getS1()));
            inVec.setS2(referenceVectors.stream().filter(vec -> vec != inVec.getS1()).min((vec1,vec2) -> inVec.getVector().dist(vec1) < inVec.getVector().dist(vec2) ? 1 : -1).get());
            inVec.setS2Dist(inVec.getVector().dist(inVec.getS2()));
        }
    }

    public void updateEdgesAge(){
        for (Node inVec : this.dataNodes){
            inVec.getS1Edges().forEach(edge -> edge.age += edge.age + 1);
            inVec.setError(inVec.error + inVec.getS1Dist() * inVec.getS1Dist());
        }
    }

    public void updateNeighbours(){
        for (Node inVec : this.dataNodes){
            inVec.getS1().set(new PVector(inVec.getVector().x,inVec.getVector().y).sub(inVec.getS1().x,inVec.getS1().y).mult(Main.E_B));
            inVec.getS1Neighbours().forEach(neighbour -> neighbour.set(new PVector(inVec.getVector().x,inVec.getVector().y).sub(neighbour.x,neighbour.y).mult(Main.E_N)));
        }
    }

    public void updateEdges(){
        for (Node inVec : this.dataNodes) {
            Edge e = this.edges.stream().filter(edge -> (edge.pointA == inVec.s1 && edge.pointB == inVec.s2) || (edge.pointB == inVec.s1 && edge.pointA == inVec.s2)).findFirst().get();

            if(e != null){
                e.setAge(0f);
            }else{
                this.edges.add(new Edge(inVec.s1, inVec.s2,0f));
            }

            this.edges.stream().forEach(edge -> {
                if (edge.getAge() > Main.A_MAX){
                    this.edges.remove(edge);
                }
            });


        }
    }

    public void updateGas(){
        determineWinners();
        updateEdgesAge();
        updateNeighbours();
        updateEdges();
    }

    public void drawGas() {
        referenceVectors.forEach(pVector -> {
            this.pApplet.strokeWeight(5f);
            this.pApplet.stroke(255, 0, 0);
            this.pApplet.point(pVector.x, pVector.y);
        });

        edges.forEach(edge -> {
            this.pApplet.strokeWeight(1f);
            this.pApplet.line(edge.pointA.x, edge.pointA.y, edge.pointB.x, edge.pointB.y);
        });
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}



