import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientHandler implements Runnable
{
    private Socket Incoming;

    public ClientHandler(Socket incoming)
    {
        Incoming = incoming;
    }

    @Override
    public void run()
    {
        try {

            // InputStream inputStream = Incoming.getInputStream();
            OutputStream outputStream = Incoming.getOutputStream();

            // display client information at server
            System.out.println("Client is connected");

            // Send the time at server to the client
            PrintWriter out = new PrintWriter(outputStream, true);

            out.println("The time at MyServer is " + getTime());

            Incoming.close();
            System.out.println("Client is disconnected");

        } catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        }

    }

    private static String getTime()
    {
        // Get current date time with Calendar()
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss 'on' dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}
