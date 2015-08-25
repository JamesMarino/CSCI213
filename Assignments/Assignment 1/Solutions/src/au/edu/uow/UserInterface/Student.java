package au.edu.uow.UserInterface;

public class Student {
    // Properties
    private int Score;
    private String Name;

    Student()
    {
        // Set Initial Values
        Score = 0;
        Name = "";
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

    public String getName()
    {
        return Name;
    }

    public  int getScore()
    {
        return Score;
    }
}
