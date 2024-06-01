import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ImagePanel extends JPanel {

    public void paint(Graphics g) {
        super.paint(g);
        ImageIcon icon = new ImageIcon("resource/graph1.png");
        g.drawImage(icon.getImage(), 0, 0, 400, 300, this);
    }
}