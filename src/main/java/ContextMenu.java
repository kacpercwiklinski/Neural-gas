import de.milchreis.uibooster.UiBooster;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ContextMenu {
    @NotNull UiBooster uiBooster = new UiBooster();
    String textValue;
    public void showMenu() {
        System.out.println("as");
        uiBooster.createForm("Personal information")
                .addText("Whats your first name?").setID("name")
                .addTextArea("Tell me something about you")
                .addSelection(
                        "Whats your favorite movie?",
                        Arrays.asList("Pulp Fiction", "Bambi", "The Godfather", "Hangover"))
                .addLabel("Choose an action")
                .addButton("half full", () -> printDialog("Test"))
                .addButton("half empty", () -> uiBooster.showInfoDialog("Pessimist"))
                .addSlider("How many liters did you drink today?", 0, 5, 1, 5, 1)
                .show();
    }

    public void printDialog(String value){
        this.textValue = value;
        System.out.println(this.textValue);
    }
}

