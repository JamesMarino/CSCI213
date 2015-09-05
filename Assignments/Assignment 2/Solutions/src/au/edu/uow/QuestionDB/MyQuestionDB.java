package au.edu.uow.QuestionDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import au.edu.uow.QuestionLibrary.*;

public class MyQuestionDB
{
    Database connection;

    public MyQuestionDB()
    {
        // Set up database connection
        connection = new Database();

        connection.setProperties();
        connection.start();
        connection.createTable();

        connection.insert("INSERT INTO Questions VALUES ('Hello World')");

        ResultSet result = connection.select("SELECT * FROM Questions");

        try {

            while (result.next()) {
                System.out.println(result.getString(1));
            }

            result.close();

        } catch (SQLException e) {
            System.out.println("Error no Records");
        }

        connection.removeTable();
        connection.close();

    }

    /**
     * This method creates and populates a table in the database
     *   storing the questions from the question file. This method
     *   should use the addQuestion method to insert question content
     *   into the database
     * @param questionFile The file name of the question file
     * @return True if the database is successfully populated
     * @see #addQuestion(au.edu.uow.QuestionLibrary.Question)
     */
    public boolean buildQuestionDB(String questionFile)
    {
        return true;
    }

    /**
     * This method returns the total number of questions in the database
     * @return The total number of questions in the question database
     * @see #buildQuestionDB
     */
    public int getTotalNumberOfQuestions()
    {
        return 0;
    }

    /**
     * This method returns the question from the database at the given position
     * @param questionIndex The index of the question in the database
     * @return The question object
     */
    public Question getQuestion(int questionIndex)
    {
        List<String> one = new ArrayList<>();
        List<String> two = new ArrayList<>();

        MultipleChoiceQuestion myMultiple = new MultipleChoiceQuestion(one, two, 1);

        return myMultiple;
    }

    /**
     * This method adds a question to the database
     * @param question The question object to be added to the database
     * @return True if the operation is successful
     */
    public boolean addQuestion(Question question)
    {
        return true;
    }

    /**
     * This method removes the created table from the database
     * @return True if the operation is successful
     * @see #buildQuestionDB(String)
     */
    public boolean cleanUpDB()
    {
        return true;
    }

    /**
     * This method makes a quiz from the question database
     *   with the number of questions as specified. This method
     *   should use the getQuestion method to retrieval question
     *   content from the database
     * @param noOfQuestions - the number of questions in a quiz
     * @return Quiz questions in a list
     * @see #getQuestion(int)
     */
    public List<Question> makeQuiz(int noOfQuestions)
    {
        List<Question> my = new ArrayList<>();

        return my;
    }
}
