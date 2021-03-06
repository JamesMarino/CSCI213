import java.net.*;
import java.util.Scanner;

public class MyClient
{
	public static void main(String[] args)
	{
		if (args.length != 1){
			System.out.println("Usage: MyClient serverHostname");
			System.exit(0);
		}
		
		try
		{
			Socket socket = new Socket (args[0], 8000);

			Scanner in = new Scanner(socket.getInputStream());
			System.out.println(in.nextLine());

			in.close();
			socket.close();
		}
		catch (Exception e)
		{
			System.out.println("Make sure the server is running and try again");
		}
	}
}