import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;

import java.util.Arrays;

public class ContextMenu {
    UiBooster booster = new UiBooster();
    Boolean SUBMITTED = Boolean.FALSE;
    String IMAGE_NAME;
    Float LAMBDA;
    Float E_B;
    Float E_N;
    Float A_MAX;
    Form form;

    public void showMenu() {
        this.form = booster
                .createForm("Neural Gas Settings")
                .addSelection(
                        "Choose test picture",
                        Main.imageFilesList
                ).setID("image_selection")
                .addSlider("Set Lambda", 1, 500, 300, 20, 5).setID("LAMBDA")
                .addSlider("Set E_B (Will by divided by 100)", 0, 100, 20, 20, 1).setID("E_B")
                .addSlider("Set E_N (Will by divided by 10000)", 0, 100, 6, 20, 1).setID("E_N")
                .addSlider("Set A_MAX", 1, 200, 50, 20, 5).setID("A_MAX")
                .addButton("Submit", () -> {
                            System.out.println("Close this window");
                        }
                ).setID("close")
                .setChangeListener((element, value, form) -> {
                    if (element.getId().equals("close")) {
                        submit(form);
                    }
                })
                .andWindow()
                .setSize(500, 700)
                .setUndecorated()
                .save()
                .show();

    }

    public void submit(Form form){
        this.SUBMITTED = Boolean.TRUE;
        this.IMAGE_NAME = form.getById("image_selection").asString();
        this.LAMBDA = form.getById("LAMBDA").asFloat();
        this.E_B = form.getById("E_B").asFloat()/100;
        this.E_N = form.getById("E_N").asFloat()/10000;
        this.A_MAX = form.getById("A_MAX").asFloat();
        // Print all results after submit
        form.getElements().forEach(e -> {
            System.out.println(e.getLabel() + " -> " + e.getValue());
        });
//        form.close();
    }
}

