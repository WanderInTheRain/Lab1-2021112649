import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * This class represents a graphical user interface for interacting with a directed graph.
 * The GUI allows users to load a graph from a file, find bridge words, insert bridge words,
 * find the shortest path, and perform a random walk on the graph.
 */
public class Gui {

  private JPanel mainWindow;
  private JButton req1;
  private JButton shortestPathbutton;
  private JButton bridgeInsertbutton;
  private JButton getbrigebutton;
  private JButton walkbutton;
  private JTextArea textArea1;
  private JPanel showgraph;
  private JLabel label;
  DirectedGraph graph;
  boolean run = true;
  Random random = new Random();

  /**
   * Constructs a new GUI and sets up the action listeners for various buttons.
   */
  public Gui() {
    req1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e1) {
        String filePath = JOptionPane.showInputDialog("Enter file path:");
        String fileContent = "";
        try {
          fileContent = TextFileReader.read(filePath);
          System.out.println(fileContent);
        } catch (IOException e2) {
          e2.printStackTrace();
        }
        TextFileToDirectedGraph textToGraph = new TextFileToDirectedGraph();
        graph = textToGraph.generateGraph(fileContent);
        String dotpath = "resource/graph1.dot";
        String pngpath = "resource/graph1.png";
        try (BufferedWriter writer = new BufferedWriter(
            new FileWriter(dotpath, StandardCharsets.UTF_8))) {
          writer.write(graph.generateDot());
          System.out.println("DOT file 'graph.dot' has been generated successfully.");
        } catch (IOException e) {
          e.printStackTrace();
        }
        DotToPng.convert(dotpath, pngpath);
        showgraph.setVisible(true);
        try {
          BufferedImage img = ImageIO.read(new File(pngpath));
          ImageIcon icon = new ImageIcon(img);
          label.setIcon(icon);
        } catch (IOException e) {
          e.printStackTrace();
        }
        showgraph.repaint();
      }
    });

    getbrigebutton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String query = JOptionPane.showInputDialog("Enter 2 words, such as \"x,y\":");
        String[] tokens = query.split(",");
        if (tokens.length != 2) {
          JOptionPane.showMessageDialog(null,
              "Please enter exactly two words separated by a comma.");
          return;
        }

        String word1 = tokens[0].trim();
        String word2 = tokens[1].trim();

        // Check if both words are in the graph
        if (!graph.nodes.containsKey(word1) || !graph.nodes.containsKey(word2)) {
          textArea1.setText("No " + word1 + " or " + word2 + " in the graph!");
          return;
        }

        // Get bridge words
        List<String> bridgeWords = graph.getBridgeWords(word1, word2);

        if (bridgeWords.isEmpty()) {
          textArea1.setText("No bridge words from " + word1 + " to " + word2 + "!");
        } else {
          String bridgeWordText = bridgeWords.stream()
              .collect(Collectors.joining(", ", "The bridge words from "
                  + word1 + " to " + word2 + " are: ", "."));
          textArea1.setText(bridgeWordText);
        }
      }
    });

    bridgeInsertbutton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String word = JOptionPane.showInputDialog("Enter text to insert bridge:");
        String newword = graph.insertBridgeWords(word);
        textArea1.setText(newword);

        TextFileToDirectedGraph textToGraph = new TextFileToDirectedGraph();
        DirectedGraph graph2 = textToGraph.generateGraph(newword);
        String dotpath = "resource/graph3.dot";
        String pngpath = "resource/graph3.png";
        try (BufferedWriter writer = new BufferedWriter(
            new FileWriter(dotpath, StandardCharsets.UTF_8))) {
          writer.write(graph2.generateDot());
          System.out.println("DOT file 'graph.dot' has been generated successfully.");
        } catch (IOException e3) {
          e3.printStackTrace();
        }
        DotToPng.convert(dotpath, pngpath);
        showgraph.setVisible(true);
        try {
          BufferedImage img = ImageIO.read(new File(pngpath));
          ImageIcon icon = new ImageIcon(img);
          label.setIcon(icon);
        } catch (IOException e4) {
          e4.printStackTrace();
        }
        showgraph.repaint();
      }
    });

    shortestPathbutton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String query = JOptionPane.showInputDialog("Enter src and des, such as \"x,y\":");
        String[] tokens = query.split(",");
        if (tokens.length == 2) {
          Map<String, String> parentMap = graph.findShortestPath(tokens[0]);
          List<String> paths = graph.getPath(parentMap, tokens[0], tokens[1]);
          if (paths.isEmpty()) {
            textArea1.setText("unreachable from src and des");
            return;
          }
          String shortestPath = graph.printShortestPath(tokens[0], tokens[1], parentMap);
          textArea1.setText(shortestPath);
          String inputFilePath = "resource/graph1.dot";
          String outputFilePath = "resource/graph4.dot";
          try {
            graph.modifyDotFile(inputFilePath, outputFilePath, paths);
          } catch (IOException e12) {
            e12.printStackTrace();
          }
          String pngpath = "resource/graph4.png";
          DotToPng.convert(outputFilePath, pngpath);
          showgraph.setVisible(true);
          try {
            BufferedImage img = ImageIO.read(new File(pngpath));
            ImageIcon icon = new ImageIcon(img);
            label.setIcon(icon);
          } catch (IOException e34) {
            e34.printStackTrace();
          }
          showgraph.repaint();
        } else if (tokens.length == 1) {
          Map<String, String> parentMap = graph.findShortestPath(tokens[0]);
          List<String> allNodes = graph.getAllNodes();
          String ans = "";
          for (String node : allNodes) {
            List<String> paths = graph.getPath(parentMap, tokens[0], node);
            if (paths.isEmpty()) {
              textArea1.setText("unreachable from src and des");
              return;
            }
            String shortestPath = graph.printShortestPath(tokens[0], node, parentMap);
            ans += shortestPath;
            ans += "\n";
          }
          textArea1.setText(ans);
        }
      }
    });

    walkbutton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (walkbutton.getText().equals("RandomWalk")) {
          walkbutton.setText("Stop");
          run = true; // 开始随机游走
          new Thread(() -> {
            List<String> allNodes = graph.getAllNodes();
            if (allNodes.isEmpty()) {
              System.out.println("The graph is empty. No nodes to traverse.");
              return;
            }

            String startNode = allNodes.get(random.nextInt(allNodes.size()));
            System.out.println("Starting random walk from node: " + startNode);

            List<String> visitedNodes = new ArrayList<>();
            Set<String> visitedEdges = new HashSet<>();
            StringBuilder currentNode = new StringBuilder(startNode);

            while (run) {
              // 进行随机游走的逻辑
              if (visitedNodes.contains(currentNode.toString())) {
                run = false;
                break;
              }
              graph.randomWalk(visitedNodes, currentNode);
              try {
                TimeUnit.MILLISECONDS.sleep(500); // 0.5秒 = 500毫秒
              } catch (InterruptedException e2) {
                // 处理中断异常
              }
              List<Node> neighbors = graph.getNode(currentNode.toString()).getNeighbors();
              if (neighbors.isEmpty()) {
                System.out.println("No more outgoing edges from node: " + currentNode);
                run = false;
              } else {
                Node nextNode = neighbors.get(random.nextInt(neighbors.size()));
                String edge = currentNode + "->" + nextNode.getName();
                currentNode = new StringBuilder(nextNode.getName());
              }
            }
            String content = graph.printVisitedNodes(visitedNodes);
            textArea1.setText(content);
            try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("resource/out.txt", StandardCharsets.UTF_8))) {
              writer.write(content);
            } catch (IOException e23) {
              e23.printStackTrace();
            }
            walkbutton.setText("RandomWalk");
          }).start();
        } else {
          walkbutton.setText("RandomWalk");
          run = false;
        }
      }
    });
  }

  /**
   * The main method to start the GUI application.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("GUI");
      frame.setContentPane(new Gui().mainWindow);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 600);
      frame.setVisible(true);
    });
  }

}
