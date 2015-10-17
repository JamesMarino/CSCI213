package au.edu.uow.ClientGUI;

import au.edu.uow.Networking.ServerHandler;
import au.edu.uow.QuestionLibrary.Question;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class QuizClientGUIFrame extends JFrame
{

    // Panels
    private JPanel MainPanel = new JPanel();

    // Labels
    private JLabel WelcomeNameLabel = new JLabel("");

    // Menus
    JMenuBar  MenuBar = new JMenuBar();

    // Buttons
    private JButton NextQuestionButton = new JButton("Next");
    private List CurrentRadioButtons = new ArrayList<JRadioButton>();

    // Data
    private Student CurrentStudent = new Student();
    private List<Question> QuizData;
    private ServerHandler serverHandler = new ServerHandler();

    private static int QuestionPosition = 0;
    private static int TotalQuestions = 0;

    private static final String SUCCESS = "OK";

    /**
     *
     */
    public void setup()
    {

        // Receive
        String registerResponse;
        List<Question> question;

        registerResponse = serverHandler.register("James");
        question = serverHandler.getQuestion();

        // Close connection
        serverHandler.close();
    }

    public boolean registerNameServer(String name)
    {
        String response = serverHandler.register(name);

        return response.equals(SUCCESS);
    }

    public QuizClientGUIFrame(String windowName)
    {

        /*
         * Set up frame
         */
        // Set the name
        super(windowName);

        // Close operation
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set the size
        this.setSize(600, 500);

        // Set position on screen centre
        this.setLocationRelativeTo(null);

        // Add the Menu Bar to the frame
        this.addMenu();

        /*
         * Setup Student
         */
        QuizData = serverHandler.getQuestion();

        // Check for error
        if (QuizData != null) {
            TotalQuestions = QuizData.size();

            // Set event listener - next question
            NextQuestionButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    // Show another question
                    if (QuestionPosition < TotalQuestions) {

                        // Get the last Current Quiz Question
                        Question currentQuestion = QuizData.get(QuestionPosition - 1);

                        for (int i = 0; i < CurrentRadioButtons.size(); i++) {

                            // Get the selected response
                            JRadioButton currentRadioButton = (JRadioButton) CurrentRadioButtons.get(i);

                            if (currentRadioButton.isSelected()) {

                                // Check if current question answered is correct
                                // + 1 to counteract for 1 based answer values
                                if (currentQuestion.compareAnswer(i + 1)) {
                                    // Add a point
                                    CurrentStudent.recordScore(true);
                                }

                            }

                        }

                        // Go to next question
                        showQuestion();
                        showFrame();

                    } else {

                        // Show marking
                        showMarksResult();
                        showFrame();

                    }

                }
            });

            // Add listener for Window Closing
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {

                    // Close Connection
                    serverHandler.close();
                }
            });
        } else {
            // Dialogue telling user there is error
            JOptionPane.showMessageDialog(MainPanel.getParent(),
                    "Could not get questions from server.\nWill close now.",
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);

            // Quit on OK
            System.exit(0);
        }

    }

    /**
     * Update and show the Main Frame
     */
    public void showFrame()
    {
        // Show the frame
        this.setVisible(true);
    }

    /**
     * GUI display marks result
     */
    public void showMarksResult()
    {
        // Clean Panel
        this.cleanMainPanel();

        // New Panel
        BoxLayout boxLayout = new BoxLayout(MainPanel, BoxLayout.Y_AXIS);
        MainPanel.setLayout(boxLayout);
        MainPanel.setBackground(new Color(57, 154, 153));

        // Result Label
        JLabel resultLabel = new JLabel("<html><center><h1 style='color:white;'>" +
                "Final Score</h1>" +
                "<h2>" + CurrentStudent.getScore() + " out of " + TotalQuestions +
                "</h2></center></h1></html>", JLabel.CENTER);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        MainPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        MainPanel.add(resultLabel);

        this.add(MainPanel);

    }

    /**
     * GUI Display a new question
     */
    public void showQuestion()
    {
        // Panels
        JPanel QuestionPanel = new JPanel();
        JPanel ChoicesPanel = new JPanel();

        // Current Question
        Question question = QuizData.get(QuestionPosition);

        // Clean
        this.cleanMainPanel();

        // Remove label from panel
        QuestionPanel.removeAll();

        // New Layout
        GridLayout mainLayout = new GridLayout(3, 1);

        // Setup
        MainPanel.setLayout(mainLayout);

        /*
         * Question Panel
         */
        // Set Layout
        GridBagLayout questionLayout = new GridBagLayout();
        QuestionPanel.setLayout(questionLayout);

        // Add Content
        String finalQuestion = "<html>";
        for (String current : question.getQuestion()) {
            finalQuestion += current;
        }
        finalQuestion += "</html>";

        JLabel questionText = new JLabel(finalQuestion);

        QuestionPanel.setBackground(Color.WHITE);
        QuestionPanel.add(questionText);

        /*
         * Choice Panel
         */

        // Remove Elements from choices panel
        ChoicesPanel.removeAll();

        // New Radio Buttons
        CurrentRadioButtons = new ArrayList<JRadioButton>();
        for (int i = 0; i < question.getChoices().size(); i++) {
            CurrentRadioButtons.add(new JRadioButton(question.getChoices().get(i)));
        }

        // Group them
        ButtonGroup buttonGroup = new ButtonGroup();

        for (Object current : CurrentRadioButtons) {
            buttonGroup.add((JRadioButton) current);
        }

        // Setup Layout
        BoxLayout buttonLayout = new BoxLayout(ChoicesPanel, BoxLayout.Y_AXIS);
        ChoicesPanel.setLayout(buttonLayout);
        NextQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel radioButtonsPanel = new JPanel();
        BoxLayout radioButtonsLayout = new BoxLayout(radioButtonsPanel, BoxLayout.Y_AXIS);
        radioButtonsPanel.setLayout(radioButtonsLayout);

        // Add to Panel
        for (Object current : CurrentRadioButtons) {
            radioButtonsPanel.add((JRadioButton) current);
        }

        radioButtonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ChoicesPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ChoicesPanel.add(radioButtonsPanel);

        // --- Add Submit Button ---
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new GridBagLayout());

        GridBagConstraints padding = new GridBagConstraints();
        padding.anchor = GridBagConstraints.PAGE_END;
        padding.insets = new Insets(10,0,0,0);

        // Check if it's the last question, if so change the button name
        if ((QuestionPosition + 1) == TotalQuestions) {
            NextQuestionButton.setText("Get Marks");
        }

        submitPanel.add(NextQuestionButton, padding);

        /*
         * Add to Main Panel
         */
        MainPanel.add(QuestionPanel);
        MainPanel.add(ChoicesPanel);
        MainPanel.add(submitPanel);

        // Add to frame
        this.add(MainPanel);

        // Go to next question
        QuestionPosition++;

    }

    /**
     * Show welcome screen
     */
    public void addWelcomeScreen()
    {
        // Clean
        this.cleanMainPanel();

        /*
         * Status Bar
         */
        JPanel statusBar = new JPanel();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel connectionStatus = new JLabel("Disconnected");
        connectionStatus.setFont(new Font("Default", Font.PLAIN, 12));

        statusBar.add(connectionStatus);
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(statusBar, BorderLayout.SOUTH);

        /*
         * Main Content
         */
        BoxLayout layout = new BoxLayout(MainPanel, BoxLayout.PAGE_AXIS);
        MainPanel.setLayout(layout);

        WelcomeNameLabel.setText(
                "<html><body><center>" +
                        "<h1 style='color:white;'>Java Quiz Client</h1><br>" +
                        "<h2>Created By James Marino</h2>" +
                        "<h2>for</h2>" +
                        "<h2>CSCI213 Assignment 4</h2>" +
                        "</center></body></html>");
        MainPanel.setBackground(new Color(57, 154, 153));
        WelcomeNameLabel.setPreferredSize(new Dimension(0, 400));

        // Create elements
        JPanel registerPanel = new JPanel();
        // Sub elements
        JLabel nameLabel = new JLabel("Your Name: ");
        final JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 24));

        // Add Elements to toolbar
        registerPanel.add(nameLabel);
        registerPanel.add(nameField);

        // Add to frame
        MainPanel.add(WelcomeNameLabel);
        MainPanel.add(registerPanel);

        this.add(MainPanel);

        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!nameField.getText().equals("")) {

                    // Send to server
                    if (registerNameServer(nameField.getText())) {

                        // Set the name
                        CurrentStudent.setName(nameField.getText());


                        // Register Name with server
                        registerNameServer(nameField.getText());

                        // Go to the next frame
                        showQuestion();
                        showFrame();

                        // Disable Elements
                        nameField.setEnabled(false);

                    } else {
                        // Error Box
                        JOptionPane.showMessageDialog(MainPanel.getParent(),
                                "The server encountered an error registering name.",
                                "Register Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    // Error Box
                    JOptionPane.showMessageDialog(MainPanel.getParent(),
                            "Do not leave name blank.",
                            "Register Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    /**
     * Remove panels from Main Frame
     */
    private void cleanMainPanel()
    {
        // Remove the panel
        this.remove(MainPanel);

        // Create a fresh instance and reshow
        MainPanel = new JPanel();
        MainPanel.revalidate();
        MainPanel.repaint();
    }

    private void addMenu()
    {
        JMenu connectionMenu = new JMenu("Connection");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem connectMenuItem = new JMenuItem("Connect");
        JMenuItem disconnectMenuItem = new JMenuItem("Disconnect");
        JMenuItem serverMenuItem = new JMenuItem("Set Server");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        connectionMenu.add(connectMenuItem);
        connectionMenu.add(disconnectMenuItem);
        connectionMenu.addSeparator();
        connectionMenu.add(serverMenuItem);
        connectionMenu.addSeparator();
        connectionMenu.add(exitMenuItem);

        disconnectMenuItem.setEnabled(false);

        MenuBar.add(connectionMenu);
        MenuBar.add(helpMenu);

        this.setJMenuBar(MenuBar);

        /*
         * Listeners
         */
        connectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Item Clicked");
            }
        });

    }
}
