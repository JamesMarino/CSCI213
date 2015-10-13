import au.edu.uow.Networking.JavaClientHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaQuizServer
{
    private static int Port = 8000;

    public static void main(String[] args)
    {
        try {

            // Get host information
            String HostName  = InetAddress.getLocalHost().getHostName();
            String HostIP = InetAddress.getLocalHost().getHostAddress();

            // Display server information
            System.out.println("JavaQuizServer listening at: " + JavaQuizServer.Port);

            // Setup Server
            ServerSocket serverSocket = new ServerSocket(JavaQuizServer.Port);

            while (true) {

                Socket incoming = serverSocket.accept();

                Runnable runnable = new JavaClientHandler(incoming);
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
