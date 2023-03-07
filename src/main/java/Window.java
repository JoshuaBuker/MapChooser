import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import java.util.ArrayList;
import java.awt.image.BufferedImage;


public class Window  {

    private JFrame frame;
    private ImageIcon map;
    private BufferedImage robot;
    private BufferedImage cone;
    private BufferedImage cube;
    private BufferedImage balance;

    Popup popup;
    JFrame popupFrame;
    JRotatingComponent popupImg;
    JSlider popupSlider;
    private JButton buttonConfirm;

    private JLabel myLabel;
    private JButton buttonCancel;
    private JButton buttonStart;
    private JButton buttonAngle;
    private JComboBox<String> eventMenu;

    private final double fieldWidth = 16.48969;
    private final double fieldHeight = 8.13816;


    private final int labelWidth = 1047;
    private final int labelHeight = 516;

    private int currentAngle;
    private int previousAngle;

    private final double xScalingFactor = fieldWidth / labelWidth;
    private final double yScalingFactor = fieldHeight / labelHeight;

    private boolean pathMode = false;

    ArrayList<String> arr = new ArrayList<>();

    public Window(){
        
        try {
            map = new ImageIcon(ImageIO.read(new File("Resources\\MAP_IMAGE.png")));
            robot = ImageIO.read(new File("Resources\\ROBOT.png"));
            cone = ImageIO.read(new File("Resources\\CONE.png"));
            cube = ImageIO.read(new File("Resources\\CUBE.png"));
            balance = ImageIO.read(new File("Resources\\BALANCE.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
           new Exception(e);
        }

        // ========== Create Popup Window =============
        currentAngle = 0;
        popupFrame = new JFrame();
        popupSlider = new JSlider(0, 360, 0);
        popupImg = new JRotatingComponent(popupSlider, robot);

        buttonConfirm = new JButton("Finish");
        buttonConfirm.setBounds(100, 365, 200, 50);
        buttonConfirm.setBackground(Color.GREEN);
        buttonConfirm.setForeground(Color.BLACK);
        buttonConfirm.setFocusable(false);

        popupImg.setBounds(75, 25, 250, 250);

        popupSlider.setBounds(20, 300, 350, 50);
        popupSlider.setPaintTrack(true);
        popupSlider.setPaintTicks(true);
        popupSlider.setPaintLabels(true);
        popupSlider.setMajorTickSpacing(45);
        popupSlider.setMinorTickSpacing(5);
        popupSlider.setSnapToTicks(true);


        popupFrame.add(popupImg);
        popupFrame.add(popupSlider);
        popupFrame.add(buttonConfirm);

        popupImg.setVisible(true);

        popupFrame.setSize(400, 475);
        popupFrame.setResizable(false);
        popupFrame.setLayout(null);
        popupFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        popupFrame.setLocationRelativeTo(null);
        popupFrame.setVisible(false);


        popupSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                popupImg.refresh();
            }
            
        });




        // ========== Populate Event Menu Array ==============
        String[] eventMenuOptions = {
            "No Event",
            "Pickup Cone",
            "Pickup Cube",
            "Balance"
        };

        myLabel = new JLabel(map);
        myLabel.setSize(labelWidth, labelHeight);

        buttonStart = new JButton("Start Path Mode");
        buttonStart.setBounds(300, 520, 200, 50);
        buttonStart.setBackground(Color.GREEN);
        buttonStart.setForeground(Color.BLACK);
        buttonStart.setFocusable(false);

        buttonAngle = new JButton("Set Angle");
        buttonAngle.setBounds(800, 520, 200, 50);
        buttonAngle.setBackground(Color.ORANGE);
        buttonAngle.setForeground(Color.BLACK);
        buttonAngle.setFocusable(false);

        buttonCancel = new JButton("Cancel Current Path");
        buttonCancel.setBounds(550, 520, 200, 50);
        buttonCancel.setBackground(Color.BLACK);
        buttonCancel.setForeground(Color.WHITE);
        buttonCancel.setFocusable(false);

        eventMenu = new JComboBox<String>(eventMenuOptions);
        eventMenu.setBounds(50, 520, 200, 50);
        buttonCancel.setFocusable(false);

        frame = new JFrame("FRC Map");
        frame.add(myLabel);
        frame.add(buttonStart);
        frame.add(buttonCancel);
        frame.add(buttonAngle);
        frame.add(eventMenu);

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

        buttonAngle.addActionListener(e -> {
            popupFrame.setVisible(true);
            previousAngle = popupSlider.getValue();
        });

        buttonConfirm.addActionListener(e -> {
            popupFrame.setVisible(false);
            currentAngle = popupSlider.getValue();
        });



        // ================== MOUSE CONTROLS ==========================

       myLabel.addMouseListener(new MouseInputListener() {

           @Override
           public void mouseClicked(java.awt.event.MouseEvent e) {
               Graphics g = myLabel.getGraphics();
               if (pathMode) {
                    if (eventMenu.getSelectedItem().equals("No Event")) {
                        g.setColor(Color.YELLOW);

                        arr.add(String.format("X: %f  Y: %f  Event: %s  Angle: %d", scaleX(e.getX()), scaleY(e.getY()), eventMenu.getSelectedItem(), currentAngle));
                        g.fillOval(e.getX() - 10, e.getY() - 10, 20,20);

                    } else if (eventMenu.getSelectedItem().equals("Pickup Cone")) {
                        drawEventIcon(cone, g, e);

                    } else if (eventMenu.getSelectedItem().equals("Pickup Cube")) {
                        drawEventIcon(cube, g, e);

                    } else if (eventMenu.getSelectedItem().equals("Balance")) {
                        drawEventIcon(balance, g, e);
                        
                    }
               } else {
                   System.out.printf("X: %f  Y: %f  Event: %s  Angle: %d \n", scaleX(e.getX()), scaleY(e.getY()), eventMenu.getSelectedItem(), currentAngle);
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

    public void drawEventIcon(BufferedImage img, Graphics g, java.awt.event.MouseEvent e) {
        arr.add(String.format("X: %f  Y: %f  Event: %s  Angle: %d", scaleX(e.getX()), scaleY(e.getY()), eventMenu.getSelectedItem(), currentAngle));
        g.setColor(Color.WHITE);
        g.fillOval(e.getX() - 17, e.getY() - 17, 34,34);
        g.drawImage(img, e.getX() - 15, e.getY() - 15, null);
        eventMenu.setSelectedIndex(0);
    }

}
