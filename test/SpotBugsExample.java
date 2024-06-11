import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpotBugsExample {

    /**
     * Returns the value for the specified key from the map.
     * If the key is not present, returns a default value.
     *
     * @param exp the key whose associated value is to be returned
     * @return the value associated with the specified key, or "default" if the key is not present
     */
    public static int sum(String exp) {
        int sum = 0;
        for (String s : exp.split(" ")) {
            sum += Integer.parseInt(s);
        }
        return sum;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("hello", "world");

        // This will cause a NullPointerException if the key is not found in the map
        System.out.println(sum("missingKey")); // This will throw an exception
    }
}
