package au.edu.uow.UserInterfaceGUI;

import au.edu.uow.QuestionLibrary.Question;
import au.edu.uow.QuestionLibrary.QuestionLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class QuizGUIFrame extends JFrame
{

    // Panels
    private JPanel MainPanel = new JPanel();
    private JPanel QuestionPanel = new JPanel();
    private JPanel ChoicesPanel = new JPanel();

    // Labels
    private JLabel WelcomeNameLabel = new JLabel("");

    // Buttons
    private JButton NextQuestionButton = new JButton("Continue");

    // Data
    private Student CurrentStudent = new Student();
    private static int QuestionPosition = 0;
    private static int QuestionListSize = 0;
    private List<Question> QuizData;

    public QuizGUIFrame(String windowName)
    {
        /*
         * Set up frame
         */
        // Set the name
        super(windowName);

        // Close operation
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set the size
        this.setSize(650, 450);

        // Set position on screen centre
        this.setLocationRelativeTo(null);

        // Add the toolbar to the frame
        this.addToolBar();

        /*
         * Generate Quiz
         */
        QuestionLibrary myQuestionDB = new QuestionLibrary();
        boolean questionList = myQuestionDB.buildLibrary("/Users/james/UOW/CSCI213 (Java)/Assignments/Assignment 3/Solutions/src/data/questions.xml");
        QuizData = myQuestionDB.makeQuiz(5);

        // Set event listener - next question
        NextQuestionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                // Show another question
                showQuestion();
                showFrame();

            }
        });

    }

    public void showMarksResults()
    {
        // Clean
        this.cleanMainPanel();

        // New Panel
        GridLayout boxLayout = new GridLayout();
        MainPanel.setLayout(boxLayout);

        // Result Label
        JLabel resultLabel = new JLabel("My Results");
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Result Button
        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        MainPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        MainPanel.add(resultLabel);
        MainPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        MainPanel.add(exitButton, BOTTOM_ALIGNMENT);

        this.add(MainPanel);

    }

    public void showMarksResult()
    {
        // Clean
        this.cleanMainPanel();

        // New Panel
        BoxLayout boxLayout = new BoxLayout(MainPanel, BoxLayout.Y_AXIS);
        MainPanel.setLayout(boxLayout);

        // Result Label
        JLabel resultLabel = new JLabel("My Results");
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Result Button
        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        MainPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        MainPanel.add(resultLabel);
        MainPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        MainPanel.add(exitButton);

        this.add(MainPanel);

    }

    public void showQuestion()
    {

        // Current Question
        Question question = QuizData.get(QuestionPosition);

        // Clean
        this.cleanMainPanel();

        MainPanel.removeAll();

        // New Layout
        GridLayout mainLayout = new GridLayout(2, 1);

        // Setup
        MainPanel.setLayout(mainLayout);

        /*
         * Question Panel
         */
        // Set Layout
        GridBagLayout questionLayout = new GridBagLayout();
        QuestionPanel.setLayout(questionLayout);

        // Add Content
        JLabel questionText = new JLabel("<html>" + question.getQuestion().get(0) +
                "</html>");
        QuestionPanel.setBackground(Color.WHITE);
        QuestionPanel.add(questionText);


        /*
         * Choice Panel
         */
        // Submit Button
        JButton submit = new JButton("Continue");

        // ----- Edit ------
        // New Radio Buttons
        List radioButtonList = new ArrayList<JRadioButton>();
        for (int i = 0; i < question.getChoices().size(); i++) {
            radioButtonList.add(new JRadioButton(question.getChoices().get(i)));
        }

        // Group them
        ButtonGroup buttonGroup = new ButtonGroup();

        for (Object current : radioButtonList) {
            buttonGroup.add((JRadioButton) current);
        }

        // Setup Layout
        BoxLayout buttonLayout = new BoxLayout(ChoicesPanel, BoxLayout.Y_AXIS);
        ChoicesPanel.setLayout(buttonLayout);
        NextQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel radioButtonsPanel = new JPanel();
        BoxLayout radioButtonsLayout = new BoxLayout(radioButtonsPanel, BoxLayout.Y_AXIS);
        radioButtonsPanel.setLayout(radioButtonsLayout);

        // ----- Edit ------
        // Add to Panel
        for (Object current : radioButtonList) {
            radioButtonsPanel.add((JRadioButton) current);
        }

        radioButtonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ChoicesPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ChoicesPanel.add(radioButtonsPanel);

        // Submit Button
        ChoicesPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        ChoicesPanel.add(NextQuestionButton, BorderLayout.PAGE_END);

        /*
         * Add to Main Panel
         */
        MainPanel.add(QuestionPanel);
        MainPanel.add(ChoicesPanel);

        // Add to frame
        this.add(MainPanel);

        // Go to next question
        QuestionPosition++;

    }

    private void setWelcomeName()
    {

        WelcomeNameLabel.setText(
            "<html><center>" +
            "<p style='color:blue;font-weight:bold;font-size:24px;'>Java Quiz</p><br>" +
            "<p>Created By:</p>" +
            "<p>" + CurrentStudent.getName() + "</p>" +
            "</center></html>"
        );
    }

    public void addWelcomeScreen()
    {
        // Clean
        this.cleanMainPanel();

        WelcomeNameLabel.setText(
                "<html><center>" +
                "<p style='color:blue;font-weight:bold;font-size:24px;'>Java Quiz</p><br>" +
                "<p>Created By:</p>" +
                "<p></p>" +
                "</center></html>");

        MainPanel.add(WelcomeNameLabel);

        GridBagLayout layout = new GridBagLayout();
        MainPanel.setLayout(layout);

        this.add(MainPanel);
    }

    public void showFrame()
    {
        // Show the frame
        this.setVisible(true);
    }

    private void cleanMainPanel()
    {
        // Remove the panel
        this.remove(MainPanel);

        // Create a fresh instance
        MainPanel = new JPanel();
    }

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

                }
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                System.exit(0);

            }
        });

        scoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                // Test For empty student record
                if (!CurrentStudent.getName().equals("")) {

                    // All good, display score


                } else {
                    // Error Box

                }

            }
        });

    }
}
