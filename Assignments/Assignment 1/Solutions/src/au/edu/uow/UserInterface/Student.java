package au.edu.uow.UserInterface;

public class Student {

    // Properties
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

    public void recordScore(boolean isCorrect)
    {
        if (isCorrect) {
            Score += 1;
        }
    }

    public void setName(String name)
    {
        Name = name;
    }

    public void setQuestionCount(int count)
    {
        QuestionCount = count;
    }

    public String getName()
    {
        return Name;
    }

    public  int getScore()
    {
        return Score;
    }

    public int getQuestionCount()
    {
        return QuestionCount;
    }
}
