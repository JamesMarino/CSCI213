import au.edu.uow.UserInterfaceGUI.QuizGUIFrame;

public class JavaQuizGUI
{
    public static void main(String[] args)
    {
        // Create Frame
        QuizGUIFrame frame = new QuizGUIFrame("Java Quiz");

        // Show the main screen
        frame.addWelcomeScreen();
        frame.showFrame();

    }
}
