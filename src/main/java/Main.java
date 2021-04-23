import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

public class Main extends PApplet {

    // BASIC SETTINGS
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    public static final int BACKGROUND_COLOR = 51;

    Data data;

    public void settings() {
        size(WIDTH, HEIGHT);
        data = new Data(this);
        data.initializeData();
    }

    public void draw() {
        background(BACKGROUND_COLOR);

        this.data.draw();
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}

