import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.io.*;
import java.util.*;
public class TextFileToDirectedGraph {
    public DirectedGraph genarateGraph(String text) {
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
    };
}

