import de.milchreis.uibooster.UiBooster;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {

    // BASIC SETTINGS
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    public static final int BACKGROUND_COLOR = 51;

    // Gas settings
    public static final float LAMBDA = 50;
    public static final float E_B = 0.2f;
    public static final float E_N = 0.006f;
    public static final float ALFA = 0.5f;
    public static final float D = 0.995f;
    public static final int A_MAX = 50;

    List<PVector> dataNodes;
    NeuralGas neuralGas;
    UiBooster uiBooster;

    public void settings() {
        size(WIDTH, HEIGHT);
        uiBooster = new UiBooster();
        dataNodes = new ArrayList<>();

        initializeData();

        neuralGas = new NeuralGas(this, this.dataNodes);
        neuralGas.initializeNeuralGas();
    }

    public void draw() {
        background(BACKGROUND_COLOR);
        this.neuralGas.updateGas();

        this.neuralGas.drawGas();
        this.dataNodes.forEach(node -> {
            stroke(255, 255, 255, 51f);
            strokeWeight(3f);
            point(node.x, node.y);
        });

        handleMouse();
    }

    public void initializeData(){
        dataNodes = new ArrayList<>();
        PVector a = new PVector(Main.WIDTH / 4, Main.HEIGHT / 4);
        PVector b = new PVector(Main.WIDTH / 4 * 3, Main.HEIGHT / 4);
        PVector c = new PVector(Main.WIDTH / 4 * 3, Main.HEIGHT / 4 * 3);
        PVector d = new PVector(Main.WIDTH / 4, Main.HEIGHT / 4 * 3);
        dataNodes.add(a);
        dataNodes.add(b);
        dataNodes.add(c);
        dataNodes.add(d);
    }

    public void handleMouse(){
        if(mousePressed && mouseButton == LEFT){
            this.dataNodes.add(new PVector(mouseX,mouseY));
            this.neuralGas.setDataNodes(this.dataNodes);
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}

