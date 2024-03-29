import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PImage;
import processing.core.PVector;

import javax.swing.filechooser.FileSystemView;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class Main extends PApplet {
    static ContextMenu menu;

    // BASIC SETTINGS
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    public static final int BACKGROUND_COLOR = 60;
    public static final Boolean DRAW_IMAGE = true; //Drawing input photo
    public static final Boolean DRAW_INPUT_NODES = false;

    public static final float ALFA = 0.5f;
    public static final float D = 0.995f;
    public int ITERATIONS;
    public boolean isReallyMousePressed;
    public static List<String> imageFilesList; //This will hold image names form settings()


    // Image settings
    PImage inputImage;
    List<PVector> dataNodes;
    NeuralGas neuralGas;

    public void settings() {
        size(WIDTH, HEIGHT);
        dataNodes = new ArrayList<>();
        menu = new ContextMenu();

        neuralGas = new NeuralGas(this);
        neuralGas.initializeNeuralGas();

        //Creating a File object for directory
        String filePath = String.join("",String.valueOf(System.getProperty("user.dir")),"\\src\\main\\resources");
        File directoryPath = new File(filePath);
        System.out.println(filePath);
        //Creating filter for jpg files
        FilenameFilter jpgFilefilter = new FilenameFilter(){
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".png")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        imageFilesList = new ArrayList<String>(Arrays.asList(directoryPath.list(jpgFilefilter)));
        System.out.println("List of the jpeg files in the specified directory:");
        for(String fileName : imageFilesList) {
            System.out.println(fileName);
        }
        menu.showMenu();
    }

    public void setup() {
        if (menu.SUBMITTED == Boolean.FALSE){
            System.exit(0);
        }else{
            initialize();
        }
    }

    public void initialize() {
        this.ITERATIONS = 0;
        neuralGas = new NeuralGas(this);
        neuralGas.initializeNeuralGas();
        inputImage = loadImage(menu.IMAGE_NAME);
        if (inputImage != null) {
            this.dataNodes = initializeData(inputImage);
        }
    }

    public void draw() {
        drawBackground(); //background has to be drawn each iteration
        neuralGasNextStep();
        handleMouse();
    }

    public void drawBackground(){
        //Based on DRAW_IMAGE variable either Image or background (black) i being drawn
        //If DRAW_INPUT_NODES == true calculated input nodes are being drawn
        if (DRAW_IMAGE)
            image(inputImage, 0, 0);
        else
            background(BACKGROUND_COLOR);

        if (DRAW_INPUT_NODES)
            this.dataNodes.forEach(node -> {
                stroke(255, 255, 255, 51f);
                strokeWeight(3f);
                point(node.x, node.y);
            });
    }


    public void neuralGasNextStep(){
        //SPEED_UP variable determines how many nodes calculations are skipped before drawing them.
        if (this.dataNodes.size() > 0){
            for (int i = 0; (i < menu.SPEED_UP) && (this.ITERATIONS <= menu.MAX_ITERATIONS); i++) {
                this.ITERATIONS = this.ITERATIONS + 1;
                this.neuralGas.nextIteration(this.dataNodes);
            }
        }
        System.out.println(this.ITERATIONS);
        this.neuralGas.drawGas();
    }

    public List<PVector> initializeData(PImage image) {
        List<PVector> list = new ArrayList<>();
        image.loadPixels();
        if (image.pixels != null) {
            for (int y = 0; y < image.height; y++) {
                for (int x = 0; x < image.width; x++) {
                    int pos = y * image.width + x;
                    float val = brightness(image.pixels[pos]);
                    if (val > 240.0f) { //Color threshold. If >240 looks for blacks, if ==0 gets only white
                        list.add(new PVector(x, y));
                    }
                }
            }
        }
        return list;
    }

    public void handleMouse(){

        if(mousePressed && mouseButton == RIGHT) {
            menu.showMenu();
            if (menu.SUBMITTED == Boolean.TRUE){
                initialize();
                menu.SUBMITTED = Boolean.FALSE;
            }
            mousePressed = false;
            mouseButton = 0;
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}

