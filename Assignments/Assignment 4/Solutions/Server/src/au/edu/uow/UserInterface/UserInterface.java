package au.edu.uow.UserInterface;

public class UserInterface
{
    public static void showUsage()
    {
        System.out.println("Usage: java JavaQuizServer 12345");
    }

    public static void showServerInfo(int port)
    {
        System.out.println("JavaQuizServer listening at: " + port);
    }

    public static void showRegistra(String currentUser)
    {
        System.out.println(currentUser + " registered");
    }

    public static void showDisconnection(String currentUser, boolean userConnected)
    {
        if (userConnected) {
            System.out.println(currentUser + " disconnected");
        } else {
            System.out.println("Disconnected");
        }
    }

    public static void error()
    {
        System.out.println("An Error occurred");
    }

}
