import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Data {
    PApplet pApplet;
    List<Vector> dataVectors;

    public Data(PApplet pApplet) {
        this.pApplet = pApplet;
        dataVectors = new ArrayList<Vector>();
    }

    public void initializeData() {
        Vector a = new Vector(new PVector(Main.WIDTH / 4, Main.HEIGHT / 4), new PVector(Main.WIDTH / 4 * 3, Main.HEIGHT / 4));
        Vector b = new Vector(a.getEnd(), new PVector(a.getEnd().x, Main.HEIGHT / 4 * 3));
        Vector c = new Vector(b.getEnd(), new PVector(Main.WIDTH / 4, b.getEnd().y));
        Vector d = new Vector(c.getEnd(), a.getStart());
        dataVectors.add(a);
        dataVectors.add(b);
        dataVectors.add(c);
        dataVectors.add(d);
    }

    public void draw() {
        dataVectors.forEach(vector -> {
            this.pApplet.stroke(255,255,255);
            this.pApplet.line(vector.getStart().x, vector.getStart().y, vector.getEnd().x, vector.getEnd().y);
        });
    }
}
