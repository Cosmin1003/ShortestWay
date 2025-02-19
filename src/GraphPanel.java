import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphPanel extends JPanel {
    private List<Node> nodes;
    private List<Arc> arcs;
    private int minLongitude;
    private int minLatitude;

    private Node selectedNode1 = null;
    private Node selectedNode2 = null;

    public GraphPanel(List<Node> nodes, List<Arc> arcs) {
        this.nodes = nodes;
        this.arcs = arcs;

        calculateMinCoordinates();

        addMouseListener(new MouseAdapter() {
            private int clickCount = 0;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickCount < 2) {
                    int x = e.getX();
                    int y = e.getY();
                    Node closestNode = findClosestNode(x, y);
                    if (clickCount == 0) {
                        selectedNode1 = closestNode;
                    } else {
                        selectedNode2 = closestNode;
                    }
                    clickCount++;
                    repaint();
                }
            }
        });
    }

    private Node findClosestNode(int x, int y) {
        Node closestNode = null;
        double minDistance = Double.MAX_VALUE;

        for (Node node : nodes) {
            int nodeX = (node.getLongitude() - minLongitude) / 100;
            int nodeY = (node.getLatitude() - minLatitude) / 100;

            double distance = Math.sqrt(Math.pow(x - nodeX, 2) + Math.pow(y - nodeY, 2));
            if (distance < minDistance) {
                minDistance = distance;
                closestNode = node;
            }
        }

        return closestNode;
    }

    private void calculateMinCoordinates() {
        minLongitude = Integer.MAX_VALUE;
        minLatitude = Integer.MAX_VALUE;

        for (Node node : nodes) {
            if (node.getLongitude() < minLongitude) {
                minLongitude = node.getLongitude();
            }
            if (node.getLatitude() < minLatitude) {
                minLatitude = node.getLatitude();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;



        g2d.setColor(Color.GREEN);
        for (Node node : nodes) {
            int x = (node.getLongitude() - minLongitude) / 100;
            int y = (node.getLatitude() - minLatitude) / 100;
            g2d.fillOval(x - 2, y - 2, 4, 4);
        }

        g2d.setColor(Color.MAGENTA);
        if (selectedNode1 != null) {
            int x = (selectedNode1.getLongitude() - minLongitude) / 100;
            int y = (selectedNode1.getLatitude() - minLatitude) / 100;
            g2d.fillOval(x - 4, y - 4, 8, 8);
        }
        if (selectedNode2 != null) {
            int x = (selectedNode2.getLongitude() - minLongitude) / 100;
            int y = (selectedNode2.getLatitude() - minLatitude) / 100;
            g2d.fillOval(x - 4, y - 4, 8, 8);
        }

        for (Arc arc : arcs) {
            Node start = arc.getStart();
            Node end = arc.getEnd();
            Color color = arc.getColor();
            g2d.setColor(color);
            g2d.drawLine(
                    (start.getLongitude() - minLongitude) / 100,
                    (start.getLatitude() - minLatitude) / 100,
                    (end.getLongitude() - minLongitude) / 100,
                    (end.getLatitude() - minLatitude) / 100
            );
        }
    }

    public Node getSelectedNode1() {
        return selectedNode1;
    }

    public Node getSelectedNode2() {
        return selectedNode2;
    }


}
