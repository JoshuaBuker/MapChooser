import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.Point;
    

public class Window  {

    private JFrame frame;
    private ImageIcon map;
    private JLabel myLabel;

    private Point point;

    public Window(){
        
        try {
            map = new ImageIcon(ImageIO.read(new File("img.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Could not find file");
        }
        myLabel = new JLabel(map);
        myLabel.setSize(1134, 580);

        frame = new JFrame("FRC Map");
        frame.add(myLabel);

       frame.addMouseListener(new MouseInputListener() {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            point = e.getPoint();
            System.out.println(String.format("X: %d    Y: %d", point.x, point.y));
            Program.xPub.set(point.getX());
            Program.yPub.set(point.getY());
            
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseDragged(java.awt.event.MouseEvent arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseMoved(java.awt.event.MouseEvent arg0) {
            // TODO Auto-generated method stub
            
        }
        
       });

        frame.setSize(1144, 590);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        

    }
    
}
