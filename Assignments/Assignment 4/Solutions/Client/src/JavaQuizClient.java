import au.edu.uow.ClientGUI.QuizClientGUIFrame;

public class JavaQuizClient
{
    private static final String  WindowName = "Quiz";

    public static void main(String[] args)
    {
        QuizClientGUIFrame quizClientGUIFrame = new QuizClientGUIFrame(WindowName);

        // Show the main screen
        quizClientGUIFrame.addWelcomeScreen();
        quizClientGUIFrame.showFrame();
    }
}
