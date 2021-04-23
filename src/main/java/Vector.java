import processing.core.PVector;

public class Vector {

    PVector start;
    PVector end;

    public Vector(PVector start, PVector end) {
        this.start = start;
        this.end = end;
    }

    public PVector getStart() {
        return start;
    }

    public void setStart(PVector start) {
        this.start = start;
    }

    public PVector getEnd() {
        return end;
    }

    public void setEnd(PVector end) {
        this.end = end;
    }
}
