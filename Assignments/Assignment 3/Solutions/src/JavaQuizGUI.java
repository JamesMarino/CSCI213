import au.edu.uow.UserInterfaceGUI.QuizGUIFrame;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * App entry point
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */
public class JavaQuizGUI
{
    public static void main(String[] args)
    {
        // Get Properties
        Properties properties = setProperties();

        // Set Look and Feel
        setLookAndFeel(properties);

        // Create Frame
        QuizGUIFrame frame = new QuizGUIFrame("Java Quiz", properties);

        // Show the main screen
        frame.addWelcomeScreen();
        frame.showFrame();

    }

    /**
     * Sets properties from JavaQuizGUI.conf
     * @return Properties instance
     */
    private static Properties setProperties()
    {
        // Properties
        Properties properties = new Properties();

        // Setup URL
        String propertiesURL = JavaQuizGUI.class.getResource("JavaQuizGUI.conf").getPath();
        propertiesURL = propertiesURL.replaceAll("%20", " ");

        try {
            FileInputStream in = new FileInputStream(propertiesURL);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            System.out.println("Could not load Properties File");
        }

        // Return Properties
        return properties;

    }

    /**
     * Setter for 'Look and Feel'
     * @param properties Full properties object
     */
    private static void setLookAndFeel(Properties properties)
    {
        try {
            UIManager.setLookAndFeel(properties.getProperty("LookAndFeel"));
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception f) {

                // Cannot Load basic Look and Feel, exit out
                System.out.println("Fatal Error");
                System.exit(0);
            }
        }
    }
}
