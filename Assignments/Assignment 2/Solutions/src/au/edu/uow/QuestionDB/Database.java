package au.edu.uow.QuestionDB;

import au.edu.uow.QuestionLibrary.MultipleChoiceQuestion;
import au.edu.uow.QuestionLibrary.Question;
import au.edu.uow.QuestionLibrary.TrueAndFalseQuestion;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database
{

    // Constants
    private static final String PropertiesFileName = "database.properties";
    private static final String PropertiesURL = "jdbc.url";
    private static final String PropertiesHome = "jdbc.system.home";

    // Table Constants
    private static final String TableName = "Questions";

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

    public boolean insert(Question question)
    {

        if (DBConnection != null) {

            // Data
            List<String> choices = question.getChoices();
            List<String> questions = question.getQuestion();
            int answer = question.getAnswer();

            try {

                // Serialise
                ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                ByteArrayOutputStream bos2 = new ByteArrayOutputStream();

                ObjectOutputStream os1 = new ObjectOutputStream(bos1);
                ObjectOutputStream os2 = new ObjectOutputStream(bos2);

                os1.writeObject(choices);
                os2.writeObject(questions);

                os1.close();
                os2.close();

                InputStream choicesStream = new ByteArrayInputStream(bos1.toByteArray());
                InputStream questionsStream = new ByteArrayInputStream(bos2.toByteArray());

                PreparedStatement ps = DBConnection.prepareStatement("INSERT INTO " + TableName + "" +
                        "(question, choices, answer) VALUES (?, ?, ?)");

                // Insert Values
                ps.setAsciiStream(1, questionsStream);
                ps.setAsciiStream(2, choicesStream);
                ps.setInt(3, answer);

                // Run the query
                ps.execute();

                // Close
                ps.close();

                return true;

            } catch (Exception e) {
                return false;
            }

        } else {
            return false;
        }

    }

    public Question select(int questionIndex)
    {
        ResultSet resultSet;

        List<String> questionList = new ArrayList<String>();
        List<String> choiceList = new ArrayList<String>();
        int answerInt = 0;

        if (DBConnection != null) {
            try {

                {
                    // Query for Questions
                    Statement stat = DBConnection.createStatement();
                    resultSet = stat.executeQuery("SELECT question FROM " + TableName + " WHERE Q_ID = " + questionIndex + 1);

                    while (resultSet.next()) {
                        Clob clob = resultSet.getClob("question");

                        InputStream ip = clob.getAsciiStream();

                        ObjectInputStream in = new ObjectInputStream(ip);
                        questionList = (List<String>) in.readObject();

                    }

                    stat.close();
                }

                // Query for Choices
                {
                    Statement stat = DBConnection.createStatement();
                    resultSet = stat.executeQuery("SELECT choices FROM " + TableName + " WHERE Q_ID = " + questionIndex + 1);

                    while (resultSet.next()) {
                        Clob clob = resultSet.getClob("choices");

                        InputStream ip = clob.getAsciiStream();

                        ObjectInputStream in = new ObjectInputStream(ip);
                        choiceList = (List<String>) in.readObject();

                    }

                    stat.close();
                }

                // Get Answer
                {
                    Statement stat = DBConnection.createStatement();
                    resultSet = stat.executeQuery("SELECT answer FROM " + TableName + " WHERE Q_ID = " + questionIndex + 1);

                    while (resultSet.next()) {

                        answerInt = resultSet.getInt("answer");

                    }

                    stat.close();
                }

            } catch (Exception e) {
                choiceList = null;
            }
        }

        try {
            // Check what class through choice
            if (choiceList.get(0).toLowerCase().equals("true") || choiceList.get(0).toLowerCase().equals("false")) {
                String out = "" + answerInt;
                return new TrueAndFalseQuestion(questionList, out);
            } else {
                return new MultipleChoiceQuestion(questionList, choiceList, answerInt);
            }
        } catch (Exception e) {
            return new MultipleChoiceQuestion(questionList, choiceList, answerInt);
        }

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

                stat.executeUpdate("DROP TABLE " + TableName);

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

                stat.executeUpdate("CREATE TABLE " + TableName + "(Q_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY " +
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

    public int totalRows()
    {
        ResultSet result;

        if (DBConnection != null) {
            try {
                Statement stat = DBConnection.createStatement();
                int count = 0;

                result = stat.executeQuery("SELECT COUNT(*) FROM " + TableName);

                while (result.next()) {
                    count = result.getInt(1);
                }

                stat.close();

                return count;
            } catch (SQLException sqle) {
                return -1;
            }
        } else {
            return -1;
        }

    }
}
