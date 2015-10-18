import au.edu.uow.ClientGUI.QuizClientGUIFrame;

/**
 * Main client class
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */
public class JavaQuizClient
{
    private static final String  WindowName = "Quiz";

    public static void main(String[] args)
    {
        String ServerDomain = null;
        int ServerPort = 0;

        // Take command line args
        if (args.length > 0) {
            try {

                String[] split = args[0].split(":");

                ServerDomain = split[0];
                ServerPort = Integer.parseInt(split[1]);

            } catch (Exception e) {

                // If split fails, single args taken in just server name
                ServerDomain = args[0];
            }
        }

        QuizClientGUIFrame quizClientGUIFrame = new QuizClientGUIFrame(WindowName, ServerDomain, ServerPort);

        // Show the main screen
        quizClientGUIFrame.addWelcomeScreen();
        quizClientGUIFrame.showFrame();
    }
}
