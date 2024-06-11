import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class DirectedGraphWhiteTest {
    @Test
    public void testGetBridgeWords_sourceNodeNull() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("destination");
        List<String> result = graph.getBridgeWords(null, "destination");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBridgeWords_destinationNodeNull() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("source");
        List<String> result = graph.getBridgeWords("source", null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBridgeWords_bothNodesNull() {
        DirectedGraph graph = new DirectedGraph();
        List<String> result = graph.getBridgeWords(null, null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBridgeWords_path2() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("source");
        graph.addNode("destination");

        List<String> result = graph.getBridgeWords("source", "destination");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBridgeWords_path3() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("source");
        graph.addNode("neighbor1");
        graph.addNode("destination");

        graph.addEdge("source", "neighbor1");

        List<String> result = graph.getBridgeWords("source", "destination");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBridgeWords_path4() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("source");
        graph.addNode("neighbor1");
        graph.addNode("neighbor2");
        graph.addNode("destination");

        graph.addEdge("source", "neighbor1");
        graph.addEdge("neighbor1", "neighbor2");

        List<String> result = graph.getBridgeWords("source", "destination");
        assertTrue(result.isEmpty());
    }


    @Test
    public void testGetBridgeWords_path5() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("source");
        graph.addNode("neighbor");
        graph.addNode("destination");

        graph.addEdge("source", "neighbor");
        graph.addEdge("neighbor", "destination");

        List<String> result = graph.getBridgeWords("source", "destination");
        assertEquals(Collections.singletonList("neighbor"), result);
    }
}
