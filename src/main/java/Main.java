import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Arrays;
import java.util.List;

public class Main extends PApplet {

    // BASIC SETTINGS
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    public static final int BACKGROUND_COLOR = 51;

    Data data;
    NeuralGas neuralGas;
    UiBooster uiBooster;

    public void settings() {
        size(WIDTH, HEIGHT);
        uiBooster = new UiBooster();

        data = new Data(this);
        data.initializeData();

        neuralGas = new NeuralGas(this);
        neuralGas.initializeNeuralGas();
    }

    public void draw() {
        background(BACKGROUND_COLOR);
        this.data.draw();
        this.neuralGas.drawGas();

        handleMouse();
    }

    public void handleMouse(){
        if(mousePressed && mouseButton == RIGHT){
            Form form = uiBooster.createForm("Personal information")
                    .addText("Whats your first name?")
                    .addTextArea("Tell me something about you")
                    .addSelection(
                            "Whats your favorite movie?",
                            Arrays.asList("Pulp Fiction", "Bambi", "The Godfather", "Hangover"))
                    .addLabel("Choose an action")
                    .addButton("half full", () -> uiBooster.showInfoDialog("Optimist"))
                    .addButton("half empty", () -> uiBooster.showInfoDialog("Pessimist"))
                    .addSlider("How many liters did you drink today?", 0, 5, 1, 5, 1)
                    .show();
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}

