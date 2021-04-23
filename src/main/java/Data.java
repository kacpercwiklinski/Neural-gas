import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Data {
    PApplet pApplet;
    List<PVector> dataVectors;

    public Data(PApplet pApplet) {
        this.pApplet = pApplet;
        dataVectors = new ArrayList<PVector>();
    }

    public void initializeData() {
        PVector a = new PVector(Main.WIDTH / 4, Main.HEIGHT / 4);
        PVector b = new PVector(Main.WIDTH / 4 * 3, Main.HEIGHT / 4);
        PVector c = new PVector(Main.WIDTH / 4 * 3, Main.HEIGHT / 4 * 3);
        PVector d = new PVector(Main.WIDTH / 4, Main.HEIGHT / 4 * 3);
        dataVectors.add(a);
        dataVectors.add(b);
        dataVectors.add(c);
        dataVectors.add(d);
    }

    public void draw() {
        dataVectors.forEach(vector -> {
            this.pApplet.stroke(255,255,255);
            this.pApplet.strokeWeight(10f);
            this.pApplet.point(vector.x,vector.y);
        });
    }
}
