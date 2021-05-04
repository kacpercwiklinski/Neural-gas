import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;

import java.util.Arrays;

public class ContextMenu {
    UiBooster booster = new UiBooster();
    Boolean SUBMITTED = Boolean.FALSE;
    Float LAMBDA;
    Float E_B;
    Float E_N;
    Float A_MAX;
    Form form;

    public void showMenu() {
        this.form = booster
                .createForm("Neural Gas Settings")
                .addSlider("Set Lambda", 0, 300, 50, 20, 5).setID("LAMBDA")
                .addSlider("Set E_B", 0, 300, 50, 20, 5).setID("E_B")
                .addSlider("Set E_N", 0, 300, 50, 20, 5).setID("E_N")
                .addSlider("Set A_MAX", 0, 300, 50, 20, 5).setID("A_MAX")
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
        this.LAMBDA = form.getById("LAMBDA").asFloat();
        this.E_B = form.getById("E_B").asFloat();
        this.E_N = form.getById("E_N").asFloat();
        this.A_MAX = form.getById("A_MAX").asFloat();
        // Print all results after submit
        form.getElements().forEach(e -> {
            System.out.println(e.getLabel() + " -> " + e.getValue());
        });
        form.close();
    }
}

