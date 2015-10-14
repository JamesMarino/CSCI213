package au.edu.uow.ClientGUI;

/**
 * Single User Access
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */

public class Student {

    private int Score;
    private String Name;
    private int QuestionCount;

    public Student()
    {
        // Set Initial Values
        Score = 0;
        Name = "";
        QuestionCount = 0;
    }

    /**
     * This method records the score if correct
     * @param isCorrect Correct or incorrect answer
     */
    public void recordScore(boolean isCorrect)
    {
        if (isCorrect) {
            Score += 1;
        }
    }

    /**
     * Setter - Name
     * @param name Name of user
     */
    public void setName(String name)
    {
        Name = name;
    }

    /**
     * Setter - Count of questions
     * @param count count of question
     */
    public void setQuestionCount(int count)
    {
        QuestionCount = count;
    }

    /**
     * Getter - Name
     * @return Name of user
     */
    public String getName()
    {
        return Name;
    }

    /**
     * Getter - Current score
     * @return Current Score
     */
    public  int getScore()
    {
        return Score;
    }

    /**
     * Getter - Question Count
     * @return Count of Questions
     */
    public int getQuestionCount()
    {
        return QuestionCount;
    }
}
