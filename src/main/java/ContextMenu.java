import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;

import java.util.Arrays;

public class ContextMenu {
    UiBooster booster = new UiBooster();
    Boolean SUBMITTED = Boolean.FALSE;
    String IMAGE_NAME;
    Float LAMBDA;
    int lambdaSet;
    Float E_B;
    int ebSet;
    Float E_N;
    int enSet;
    Float A_MAX;
    int aMaxSet;
    int SPEED_UP;
    int speedUPSet;
    int MAX_ITERATIONS;
    int maxIterationsSet;
    Form form;

    public void showMenu() {
        if (this.IMAGE_NAME != null){
            Main.imageFilesList.remove(this.IMAGE_NAME);
            Main.imageFilesList.add(0, this.IMAGE_NAME);
        }
        int lambdaInit = ((lambdaInit = this.lambdaSet) != 0) ? lambdaInit : 300;
        int ebInit = ((ebInit = this.ebSet) != 0) ? ebInit : 20;
        int enInit = ((enInit = this.enSet) != 0) ? enInit : 6;
        int aMaxInit = ((aMaxInit = this.aMaxSet) != 0) ? aMaxInit : 50;
        int speedUpInit = ((speedUpInit = this.speedUPSet) != 0) ? speedUpInit : 100;
        int maxIterationsInit = ((maxIterationsInit = this.maxIterationsSet) != 0) ? maxIterationsInit : 50000;

        this.form = booster
                .createForm("Neural Gas Settings")
//                .addMultipleSelection("Test", Boolean.TRUE, "Test", "test3", "Test5"
//                ).setID("multipleSelection")
                .addSelection(
                        "Choose test picture",
                        Main.imageFilesList
                ).setID("image_selection")
                .addSlider("Set Lambda", 1, 500, lambdaInit, 50, 5).setID("LAMBDA")
                .addSlider("Set E_B (Will by divided by 100)", 1, 100, ebInit, 20, 1).setID("E_B")
                .addSlider("Set E_N (Will by divided by 10000)", 1, 100, enInit, 20, 1).setID("E_N")
                .addSlider("Set A_MAX", 1, 200, aMaxInit, 20, 5).setID("A_MAX")
                .addSlider("Set Speed", 1, 200, speedUpInit, 20, 5).setID("SPEED_UP")
                .addSlider("Set Max Number Of Iterations", 1000, 100000, maxIterationsInit, 10000, 1000).setID("MAX_ITERATIONS")
                .addButton("Submit", () -> {
                            System.out.println("Close this window");
                        }
                ).setID("close")
                .setChangeListener((element, value, form) -> {
                    if (element.getId().equals("close")) {
                        submit(form);
                        form.close();
                    }
                })
                .andWindow()
                .setSize(500, 1000)
                .setPosition(1000,10)
                .setUndecorated()
                .save()
                .show();

    }

    public void submit(Form form){
        this.SUBMITTED = Boolean.TRUE;
        this.IMAGE_NAME = form.getById("image_selection").asString();
        this.lambdaSet = form.getById("LAMBDA").asInt();
        this.LAMBDA = Float.valueOf(this.lambdaSet);
        this.ebSet = form.getById("E_B").asInt();
        this.E_B = Float.valueOf(this.ebSet)/100;
        this.enSet = form.getById("E_N").asInt();
        this.E_N = Float.valueOf(this.enSet)/10000;
        this.aMaxSet = form.getById("A_MAX").asInt();
        this.A_MAX = Float.valueOf(this.aMaxSet);
        this.speedUPSet = form.getById("SPEED_UP").asInt();
        this.SPEED_UP = speedUPSet;
        this.maxIterationsSet = form.getById("MAX_ITERATIONS").asInt();
        this.MAX_ITERATIONS = maxIterationsSet;
        // Print all results after submit
        form.getElements().forEach(e -> {
            System.out.println(e.getLabel() + " -> " + e.getValue());
        });
        System.out.println(this.LAMBDA + " " +
                this.E_B + " "+
                this.E_N + " " +
                this.A_MAX);
    }
}