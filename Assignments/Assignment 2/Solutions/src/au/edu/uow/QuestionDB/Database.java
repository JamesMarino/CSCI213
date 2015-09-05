package au.edu.uow.QuestionDB;

import java.sql.Statement;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class Database
{

    // Constants
    private static String PropertiesFileName = "database.properties";
    private static String PropertiesURL = "jdbc.url";
    private static String PropertiesHome = "jdbc.system.home";

    private Properties DBProperties;
    private Connection DBConnection;

    public Database()
    {
        DBProperties = null;
        DBConnection = null;
    }

    public boolean close()
    {
        try {
            DBConnection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean insert(String query)
    {
        if (DBConnection != null) {

            try {

                Statement stat = DBConnection.createStatement();

                stat.executeUpdate(query);

                stat.close();

                return true;

            } catch (SQLException sqle) {
                return false;
            }

        } else {
            return false;
        }

    }

    public ResultSet select(String query)
    {
        ResultSet result = null;

        if (DBConnection != null) {
            try {
                Statement stat = DBConnection.createStatement();

                result = stat.executeQuery(query);

                stat.close();

                return result;
            } catch (SQLException sqle) {
                return result;
            }
        }

        return result;
    }

    public boolean start()
    {
        if (DBProperties != null) {
            try {
                DBConnection = DriverManager.getConnection(DBProperties.getProperty(PropertiesURL) + ";create=true");

                return true;
            } catch (SQLException sqle) {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean setProperties()
    {
        DBProperties = new Properties();
        InputStream in = getClass().getResourceAsStream(PropertiesFileName);

        try {
            DBProperties.load(in);
            in.close();
        } catch (Exception e) {
            System.out.println(System.getProperty("user.dir"));
            System.out.println("Failure");

            return false;
        }

        return true;
    }

    public boolean removeTable()
    {
        if (DBConnection != null) {

            try {

                Statement stat = DBConnection.createStatement();

                stat.executeUpdate("DROP TABLE Questions");

                stat.close();

                return true;

            } catch (SQLException sqle) {
                return false;
            }

        } else {
            return false;
        }
    }

    public boolean createTable()
    {
        if (DBConnection != null) {

            try {

                Statement stat = DBConnection.createStatement();

                stat.executeUpdate("CREATE TABLE Questions (Q_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY " +
                        "(START WITH 1, INCREMENT BY 1), question CLOB, choices CLOB, answer INT, CONSTRAINT " +
                        "primary_key PRIMARY KEY (Q_ID))");

                stat.close();

                return true;

            } catch (SQLException sqle) {
                return false;
            }

        } else {
            return false;
        }
    }
}
