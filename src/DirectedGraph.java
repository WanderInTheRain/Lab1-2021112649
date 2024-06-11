import java.util.*;
import java.util.Random;
import java.io.*;

class DirectedGraph {
    Map<String, Node> nodes;

    public DirectedGraph() {
        this.nodes = new HashMap<>();
    }

    public void addNode(String source) {
        nodes.computeIfAbsent(source, Node::new);
    }

    public void addEdge(String source, String destination) {
        Node sourceNode = nodes.computeIfAbsent(source, Node::new);
        Node destinationNode = nodes.computeIfAbsent(destination, Node::new);
        sourceNode.addNeighbor(destinationNode);
    }

    public void printGraph() {
        for (Node node : nodes.values()) {
            System.out.print(node.getName() + " -> ");
            for (Node neighbor : node.getNeighbors()) {
                System.out.print(neighbor.getName() + ", ");
            }
            System.out.println();
        }
    }

    public List<String> getBridgeWords(String source, String destination) {
        List<String> bridgeWords = new ArrayList<>();
        Node sourceNode = nodes.get(source);
        Node destinationNode = nodes.get(destination);

        if (sourceNode != null && destinationNode != null) {
            List<Node> sourceNeighbors = sourceNode.getNeighbors();

            for (Node sourceNeighbor : sourceNeighbors) {
                List<Node> neighborNeighbors = sourceNeighbor.getNeighbors();
                for (Node neighborNeighbor : neighborNeighbors) {
                    if (neighborNeighbor == destinationNode) {
                        bridgeWords.add(sourceNeighbor.getName());
                    }
                }
            }
        }

        return bridgeWords;
    }

    public String generateDOT() {
        StringBuilder dotBuilder = new StringBuilder();
        dotBuilder.append("digraph G {\n");
        for (Node node : nodes.values()) {
            for (Map.Entry<Node, Integer> entry : node.edgeValues.entrySet()) {
                Node neighbor = entry.getKey();
                int weight = entry.getValue();
                dotBuilder.append("\t\"").append(node.getName()).append("\" -> \"").append(neighbor.getName()).append("\" [label=\"").append(weight).append("\"];\n");
            }
        }
        dotBuilder.append("}");
        return dotBuilder.toString();
    }

    public String insertBridgeWords(String inputText) {
        String[] words = inputText.split("\\s+|,");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            List<String> bridgeWords =  getBridgeWords(word1, word2);

            result.append(word1);
            if (!bridgeWords.isEmpty()) {
                // 插入第一个桥接词
                Random random = new Random();
                int randomIndex = random.nextInt(bridgeWords.size());

                // Insert the randomly selected bridge word into the result
                result.append(" ").append(bridgeWords.get(randomIndex));
            }
            result.append(" ");
        }
        // Append the last word
        result.append(words[words.length - 1]);

        return result.toString();
    }

    // 寻找从源节点到目标节点的最短路径长度
    public Map<String, String> findShortestPath(String source) {
        Map<String, String> parentMap = new HashMap<>(); // 存储节点的直接前驱节点，用于回溯路径
        Queue<String> queue = new LinkedList<>(); // 用于广度优先搜索

        parentMap.put(source, null); // 源节点的直接前驱节点为null
        queue.offer(source); // 将源节点加入队列

        while (!queue.isEmpty()) {
            String current = queue.poll(); // 出队列

            Node currentNode = getNode(current); // 获取当前节点
            if (currentNode != null) {
                for (Node neighbor : currentNode.getNeighbors()) { // 遍历当前节点的邻居节点
                    String neighborName = neighbor.getName();
                    if (!parentMap.containsKey(neighborName)) { // 如果邻居节点未被访问过
                        parentMap.put(neighborName, current); // 记录直接前驱节点
                        queue.offer(neighborName); // 入队列
                    }
                }
            }
        }

        return parentMap;
    }
    public static List<String> getPath(Map<String, String> parentMap, String src, String des) {
        LinkedList<String> path = new LinkedList<>();
        String currentNode = des;

        // Traverse from destination to source using the parentMap
        while (currentNode != null) {
            path.addFirst(currentNode);  // Add to the front of the list
            currentNode = parentMap.get(currentNode);
        }

        // Check if the first node in the path is the source node
        if (!path.isEmpty() && path.getFirst().equals(src)) {
            return path;
        } else {
            return Collections.emptyList();  // No valid path found
        }
    }
    public static void modifyDotFile(String inputFilePath, String outputFilePath, List<String> paths) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

        String line;
        while ((line = reader.readLine()) != null) {
            // Check if the line contains an edge definition
            if (line.contains("->")) {
                String[] parts = line.split("->");
                String from = parts[0].trim().replace("\"", "");
                String to = parts[1].split("\\[")[0].trim().replace("\"", "");

                // Check if the edge is in
                if (paths.contains(from) && paths.indexOf(from) + 1 < paths.size() && paths.get(paths.indexOf(from) + 1).equals(to)) {
                    line = line.replace("];", ", color=\"blue\"];");
                }
            }
            writer.write(line);
            writer.newLine();
        }

        reader.close();
        writer.close();
    }
    // 打印最短路径
    public String printShortestPath(String source, String destination, Map<String, String> parentMap) {
        List<String> path = new ArrayList<>();
        String current = destination;
        while (!current.equals(source)) {
            path.add(0, current);
            current = parentMap.get(current); // 回溯到父节点
        }
        path.add(0, source);

        String shortestPath = "Shortest path from " + source + " to " + destination + ":\n";
        for (String node : path) {
            shortestPath += node + " ";
        }
        shortestPath += "\n";
        shortestPath += "len is ";
        shortestPath += path.size() - 1;
        return shortestPath;
    }
    public void RandomWalk(List<String> visitedNodes, StringBuilder currentNode) {
        if (!visitedNodes.contains(currentNode.toString())) {
            visitedNodes.add(currentNode.toString());
        }
    }
    public String printVisitedNodes(List<String> visitedNodes) {
        String result = new String();
        for (String node : visitedNodes) {
            result += node;
            result += " ";
        }
        return result;
    }
    public List<String> getAllNodes() {
        return new ArrayList<>(nodes.keySet());
    }
    public Node getNode(String name) {
        return nodes.get(name);
    }

}
