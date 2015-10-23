import javax.swing.*;

public class HelloApplet extends JApplet
{

    public static void main(String[] args)
    {

    }

    @Override
    public void init()
    {
        JLabel label = new JLabel("Hello Applet", JLabel.CENTER);
        add(label);
    }

}
