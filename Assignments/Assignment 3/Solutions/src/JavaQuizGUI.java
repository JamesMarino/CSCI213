import au.edu.uow.UserInterfaceGUI.QuizGUIFrame;

public class JavaQuizGUI
{
    public static void main(String[] args)
    {
        QuizGUIFrame frame = new QuizGUIFrame("Java Quiz");

        frame.addWelcomeScreen();
        frame.showFrame();

        frame.showQuestion();
        frame.showFrame();

        frame.showMarksResult();
        frame.showFrame();

    }
}
