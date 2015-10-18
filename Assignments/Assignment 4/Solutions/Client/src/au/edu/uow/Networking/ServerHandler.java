package au.edu.uow.Networking;

import au.edu.uow.QuestionLibrary.Question;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * Server response handler
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */
public class ServerHandler
{

    /*
     * Constants
     */
    private static final String REGISTER_REQUEST = "REGISTER";
    private static final String GET_REQUEST = "GET";
    private static final String QUESTION_REQUEST_ARG = "QUESTION";
    private static final String BYE_REQUEST = "BYE";

    private String Host = "localhost";
    private int Port = 40213;

    private Socket Socket = null;
    private PrintWriter Out = null;
    private ObjectInputStream ObjectIn = null;
    private Scanner ScannerIn = null;

    boolean connected;

    /**
     * Set default connection status to false
     */
    public ServerHandler()
    {
        connected = false;
    }

    /**
     * Getter - Connection status
     * @return connection status
     */
    public boolean getConnectionStatus()
    {
        return connected;
    }

    /**
     * Connect to server
     * @param serverName servers name
     * @param serverPort servers port
     */
    public void connect(String serverName, int serverPort)
    {
        Host = serverName;
        Port = serverPort;

        try {

            Socket = new Socket(Host, Port);
            Out = new PrintWriter(Socket.getOutputStream(), true);
            ObjectIn = new ObjectInputStream(Socket.getInputStream());
            ScannerIn = new Scanner(Socket.getInputStream());

            // Check if ready
            System.out.println(ScannerIn.nextLine());

            // Set Status
            connected = true;

        } catch (Exception e) {
            System.out.println("Could not connect to server");
            connected = false;
        }
    }

    /**
     * Getter - Questions
     * @return questions
     */
    public List<Question> getQuestion()
    {
        return (List<Question>)request(GET_REQUEST + " " + QUESTION_REQUEST_ARG);
    }

    /**
     * Register a user
     * @param name current user
     * @return response
     */
    public String register(String name)
    {
        return (String)request(REGISTER_REQUEST + " " + name);
    }

    /**
     * Close connection to server
     */
    public void close()
    {
        try {

            // Tell server we are leaving
            Out.println(BYE_REQUEST);

            Out.close();
            ScannerIn.close();
            Socket.close();

            connected = false;

        } catch (Exception e) {
            System.out.println("Error Closing Connection");
            connected = true;
        }
    }

    /**
     * Reuest a serialised object from the server
     * @param command protocol command
     * @return object serialised
     */
    private Object request(String command)
    {
        Object received;

        // Receive Object
        try {

            // Send
            Out.println(command);

            received = ObjectIn.readObject();
        } catch (Exception e) {
            received = null;
        }

        return received;
    }
}
