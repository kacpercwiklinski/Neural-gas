import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node extends PVector{

    float error;

    public Node(PVector vector) {
        this.error = 0.0f;
    }

    public Node(float x, float y) {
        super(x, y);
        this.error = 0.0f;
    }

    public void increaseError(float value){
        this.error += value;
    }

    public void decreaseError(){
        this.error *= Main.ALFA;
    }

    public void decreaseError(float value){
        this.error -= value;
    }

    public float getError() {
        return error;
    }

    public void setError(float error) {
        this.error = error;
    }
}
