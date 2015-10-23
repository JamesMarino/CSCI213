import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class SunriseApplet extends JApplet
{
    private int x = 0;
    private int y = 0;
    private int yIncrement = 0;
    private Ellipse2D circle = new Ellipse2D.Double();

    public static void main(String[] args)
    {

    }

    @Override
    public void init()
    {
        x = Integer.parseInt(getParameter("x"));
        y = Integer.parseInt(getParameter("y"));

        ActionListener updateMove = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Add increment
                circle.setFrame(x, y - yIncrement, 20, 20);

                // Repaint the frame
                repaint();

                // Go up
                yIncrement++;
            }
        };

        new Timer(5, updateMove).start();

    }

    @Override
    public void paint(Graphics graphics)
    {
        Image image = getImage(getCodeBase(), "SydneyOperaHouse.jpg");
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics.drawImage(image, 0, 0, 1024, 677, this);

        graphics2D.setPaint(Color.red);
        graphics2D.fill(circle);
        graphics2D.draw(circle);
    }
}
