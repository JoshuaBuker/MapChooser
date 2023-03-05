import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JRotatingPanel extends JPanel {

    JSlider slide;
    BufferedImage img;

    public JRotatingPanel(JSlider slide, BufferedImage img) {
        this.slide = slide;
        this.img = img;

        refresh();
    }


    @Override
    public void paintComponents(Graphics g) {
        Graphics2D g2d = img.createGraphics();
        g2d.rotate(Math.toRadians(slide.getValue()));
        g2d.drawImage(img, 0, 0, null);
    }

    public void refresh() {
        repaint();
    }
}
