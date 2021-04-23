import processing.core.PApplet;

public class Main extends PApplet {

    // BASIC SETTINGS
    private static final int HEIGHT = 800;
    private static final int WIDTH = 800;
    private static final int BACKGROUND_COLOR = 51;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void draw() {
        background(BACKGROUND_COLOR);
    }
}

