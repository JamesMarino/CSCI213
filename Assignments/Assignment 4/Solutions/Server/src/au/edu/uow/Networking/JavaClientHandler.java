package au.edu.uow.Networking;

import au.edu.uow.QuestionLibrary.Question;
import au.edu.uow.QuestionLibrary.QuestionLibrary;
import au.edu.uow.UserInterface.UserInterface;

import java.io.*;
import java.net.Socket;
import java.util.List;
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

    private static final int QUESTIONS = 5;

    public JavaClientHandler(Socket incoming, String questionsFileName)
    {
        Incoming = incoming;
        CurrentUser = "";

        // Create Questions
        QuestionLibrary.buildLibrary(questionsFileName);
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
            out.println("Connected to Server");

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
                            UserInterface.showRegistra(CurrentUser);

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

                                // Get Quiz
                                List<Question> quiz = QuestionLibrary.makeQuiz(QUESTIONS);

                                // Return response object
                                objectOutputStream.writeObject(quiz);

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
                            UserInterface.showDisconnection(CurrentUser, true);
                        } else {
                            UserInterface.showDisconnection(null, false);
                        }

                        // Set done to true
                        done = true;

                        break;

                    default:
                        String myObject = INVALID_REQUEST;
                        objectOutputStream.writeObject(myObject);
                        break;
                }
            }

        } catch (IOException ioe) {
            UserInterface.error();
        } finally {
            try {
                Incoming.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
