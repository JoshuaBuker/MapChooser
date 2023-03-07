import javax.swing.JSlider;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JRotatingComponent extends Component {

    JSlider slide;
    BufferedImage img;

    public JRotatingComponent(JSlider slide, BufferedImage img) {
        this.slide = slide;
        this.img = img;

        refresh();
    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.rotate(Math.toRadians(slide.getValue()), 125, 125);
        g2d.drawImage(img, 25, 25, null);
    }

    public void refresh() {
        repaint();
    }
}
