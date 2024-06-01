import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageDisplay extends JFrame {
    private JLabel label;

    public ImageDisplay() {
        setTitle("Image Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        label = new JLabel();
        add(label);

        // Load and display the image
        try {
            BufferedImage img = ImageIO.read(new File("resource/graph1.png"));
            ImageIcon icon = new ImageIcon(img);
            label.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageDisplay());
    }
}
