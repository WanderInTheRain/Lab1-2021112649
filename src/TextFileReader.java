import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextFileReader {

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
     * 从指定路径读取文件内容并返回字符串
     *
     * @param filePath 文件路径
     * @return 文件内容字符串
     * @throws IOException 如果读取文件时发生错误
     */
    public static String read(String filePath) throws IOException {
        // 创建File对象
        File file = new File(filePath);

        // 使用StringBuilder来累加文件内容
        StringBuilder fileContent = new StringBuilder();

        // 创建FileReader和BufferedReader对象
        try (FileReader fileReader = new FileReader(file);
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
