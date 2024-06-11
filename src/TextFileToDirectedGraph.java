import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * A utility class for converting text input into a directed graph.
 */
public class TextFileToDirectedGraph {

    /**
     * Generates a directed graph from the given text input.
     * The text input is expected to contain words separated by spaces or commas.
     * Each pair of consecutive words will be treated as a directed edge in the graph.
     *
     * @param text the text input to convert into a directed graph
     * @return a DirectedGraph object representing the directed graph
     */
    public DirectedGraph generateGraph(String text) {
        DirectedGraph graph = new DirectedGraph();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(text));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split("\\s+|,");
                for (int i = 0; i < words.length - 1; i++) {
                    String source = words[i].replaceAll("[^a-zA-Z]", "");
                    String destination = words[i + 1].replaceAll("[^a-zA-Z]", "");
                    if (!source.isEmpty() && !destination.isEmpty()) {
                        graph.addEdge(source, destination);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
