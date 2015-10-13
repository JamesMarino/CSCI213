package au.edu.uow.Networking;

import au.edu.uow.QuestionLibrary.MultipleChoiceQuestion;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class JavaClientHandler implements Runnable
{
    private Socket Incoming;
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
        CurrentUser = "";
    }

    @Override
    public void run()
    {
        try {

            OutputStream outputStream = Incoming.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(Incoming.getOutputStream());
            InputStream inputStream = Incoming.getInputStream();

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
                            String myObject = REGISTER_RESPONSE;
                            objectOutputStream.writeObject(myObject);

                        } else {
                            // Return Response
                            String myObject = INVALID_REQUEST;
                            objectOutputStream.writeObject(myObject);
                        }

                        break;

                    case GET_REQUEST:

                        if (split[1] != null) {

                            // Check what type
                            if (split[1].equals(QUESTION_REQUEST_ARG)) {

                                // Return response object
                                MultipleChoiceQuestion myObject = new MultipleChoiceQuestion();
                                objectOutputStream.writeObject(myObject);

                            } else {
                                // Return Response
                                String myObject = INVALID_REQUEST;
                                objectOutputStream.writeObject(myObject);
                            }

                        } else {
                            // Return Response
                            String myObject = INVALID_REQUEST;
                            objectOutputStream.writeObject(myObject);
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
                        // out.println(INVALID_REQUEST);
                        String myObject = INVALID_REQUEST;
                        objectOutputStream.writeObject(myObject);
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
