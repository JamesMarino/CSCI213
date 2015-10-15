import javafx.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class MovingCircle
{
    public MovingCircle() {

        initialise();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MovingCircle();
            }
        });
    }

    private void initialise() {

        JFrame frame = new JFrame();
        JLayeredPane jLayeredPane = new JLayeredPane();

        frame.setLayout(new BorderLayout());
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        final DrawingClass drawingClass = new DrawingClass();

        JPanel testPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                drawingClass.draw(graphics2D, getWidth(), getHeight());

                ActionListener updateTask = new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        repaint();
                    }
                };

                new Timer(2, updateTask).start();

                Thread updateThread = new Thread() {
                    public void run() {
                        while (true) {
                            repaint();

                            try {
                                // Delay and give other thread a chance to run
                                Thread.sleep(1);
                            } catch (InterruptedException ignore) {}
                        }
                    }
                };

                updateThread.start();

            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 500);
            }

        };

        JPanel backgroundImage = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                try {

                    String urlName = "file:///Users/james/UOW/CSCI213 (Java)/Assignments/Assignment 3/Solutions/src/SydneyOperaHouse.jpg";
                    URL url = new URL(urlName);
                    Image image = ImageIO.read(url);

                    Graphics2D graphics2D = (Graphics2D) g;
                    graphics2D.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);

                } catch (Exception e) {
                    System.out.println("Error Reading Img");
                }

            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 500);
            }

        };

        /*
        frame.add(testPanel);
        frame.add(backgroundImage);
        */

        frame.add(testPanel);
        //frame.add(backgroundImage);

        // frame.add(new JLabel(new ImageIcon(URL)));
        //frame.add(testPanel);
        //frame.add(backgroundImage);

        frame.pack();
        frame.setVisible(true);
        frame.repaint();
    }
}

class DrawingClass {

    public void draw(Graphics2D graphics2D, int w, int h) {
        graphics2D.setColor(Color.RED);
        graphics2D.fillOval(h / 2 - 30, w / 2 - 30, 30, 30);
    }

    public void drawImage(Graphics2D graphics2D, int w, int h) {
        graphics2D.setColor(Color.RED);
        graphics2D.fillOval(h/2-60, w/2-60, 30, 30);
    }
}
