package au.edu.uow.Networking;

import au.edu.uow.QuestionLibrary.Question;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class JavaClientHandler implements Runnable
{
    private Socket Incoming;
    private Question QuestionResponse;
    private String CurrentUser;

    /*
     * Constants
     */
    private static final String REGISTER_REQUEST = "REGISTER";
    private static final String REGISTER_RESPONSE = "OK";

    private static final String GET_REQUEST = "GET";
    private static final String QUESTION_REQUEST_ARG = "QUESTION";
    private static final String BYE_REQUEST = "BYE";
    private static final String INVALID_REQUEST = "INVALID";

    public JavaClientHandler(Socket incoming)
    {
        Incoming = incoming;
        QuestionResponse = null;
        CurrentUser = "";
    }

    @Override
    public void run()
    {
        try {

            InputStream inputStream = Incoming.getInputStream();
            OutputStream outputStream = Incoming.getOutputStream();

            Scanner in = new Scanner(inputStream);
            PrintWriter out = new PrintWriter(outputStream, true);

            boolean done = false;

            // Flush Buffer
            out.println("Ready:");

            while (!done && in.hasNext()) {
                String line = in.nextLine();

                // Trim
                line = line.trim();

                // Get the split of the input
                String[] split = new String[2];

                // Check if an argument is being passed with command
                if (line.toLowerCase().contains(" ")) {
                    split = line.split(" ", 2);
                } else {
                    split[0] = line;
                }

                switch (split[0]) {
                    case REGISTER_REQUEST:

                        if (split[1] != null) {

                            // Store the Name
                            CurrentUser = split[1];

                            // Say the user is Registered
                            System.out.println(CurrentUser + " registered");

                            // Return Response
                            out.println(REGISTER_RESPONSE);

                        } else {
                            // Return Response
                            out.println(INVALID_REQUEST);
                        }

                        break;

                    case GET_REQUEST:

                        if (split[1] != null) {

                            // Check what type
                            if (split[1].equals(QUESTION_REQUEST_ARG)) {

                                // Return response object
                                out.println("Object Comes out here");

                            } else {
                                out.println(INVALID_REQUEST);
                            }

                        } else {
                            // Return Response
                            out.println(INVALID_REQUEST);
                        }

                        break;

                    case BYE_REQUEST:

                        // User has Disconnected
                        if (!CurrentUser.isEmpty()) {
                            System.out.println(CurrentUser + " disconnected");
                        } else {
                            System.out.println("Disconnected");
                        }

                        // Set done to true
                        done = true;

                        break;

                    default:
                        out.println(INVALID_REQUEST);
                        break;
                }
            }

        } catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        } finally {
            try {
                Incoming.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
