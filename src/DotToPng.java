import java.io.IOException;

/**
 * A utility class for converting DOT files to PNG images using Graphviz.
 */
public class DotToPng {

    /**
     * Converts a DOT file to a PNG file using the Graphviz 'dot' command.
     *
     * @param dotFilePath the path to the input DOT file
     * @param pngFilePath the path to the output PNG file
     */
    public static void convert(String dotFilePath, String pngFilePath) {
        // 调用Graphviz库的dot命令将dot文件转换为png文件
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("tools/graphviz/bin/dot.exe",
                    "-Tpng", "-o", pngFilePath, dotFilePath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();
            System.out.println("转换完成，输出文件路径：" + pngFilePath);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method that defines the input DOT file and output PNG file paths,
     * and calls the convert method to perform the conversion.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // 定义输入的dot文件和输出的png文件路径
        String dotFilePath = "graph.dot";
        String pngFilePath = "output.png";

        convert(dotFilePath, pngFilePath);
    }
}
