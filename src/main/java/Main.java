import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends PApplet {
    ContextMenu menu;

    // BASIC SETTINGS
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    public static final int BACKGROUND_COLOR = 51;

    // Gas settings
    public static float LAMBDA = 300;
    public static float E_B = 0.05f;
    public static float E_N = 0.0006f;
    public static float ALFA = 0.5f;
    public static float BETA = 0.0005f;
    public static int A_MAX = 50;

    List<Node> dataNodes;
    NeuralGas neuralGas;



    public void settings() {
        size(WIDTH, HEIGHT);
        uiBooster = new UiBooster();
        dataNodes = new ArrayList<>();

        menu = new ContextMenu();

        neuralGas = new NeuralGas(this, this.dataNodes);
        neuralGas.initializeNeuralGas();

        initializeData();
    }

    public void draw() {
        background(BACKGROUND_COLOR);

        dataNodes.forEach(node -> {
            stroke(255,255,255);
            strokeWeight(10f);
            point(node.getVector().x,node.getVector().y);
        });

        this.neuralGas.drawGas();

        this.neuralGas.updateGas();

        handleMouse();
    }

    public void initializeData(){
        dataNodes = new ArrayList<>();
        Node a = new Node(new PVector(Main.WIDTH / 4, Main.HEIGHT / 4), this.neuralGas.getEdges());
        Node b = new Node(new PVector(Main.WIDTH / 4 * 3, Main.HEIGHT / 4), this.neuralGas.getEdges());
        Node c = new Node(new PVector(Main.WIDTH / 4 * 3, Main.HEIGHT / 4 * 3), this.neuralGas.getEdges());
        Node d = new Node(new PVector(Main.WIDTH / 4, Main.HEIGHT / 4 * 3), this.neuralGas.getEdges());
        dataNodes.add(a);
        dataNodes.add(b);
        dataNodes.add(c);
        dataNodes.add(d);
    }

    public void handleMouse(){
        if(mousePressed && mouseButton == RIGHT) {
            menu.showMenu();
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}

