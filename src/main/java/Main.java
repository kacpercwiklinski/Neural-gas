import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends PApplet {
    static ContextMenu menu;

    // BASIC SETTINGS
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    public static final int BACKGROUND_COLOR = 51;

    // Gas settings
    public static float ALFA = 0.5f;
    public static float BETA = 0.0005f;

    List<Node> dataNodes;
    NeuralGas neuralGas;



    public void settings() {
        size(WIDTH, HEIGHT);
        dataNodes = new ArrayList<>();

        menu = new ContextMenu();

        neuralGas = new NeuralGas(this, this.dataNodes);
        neuralGas.initializeNeuralGas();

        menu.showMenu();
        if (menu.SUBMITTED == Boolean.FALSE){
            System.exit(0);
        }
        initializeData();
    }

    public void draw() {
        background(BACKGROUND_COLOR);

        dataNodes.forEach(node -> {
            stroke(255,255,255);
            strokeWeight(10f);
            point(node.getVector().x,node.getVector().y);
        });

        ////Test
//        System.out.println(menu.LAMBDA);
        ///Test end
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

