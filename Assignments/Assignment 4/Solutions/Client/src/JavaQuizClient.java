import au.edu.uow.QuestionLibrary.Question;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class JavaQuizClient
{
    public static void main(String[] args)
    {

        try
        {
            Socket socket = new Socket("localhost", 8000);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            //Question question = ;
            //objectOutputStream.writeObject();

            System.out.println(in.nextLine());

            Scanner stdIn = new Scanner(System.in);
            String userInput;

            while ((userInput = stdIn.nextLine()) != null) {
                out.println(userInput);

                if (!"BYE".equals(userInput)) {
                    System.out.println(in.nextLine());
                } else {
                    break;
                }
            }

            out.close();
            in.close();
            socket.close();
        }
        catch (Exception e)
        {
            System.out.println("Make sure the server is running and try again");
        }
    }
}
