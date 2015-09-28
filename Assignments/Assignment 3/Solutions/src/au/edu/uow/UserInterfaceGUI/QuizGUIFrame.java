package au.edu.uow.UserInterfaceGUI;

import javax.swing.*;
import java.awt.*;

public class QuizGUIFrame extends JFrame
{

    private JPanel MainPanel = new JPanel();
    private JPanel QuestionPanel = new JPanel();
    private JPanel ChoicesPanel = new JPanel();

    public QuizGUIFrame(String windowName)
    {
        /*
         * Set up frame
         */
        // Set the name
        super(windowName);

        // Close operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size
        this.setSize(650, 450);

        // Set position on screen centre
        this.setLocationRelativeTo(null);

        // Add the toolbar to the frame
        this.addToolBar();


    }

    public void showQuestion()
    {
        // Clean
        this.cleanMainPanel();

        // New Layout
        GridLayout mainLayout = new GridLayout(2, 1);

        // Setup
        MainPanel.setLayout(mainLayout);

        /*
         * Question Panel
         */
        // QuestionPanel.setBorder(new EmptyBorder(20, -100, 0, 0));
        // Set Layout
        GridBagLayout questionLayout = new GridBagLayout();
        QuestionPanel.setLayout(questionLayout);

        // Add Content
        JLabel questionText = new JLabel("<html>test1" +
                "<br>Sentsvvsg shshbs " +
                "bsbsbhs bshb sbhsb sbh</html>");
        QuestionPanel.setBackground(Color.WHITE);
        QuestionPanel.add(questionText);


        /*
         * Choice Panel
         */
        // Submit Button
        JButton submit = new JButton("Continue");

        // Radio Buttons
        JRadioButton test1 = new JRadioButton("Test 1");
        JRadioButton test2 = new JRadioButton("Test 2");

        // Group them
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(test1);
        buttonGroup.add(test2);

        // Add
        ChoicesPanel.add(test1);
        ChoicesPanel.add(test2);

        ChoicesPanel.add(submit);

        /*
         * Add to Main Panel
         */
        MainPanel.add(QuestionPanel);
        MainPanel.add(ChoicesPanel);

        // Add to frame
        this.add(MainPanel);
    }

    public void addWelcomeScreen()
    {
        // Clean
        this.cleanMainPanel();

        JLabel welcomeText = new JLabel(
                "<html><center>" +
                "<p style='color:blue;font-weight:bold;font-size:24px;'>Java Quiz</p><br>" +
                "<p>Created By:</p>" +
                "<p>James Marino</p>" +
                "</center></html>");

        MainPanel.add(welcomeText);
        MainPanel.setBorder(BorderFactory.createEmptyBorder(100, 10, 10, 10));

        this.add(MainPanel);
    }

    private void cleanMainPanel()
    {
        // Remove the panel
        this.remove(MainPanel);

        // Create a fresh instance
        MainPanel = new JPanel();
    }

    public void showFrame()
    {
        // Show the frame
        this.setVisible(true);
    }

    private void addToolBar()
    {
        // Create elements
        JToolBar toolBar = new JToolBar();
        // Sub elements
        JButton scoreButton = new JButton("Score");
        JButton exitButton = new JButton("Exit");
        JLabel nameLabel = new JLabel("Your Name: ");
        JTextField nameField = new JTextField();
        JButton registerButton = new JButton("Register");

        // Toolbar properties
        toolBar.setFloatable(false);

        // Add Elements to toolbar
        toolBar.add(scoreButton);
        toolBar.add(Box.createRigidArea(new Dimension(10, 0)));
        toolBar.add(exitButton);
        toolBar.add(Box.createRigidArea(new Dimension(40, 0)));
        toolBar.add(nameLabel);
        toolBar.add(nameField);
        toolBar.add(registerButton);
        toolBar.add(Box.createRigidArea(new Dimension(40, 0)));


        // Add to frame
        this.add(toolBar, BorderLayout.NORTH);
    }
}
