import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class NeuralGas {
    PApplet pApplet;
    List<PVector> referenceVectors;
    List<Edge> edges;

    public NeuralGas(PApplet pApplet) {
        this.pApplet = pApplet;
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
}



