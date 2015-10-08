import java.io.*;
import java.net.*;

public class MyServer
{  
	private final static int port = 8000; 

	public static void main(String[] args )
	{  

		try {

			// Get host information
			String HostName  = InetAddress.getLocalHost().getHostName();
			String HostIP = InetAddress.getLocalHost().getHostAddress();

			// Display server information
			System.out.println("MyServer started on "+ HostName +" with IP: "+ HostIP + " on the port number: " + port);

			// Setup Server
			int i = 1;
			ServerSocket serverSocket = new ServerSocket(port);

			while (true) {

				Socket incoming = serverSocket.accept();
				System.out.println("Spawning: " + i);

				Runnable runnable = new ClientHandler(incoming);
				Thread thread = new Thread(runnable);
				thread.start();

				i++;

			}

		}
		catch (IOException e)
		{  
			e.printStackTrace();
		}
	}

}

