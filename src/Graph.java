import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private List<Node> nodes;
    private List<Arc> arcs;
    private Map<Integer, Node> nodeMap;

    public Graph(String filePath) throws Exception {
        nodes = new ArrayList<>();
        arcs = new ArrayList<>();
        nodeMap = new HashMap<>();
        loadFromXml(filePath);
    }

    private void loadFromXml(String filePath) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        saxParser.parse(new File(filePath), new DefaultHandler() {
            private Node currentNode;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                if (qName.equalsIgnoreCase("node")) {
                    int id = Integer.parseInt(attributes.getValue("id"));
                    int longitude = Integer.parseInt(attributes.getValue("longitude"));
                    int latitude = Integer.parseInt(attributes.getValue("latitude"));

                    if (!nodeMap.containsKey(id)) {
                        currentNode = new Node(id, longitude, latitude);
                        nodes.add(currentNode);
                        nodeMap.put(id, currentNode);
                    }
                } else if (qName.equalsIgnoreCase("arc")) {
                    int from = Integer.parseInt(attributes.getValue("from"));
                    int to = Integer.parseInt(attributes.getValue("to"));
                    int length = Integer.parseInt(attributes.getValue("length"));

                    Node startNode = nodeMap.get(from);
                    Node endNode = nodeMap.get(to);

                    if (startNode != null && endNode != null) {
                        arcs.add(new Arc(startNode, endNode, length));
                    } else {
                        System.err.println("Invalid arc definition: from=" + from + ", to=" + to);
                    }
                }
            }
        });
    }
    public List<Node> getNodes() {
        return nodes;
    }

    public List<Arc> getArcs() {
        return arcs;
    }
}