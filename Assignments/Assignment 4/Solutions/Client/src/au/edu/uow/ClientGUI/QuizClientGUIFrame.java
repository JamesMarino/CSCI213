package au.edu.uow.ClientGUI;

import au.edu.uow.Networking.ServerHandler;
import au.edu.uow.QuestionLibrary.Question;

import javax.swing.*;
import java.util.List;

public class QuizClientGUIFrame extends JFrame
{
    public QuizClientGUIFrame()
    {
        setup();
    }

    public void setup()
    {
        ServerHandler serverHandler = new ServerHandler();

        // Receive
        String registerResponse;
        List<Question> question;

        registerResponse = serverHandler.register("James");
        question = serverHandler.getQuestion();

        // Close connection
        serverHandler.close();
    }
}
