import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.util.ArrayList;


public class Window  {

    private JFrame frame;
    private ImageIcon map;
    private JLabel myLabel;
    private JButton buttonCancel;
    private JButton buttonStart;

    private final double fieldWidth = 16.48969;
    private final double fieldHeight = 8.13816;


    private final int labelWidth = 1047;
    private final int labelHeight = 516;

    private final double xScalingFactor = fieldWidth / labelWidth;
    private final double yScalingFactor = fieldHeight / labelHeight;

    private boolean pathMode = false;

    ArrayList<String> arr = new ArrayList<>();

    public Window(){
        
        try {
            map = new ImageIcon(ImageIO.read(new File("img.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Could not find file");
        }
        myLabel = new JLabel(map);
        myLabel.setSize(labelWidth, labelHeight);

        buttonStart = new JButton("Start Path Mode");
        buttonStart.setBounds(300, 520, 200, 50);
        buttonStart.setBackground(Color.GREEN);
        buttonStart.setForeground(Color.BLACK);
        buttonStart.setFocusable(false);

        buttonCancel = new JButton("Cancel Current Path");
        buttonCancel.setBounds(550, 520, 200, 50);
        buttonCancel.setBackground(Color.BLACK);
        buttonCancel.setForeground(Color.WHITE);
        buttonCancel.setFocusable(false);

        frame = new JFrame("FRC Map");
        frame.add(myLabel);
        frame.add(buttonStart);
        frame.add(buttonCancel);

        // ================= BUTTON CONTROLS ===========================

        buttonStart.addActionListener(e -> {

            if (pathMode == false) {
                enablePathMode();
            } else if (pathMode) {

                for (String str:arr) {
                    System.out.println(str);
                }

                disablePathMode();
                arr.clear();

            }

        });

        buttonCancel.addActionListener(e -> {
            buttonStart.setBackground(Color.GREEN);
            buttonStart.setForeground(Color.BLACK);
            buttonStart.setText("Start Path Mode");
            myLabel.repaint();
            pathMode = false;
            arr.clear();
        });



        // ================== MOUSE CONTROLS ==========================

       myLabel.addMouseListener(new MouseInputListener() {

           @Override
           public void mouseClicked(java.awt.event.MouseEvent e) {
               Graphics g = myLabel.getGraphics();
               g.setColor(Color.YELLOW);
               if (pathMode) {

                   arr.add(String.format("X: %f  Y: %f", scaleX(e.getX()), scaleY(e.getY())));
                   g.fillOval(e.getX() - 10, e.getY() - 10, 20,20);


               } else {
                   System.out.printf("X:%f  Y:%f \n", scaleX(e.getX()), scaleY(e.getY()));
               }
           }

           public void mouseDragged(MouseEvent mouseEvent) {}
           public void mouseMoved(MouseEvent mouseEvent) {}
           public void mousePressed(MouseEvent mouseEvent) {}
           public void mouseReleased(MouseEvent mouseEvent) {}
           public void mouseEntered(MouseEvent mouseEvent) {}
           public void mouseExited(MouseEvent mouseEvent) {}


       });

        frame.setSize(labelWidth + 10, labelHeight + 100);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        

    }

    private void enablePathMode() {
        buttonStart.setBackground(Color.RED);
        buttonStart.setForeground(Color.WHITE);
        buttonStart.setText("Finish Path Mode");
        pathMode = true;
    }


    public double scaleX(double x) {
        return x * xScalingFactor;
    }

    public double scaleY(double y) {
        y = labelHeight - y;
        return y * yScalingFactor;
    }

    public void disablePathMode() {
        buttonStart.setBackground(Color.GREEN);
        buttonStart.setForeground(Color.BLACK);
        buttonStart.setText("Start Path Mode");
        myLabel.repaint();
        pathMode = false;
    }

}
