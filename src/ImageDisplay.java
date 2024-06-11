import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * A simple application to display an image using Swing.
 */
public class ImageDisplay extends JFrame {

  private JLabel label;

  /**
   * Constructs an ImageDisplay object and initializes the GUI.
   */
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

  /**
   * The main method to launch the application.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new ImageDisplay());
  }
}
