package au.edu.uow.QuestionLibrary;

import java.util.ArrayList;
import java.util.List;

class TrueAndFalseQuestion implements Question
{

    private List<String> Questions;
    private List<String> Choices;
    private String Answer;

    public TrueAndFalseQuestion()
    {

    }

    public List<String> getQuestion()
    {
        List<String> questions = new ArrayList<>();

        return questions;
    }

    public List<String> getChoices()
    {
        List<String> choices = new ArrayList<>();

        return choices;
    }

    public boolean compareAnswer(int ans)
    {
        return true;
    }

    public void addChoice(String choice)
    {
        Choices.add(choice);
    }
}
