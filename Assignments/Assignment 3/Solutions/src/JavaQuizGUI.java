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

    /*
    private static void setProperties()
    {
        // Properties
        // create and load default properties
        Properties properties = new Properties();

        JavaQuizGUI thisClass = new JavaQuizGUI();
        URL test = thisClass.getClass().getResource("JavaQuizGUI.conf");
        String Final = test.getPath();

        Final = Final.replaceAll("%20", " ");

        try {
            FileInputStream in = new FileInputStream(Final);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            System.out.println("Could not load Properties File");
        }

        // Set System properties
        System.setProperties(properties);
    }
    */
}
