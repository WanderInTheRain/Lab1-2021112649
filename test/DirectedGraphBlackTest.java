import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class DirectedGraphBlackTest {

    @Test
    public void testSourceNodeIsNull() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        List<String> result = graph.getBridgeWords(null, "C");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testDestinationNodeIsNull() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        List<String> result = graph.getBridgeWords("A", null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testBothNodesAreNull() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        List<String> result = graph.getBridgeWords(null, null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGraphDoesNotContainSourceNode() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("B");
        graph.addNode("C");

        List<String> result = graph.getBridgeWords("A", "C");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGraphDoesNotContainDestinationNode() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("A");
        graph.addNode("B");

        List<String> result = graph.getBridgeWords("A", "C");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGraphDoesNotContainBothNodes() {
        DirectedGraph graph = new DirectedGraph();

        List<String> result = graph.getBridgeWords("A", "C");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGraphContainsNodesButNoEdges() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        List<String> result = graph.getBridgeWords("A", "C");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGraphContainsNodesWithNoBridgeWords() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("C", "A");

        List<String> result = graph.getBridgeWords("A", "C");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGraphContainsNodesWithBridgeWords() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        List<String> expected = Arrays.asList("B");
        List<String> result = graph.getBridgeWords("A", "C");
        assertEquals(expected, result);
    }

    @Test
    public void testComplexGraphWithMultipleNodesAndEdges() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        List<String> expected = Arrays.asList("B");
        List<String> result = graph.getBridgeWords("A", "C");
        assertEquals(expected, result);

        expected = Arrays.asList("C");
        result = graph.getBridgeWords("B", "D");
        assertEquals(expected, result);
    }
}
