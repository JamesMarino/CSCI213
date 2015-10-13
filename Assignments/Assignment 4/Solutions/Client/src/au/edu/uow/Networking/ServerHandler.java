package au.edu.uow.Networking;

import au.edu.uow.QuestionLibrary.MultipleChoiceQuestion;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler
{
    public ServerHandler()
    {
        try
        {
            Socket socket = new Socket("localhost", 8000);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
            Scanner scannerIn = new Scanner(socket.getInputStream());

            System.out.println(scannerIn.nextLine());

            Scanner stdin = new Scanner(System.in);
            String userInput;

            while ((userInput = stdin.nextLine()) != null) {

                out.println(userInput);

                if (!"BYE".equals(userInput)) {

                    try {

                        Object object = objectIn.readObject();

                        if (object.getClass() == String.class) {
                            String response = (String)object;
                            System.out.println(response);
                        } else if (object.getClass() == MultipleChoiceQuestion.class) {
                            MultipleChoiceQuestion response = (MultipleChoiceQuestion)object;
                            System.out.println(response);
                        }

                    } catch (Exception e) {
                        System.out.println("Response Error");
                        e.printStackTrace();
                    }

                } else {
                    break;
                }
            }

            out.close();
            scannerIn.close();
            socket.close();
        }
        catch (Exception e)
        {
            System.out.println("Make sure the server is running and try again");
        }
    }
}
