import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * A utility class for reading the content of a text file and printing it to the console.
 */
public class TextFileReader {

    /**
     * The main method that initiates the reading of a text file and prints its content.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // 请替换为你要读取的文件路径
        String filePath = "your_file_path.txt";

        // 调用read方法并输出结果
        try {
            String fileContent = read(filePath);
            System.out.println(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the content of a file from the specified path and returns it as a string.
     *
     * @param filePath the path to the file
     * @return the content of the file as a string
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static String read(String filePath) throws IOException {
        // 创建File对象
        File file = new File(filePath);

        // 使用StringBuilder来累加文件内容
        StringBuilder fileContent = new StringBuilder();

        // 创建FileReader和BufferedReader对象
        try (FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            // 读取文件直到文件结束
            while ((line = bufferedReader.readLine()) != null) {
                // 累加每一行内容并换行
                fileContent.append(line).append("\n");
            }
        }

        // 返回文件内容字符串
        return fileContent.toString();
    }
}
