import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
    String name;
    List<Node> neighbors;
    public Map<Node, Integer> edgeValues;

    public Node(String name) {
        this.name = name;
        this.neighbors = new ArrayList<>();
        this.edgeValues = new HashMap<>();
    }

    public void addNeighbor(Node neighbor) {
        this.neighbors.add(neighbor);
        if (edgeValues.containsKey(neighbor)) {
            edgeValues.put(neighbor, edgeValues.get(neighbor) + 1);
        } else {
            edgeValues.put(neighbor, 1);
        }
    }

    public String getName() {
        return name;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }
}
