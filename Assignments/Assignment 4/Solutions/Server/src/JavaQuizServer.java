import au.edu.uow.Networking.JavaClientHandler;
import au.edu.uow.UserInterface.UserInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaQuizServer
{
    private static int Port = 8000;
    private static final String QUESTION_FILE = "questions.xml";

    public static void main(String[] args)
    {

        // Set Port
        if (args.length > 0) {
            Port = Integer.parseInt(args[0]);
        } else {
            UserInterface.showUsage();
            System.exit(0);
        }

        try {

            // Display server information
            UserInterface.showServerInfo(JavaQuizServer.Port);

            // Setup Server
            ServerSocket serverSocket = new ServerSocket(JavaQuizServer.Port);

            while (true) {

                // Setup Questions
                String questionsURL = JavaQuizServer.class.getResource(QUESTION_FILE).getPath();
                questionsURL = questionsURL.replaceAll("%20", " ");

                Socket incoming = serverSocket.accept();

                Runnable runnable = new JavaClientHandler(incoming, questionsURL);
                Thread thread = new Thread(runnable);
                thread.start();

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
