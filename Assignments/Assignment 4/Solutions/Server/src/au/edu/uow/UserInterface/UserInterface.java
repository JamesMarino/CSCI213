package au.edu.uow.UserInterface;

/**
 * Server User Interface Class
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */
public class UserInterface
{

    /**
     * Print Server Info
     * @param port server port
     */
    public static void showServerInfo(int port)
    {
        System.out.println("JavaQuizServer listening at: " + port);
    }

    /**
     * Show registered user
     * @param currentUser current user logged into server
     */
    public static void showRegistra(String currentUser)
    {
        System.out.println(currentUser + " registered");
    }

    /**
     * Show disconnection status
     * @param currentUser current user registered
     * @param userConnected weather user is connected or not
     */
    public static void showDisconnection(String currentUser, boolean userConnected)
    {
        if (userConnected) {
            System.out.println(currentUser + " disconnected");
        } else {
            System.out.println("Disconnected");
        }
    }

    /**
     * Print generic error
     */
    public static void error()
    {
        System.out.println("An Error occurred");
    }

}
