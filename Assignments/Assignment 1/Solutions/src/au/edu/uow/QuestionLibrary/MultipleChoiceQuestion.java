package au.edu.uow.QuestionLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Processes multiple choice like questions
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */
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

    /**
     * Getter - Question
     * @return Question
     */
    public List<String> getQuestion()
    {
        return Questions;
    }

    /**
     * Getter - List of choices
     * @return List of choices
     */
    public List<String> getChoices()
    {
        return Choices;
    }

    /**
     * Checks correct answer
     * @param ans The student's answer
     * @return Correct answer or not
     */
    public boolean compareAnswer(int ans)
    {
        String lowerAnswer = Choices.get(Integer.parseInt(Answer.substring(0, 1))-1).toLowerCase();
        String lowerResult = Choices.get(ans-1).toLowerCase();

        return lowerAnswer.equals(lowerResult);
    }

    /**
     * Adds MAXINT amount of choices to list
     * @param choice a possible answer
     */
    public void addChoice(String choice)
    {
        Choices.add(choice);
    }

    /**
     * Setter - Questions
     * @param question Question being asked
     */
    public void setQuestion(String question)
    {
        Questions.add(question);
    }

    /**
     * Setter - Answer
     * @param answer Answer for current question
     */
    public void setAnswer(String answer)
    {
        Answer = answer;
    }
}
