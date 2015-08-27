package au.edu.uow.QuestionLibrary;

import java.util.ArrayList;
import java.util.List;

class TrueAndFalseQuestion implements Question
{

    // Properties
    private List<String> Questions;
    private List<String> Choices;
    private String Answer;

    public TrueAndFalseQuestion()
    {
        Questions = new ArrayList<>();
        Choices = new ArrayList<>();
        Answer = "";

        // Automatically Add Choices
        Choices.add("True");
        Choices.add("False");

    }

    public List<String> getQuestion()
    {
        return Questions;
    }

    public List<String> getChoices()
    {
        return Choices;
    }

    public boolean compareAnswer(int ans)
    {
        String lowerAnswer = Answer.toLowerCase();
        String lowerResult = Choices.get(ans-1).toLowerCase();

        // Remove new line
        lowerAnswer = lowerAnswer.replace("\n", "");

        return lowerAnswer.equals(lowerResult);
    }

    public void setQuestion(String question)
    {
        Questions.add(question);
    }

    public void setAnswer(String answer)
    {
        Answer = answer;
    }
}
