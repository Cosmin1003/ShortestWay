import java.awt.*;

public class Arc {
    private Node start;
    private Node end;
    private int length;
    private Color color = Color.BLACK;

    public Arc(Node start, Node end, int length) {
        this.start = start;
        this.end = end;
        this.length = length;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public int getLength() {
        return length;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
