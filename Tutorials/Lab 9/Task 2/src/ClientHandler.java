import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

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

            InputStream inputStream = Incoming.getInputStream();
            OutputStream outputStream = Incoming.getOutputStream();

            Scanner in = new Scanner(inputStream);
            PrintWriter out = new PrintWriter(outputStream, true);

            out.println("Enter Command:");

            boolean done = false;

            while (!done && in.hasNext()) {
                String line = in.nextLine();

                if (line.trim().equals("BYE")) {
                    out.println("Goodbye...");
                    done = true;
                } else if (line.trim().equals("TIME")) {
                    out.println("Time is " + getTime());
                } else if (line.trim().equals("HOSTNAME")) {
                    out.println("Host Name is " + InetAddress.getLocalHost().getHostName());
                } else {
                    out.println("Are you an alien?");
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

    private static String getTime()
    {
        // Get current date time with Calendar()
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss 'on' dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}
