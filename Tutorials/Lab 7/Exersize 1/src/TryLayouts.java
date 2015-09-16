import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TryLayouts extends JFrame
{
    public static void main(String[] args) {
        JFrame frame = new TryLayouts();
        frame.setVisible(true);
    }


    public TryLayouts()
    {
        super("Try Layouts");

        /*
         * Setup frame
         */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel colourPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        colourPanel.setLayout(new GridLayout(1, 3));

        /*
         * Labels
         */
        final JLabel redLabel = new JLabel("");
        redLabel.setOpaque(true);
        redLabel.setBackground(Color.WHITE);

        final JLabel greenLabel = new JLabel("");
        greenLabel.setOpaque(true);
        greenLabel.setBackground(Color.WHITE);

        final JLabel blueLabel = new JLabel("");
        blueLabel.setOpaque(true);
        blueLabel.setBackground(Color.WHITE);

        // Attach to window
        colourPanel.add(redLabel);
        colourPanel.add(greenLabel);
        colourPanel.add(blueLabel);

        /*
         * Buttons
         */
        JButton redButton = new JButton("Red");
        redButton.setBackground(Color.RED);

        JButton blueButton = new JButton("Blue");
        blueButton.setBackground(Color.BLUE);

        JButton greenButton = new JButton("Green");
        blueButton.setBackground(Color.GREEN);

        buttonPanel.add(redButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(greenButton);

        add(colourPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        /*
         * Mouse listeners
         */
        redButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                redLabel.setBackground(Color.RED);
            }
        });

        greenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                greenLabel.setBackground(Color.GREEN);
            }
        });

        blueButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                blueLabel.setBackground(Color.BLUE);
            }
        });

    }
}
