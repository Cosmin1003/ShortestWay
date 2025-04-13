import java.util.*;

public class Dijkstra {
    private Graph graph;
    private Map<Node, List<Arc>> adjacencyList;

    public Dijkstra(Graph graph) {
        this.graph = graph;
        this.adjacencyList = new HashMap<>();

        for (Arc arc : graph.getArcs()) {
            adjacencyList
                    .computeIfAbsent(arc.getStart(), k -> new ArrayList<>())
                    .add(arc);
        }
    }

    public List<Arc> findShortestPath(Node start, Node end) {
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> predecessors = new HashMap<>();
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>();

        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        priorityQueue.add(new NodeDistance(start, 0));

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll().getNode();

            if (currentNode.equals(end)) {
                break;
            }

            List<Arc> neighbors = adjacencyList.getOrDefault(currentNode, Collections.emptyList());
            for (Arc arc : neighbors) {
                Node neighbor = arc.getEnd();
                int newDistance = distances.get(currentNode) + arc.getLength();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    predecessors.put(neighbor, currentNode);
                    priorityQueue.add(new NodeDistance(neighbor, newDistance));
                }
            }
        }

        List<Arc> path = new ArrayList<>();
        Node step = end;

        while (predecessors.get(step) != null) {
            Node previous = predecessors.get(step);

            for (Arc arc : adjacencyList.get(previous)) {
                if (arc.getEnd().equals(step)) {
                    path.add(arc);
                    break;
                }
            }
            step = previous;
        }

        Collections.reverse(path);
        return path;
    }

    private static class NodeDistance implements Comparable<NodeDistance> {
        private Node node;
        private int distance;

        public NodeDistance(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        public Node getNode() {
            return node;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}
