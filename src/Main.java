import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Graph graph = new Graph("src/hartaLuxembourg.xml");
            Dijkstra dijkstra = new Dijkstra(graph);

            JFrame frame = new JFrame("Graph Viewer");
            GraphPanel panel = new GraphPanel(graph.getNodes(), graph.getArcs());
            frame.setLayout(new BorderLayout());

            JButton button = new JButton("Find Shortest Path");
            button.addActionListener(e -> {
                Node selectedNode1 = panel.getSelectedNode1();
                Node selectedNode2 = panel.getSelectedNode2();

                if (selectedNode1 == null || selectedNode2 == null) {
                    JOptionPane.showMessageDialog(frame, "Please select two nodes first!");
                } else {
                    List<Arc> shortestPath = dijkstra.findShortestPath(selectedNode1, selectedNode2);

                    for (Arc arc : shortestPath) {
                        arc.setColor(Color.RED);
                    }

                    panel.repaint();

                }
            });

            frame.add(panel, BorderLayout.CENTER);
            frame.add(button, BorderLayout.SOUTH);

            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
