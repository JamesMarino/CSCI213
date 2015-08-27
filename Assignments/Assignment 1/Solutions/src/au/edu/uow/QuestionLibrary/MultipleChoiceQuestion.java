package au.edu.uow.QuestionLibrary;

import java.util.ArrayList;
import java.util.List;

class MultipleChoiceQuestion implements Question
{
    // Properties
    private List<String> Questions;
    private List<String> Choices;
    private String Answer;

    public MultipleChoiceQuestion()
    {
        Questions = new ArrayList<>();
        Choices = new ArrayList<>();
        Answer = "";
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
        String lowerAnswer = Choices.get(Integer.parseInt(Answer.substring(0, 1))-1).toLowerCase();
        String lowerResult = Choices.get(ans-1).toLowerCase();

        return lowerAnswer.equals(lowerResult);
    }

    public void addChoice(String choice)
    {
        Choices.add(choice);
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
