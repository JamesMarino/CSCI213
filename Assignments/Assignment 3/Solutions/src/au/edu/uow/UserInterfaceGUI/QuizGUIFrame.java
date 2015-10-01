package au.edu.uow.UserInterfaceGUI;

import au.edu.uow.QuestionLibrary.Question;
import au.edu.uow.QuestionLibrary.QuestionLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * GUI Functionality
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */
public class QuizGUIFrame extends JFrame
{

    // Panels
    private JPanel MainPanel = new JPanel();

    // Labels
    private JLabel WelcomeNameLabel = new JLabel("");

    // Buttons
    private JButton NextQuestionButton = new JButton("Next");
    private List CurrentRadioButtons = new ArrayList<JRadioButton>();

    // Data
    private Student CurrentStudent = new Student();
    private List<Question> QuizData;

    private static int QuestionPosition = 0;
    private static int TotalQuestions = 0;

    /**
     * Constructor
     * @param windowName Name of window
     * @param properties Properties of App
     */
    public QuizGUIFrame(String windowName, Properties properties)
    {
        /*
         * Set up frame
         */
        // Set the name
        super(windowName);

        // Close operation
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set the size
        this.setSize(Integer.parseInt(properties.getProperty("SizeWidth")),
                Integer.parseInt(properties.getProperty("SizeHeight")));

        // Set position on screen centre
        this.setLocationRelativeTo(null);

        // Add the toolbar to the frame
        this.addToolBar();

        /*
         * Setup Student
         */
        CurrentStudent.setName("");

        /*
         * Setup Properties
         */
        TotalQuestions = Integer.parseInt(properties.getProperty("NumberOfQuestions"));

        /*
         * Generate Quiz
         */
        boolean questionList = QuestionLibrary.buildLibrary(properties.getProperty("QuestionFileName"));

        // Check for error generating quiz
        if (questionList) {

            QuizData = QuestionLibrary.makeQuiz(TotalQuestions);

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

        } else {

            // Dialogue telling user there is error
            JOptionPane.showMessageDialog(MainPanel.getParent(),
                    "Could not open Questions File.\nWill close now.",
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);

            // Quit on OK
            System.exit(0);
        }

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

        // Result Label
        JLabel resultLabel = new JLabel("Results of " + CurrentStudent.getName() + ": " + CurrentStudent.getScore() + " out of " + TotalQuestions);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Result Button
        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        MainPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        MainPanel.add(resultLabel);
        MainPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        MainPanel.add(exitButton);

        this.add(MainPanel);

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                System.exit(0);

            }
        });

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
     * Setter for Welcome Name Label
     */
    private void setWelcomeName()
    {
        // Use HTML
        WelcomeNameLabel.setText(
            "<html><center>" +
            "<p style='color:blue;font-weight:bold;font-size:24px;'>Java Quiz</p><br>" +
            "<p>Created By:</p>" +
            "<p>" + CurrentStudent.getName() + "</p>" +
            "</center></html>"
        );
    }

    /**
     * Show welcome screen
     */
    public void addWelcomeScreen()
    {
        // Clean
        this.cleanMainPanel();

        WelcomeNameLabel.setText(
                "<html><center>" +
                "<p style='color:blue;font-weight:bold;font-size:24px;'>Java Quiz</p><br>" +
                "<p>Created By:</p>" +
                "<p>You</p>" +
                "</center></html>");

        MainPanel.add(WelcomeNameLabel);

        GridBagLayout layout = new GridBagLayout();
        MainPanel.setLayout(layout);

        this.add(MainPanel);
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

    /**
     * Add toolbar to main frame
     */
    private void addToolBar()
    {
        // Create elements
        JToolBar toolBar = new JToolBar();

        // Sub elements
        JButton scoreButton = new JButton("Score");
        JButton exitButton = new JButton("Exit");
        JLabel nameLabel = new JLabel("Your Name: ");
        final JTextField nameField = new JTextField();
        final JButton registerButton = new JButton("Register");

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

        /*
         * Listeners
         */
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Do the click
                if (!nameField.getText().equals("") && registerButton.isEnabled()) {

                    // Set the name
                    CurrentStudent.setName(nameField.getText());

                    // Set the label
                    setWelcomeName();

                    // Go to the next frame
                    showQuestion();
                    showFrame();

                    // Disable Elements
                    registerButton.setEnabled(false);
                    nameField.setEnabled(false);

                } else {
                    // Error Box
                    JOptionPane.showMessageDialog(MainPanel.getParent(),
                            "Do not leave name blank.",
                            "Register Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (!nameField.getText().equals("") && registerButton.isEnabled()) {

                    // Set the name
                    CurrentStudent.setName(nameField.getText());

                    // Set the label
                    setWelcomeName();

                    // Go to the next frame
                    showQuestion();
                    showFrame();

                    // Disable Elements
                    registerButton.setEnabled(false);
                    nameField.setEnabled(false);

                } else {
                    // Error Box
                    JOptionPane.showMessageDialog(MainPanel.getParent(),
                            "Do not leave name blank.",
                            "Register Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                // Harsh exit
                System.exit(0);

            }
        });

        scoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                // Test For empty student record
                if (!CurrentStudent.getName().equals("")) {

                    // All good, display score
                    JOptionPane.showMessageDialog(MainPanel.getParent(),
                            "Current score of " + CurrentStudent.getName() +
                            " is " + CurrentStudent.getScore(),
                            "Score Check",
                            JOptionPane.INFORMATION_MESSAGE);

                } else {
                    // Error Box
                    JOptionPane.showMessageDialog(MainPanel.getParent(),
                            "Cannot check score if not registered!",
                            "Score Check Error",
                            JOptionPane.ERROR_MESSAGE);

                }

            }
        });

    }
}
