import java.io.IOException;

public class DotToPng {
    public static void convet(String dotFilePath, String pngFilePath) {
        // 调用Graphviz库的dot命令将dot文件转换为png文件
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("tools/graphviz/bin/dot.exe", "-Tpng", "-o", pngFilePath, dotFilePath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();
            System.out.println("转换完成，输出文件路径：" + pngFilePath);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        // 定义输入的dot文件和输出的png文件路径
        String dotFilePath = "graph.dot";
        String pngFilePath = "output.png";

        convet(dotFilePath,pngFilePath);
    }
}
