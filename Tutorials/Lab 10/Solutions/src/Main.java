import javax.swing.*;
import java.awt.*;

public class Main
{

    public Main() {
        initialise();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

    private void initialise() {

        JFrame frame = new JFrame();
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

            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 500);
            }

        };

        frame.add(testPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
