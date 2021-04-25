import de.milchreis.uibooster.UiBooster;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {

    // BASIC SETTINGS
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    public static final int BACKGROUND_COLOR = 51;
    public static final Boolean DRAW_IMAGE = false;
    public static final Boolean DRAW_INPUT_NODES = false;

    // Gas settings
    public static final float LAMBDA = 300;
    public static final float E_B = 0.2f;
    public static final float E_N = 0.006f;
    public static final float ALFA = 0.5f;
    public static final float D = 0.995f;
    public static final int A_MAX = 50;
    public static int SPEED_UP = 200;

    // Image settings
    PImage inputImage;

    List<PVector> dataNodes;
    NeuralGas neuralGas;
    UiBooster uiBooster;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size(WIDTH, HEIGHT);
        uiBooster = new UiBooster();
        dataNodes = new ArrayList<>();
        //initializeData();

        neuralGas = new NeuralGas(this);
        neuralGas.initializeNeuralGas();
    }

    public void setup() {
        inputImage = loadImage("test.png");

        if (inputImage != null) {
            this.dataNodes = initializeData(inputImage);
        }
    }

    public void draw() {

        if (DRAW_IMAGE)
            image(inputImage, 0, 0);
        else
            background(BACKGROUND_COLOR);

        if (this.dataNodes.size() > 0) {
            for (int i = 0; i < SPEED_UP; i++) {
                this.neuralGas.nextIteration(this.dataNodes);
            }
        }

        this.neuralGas.drawGas();

        if (DRAW_INPUT_NODES)
            this.dataNodes.forEach(node -> {
                stroke(255, 255, 255, 51f);
                strokeWeight(3f);
                point(node.x, node.y);
            });

        handleMouse();
    }

    public void initializeData() {
        dataNodes = new ArrayList<>();
        PVector a = new PVector((float) Main.WIDTH / 4, (float) Main.HEIGHT / 4);
        PVector b = new PVector((float) Main.WIDTH / 4 * 3, (float) Main.HEIGHT / 4);
        PVector c = new PVector((float) Main.WIDTH / 4 * 3, (float) Main.HEIGHT / 4 * 3);
        PVector d = new PVector((float) Main.WIDTH / 4, (float) Main.HEIGHT / 4 * 3);
        dataNodes.add(a);
        dataNodes.add(b);
        dataNodes.add(c);
        dataNodes.add(d);
    }

    public List<PVector> initializeData(PImage image) {
        List<PVector> list = new ArrayList<>();
        image.loadPixels();
        if (image.pixels != null) {
            for (int y = 0; y < image.height; y++) {
                for (int x = 0; x < image.width; x++) {
                    int pos = y * image.width + x;
                    float val = brightness(image.pixels[pos]);
                    if (val > 240.0f) {
                        list.add(new PVector(x, y));
                    }
                }
            }
        }
        return list;
    }

    public void handleMouse() {
        if (mousePressed && mouseButton == LEFT) {
            this.dataNodes.add(new PVector(mouseX, mouseY));
        }
    }
}

