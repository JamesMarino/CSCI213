import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

abstract public class InteractiveFrame
{

    public static void main(String[] args)
    {
        final JFrame frame = new JFrame("Interactive Frame");

        // Setup Frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(400, 200);
        frame.setSize(500, 400);

        // Mouse Events
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String result = "You clicked at " + e.getX() + "x" + e.getY() + " in the frame.";
                JOptionPane.showMessageDialog(frame, result);

            }
        });

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        // Add Menu Bar
        frame.setJMenuBar(menuBar);

        // Menu
        JMenu fileMenu = new JMenu("Menu");
        // Add Menu
        menuBar.add(fileMenu);

        // Menu Items
        JMenuItem menuItem1 = new JMenuItem("A Menu Item"), menuItem2 = new JMenuItem("Exit");
        // Attach to menu
        fileMenu.add(menuItem1);
        fileMenu.add(menuItem2);

        // Event handling
        menuItem1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane.showMessageDialog(frame, "You Selected a Menu Item");
            }
        });

        menuItem2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(JFrame.NORMAL);
            }
        });

        // Set frame visible
        frame.setVisible(true);
    }

}
