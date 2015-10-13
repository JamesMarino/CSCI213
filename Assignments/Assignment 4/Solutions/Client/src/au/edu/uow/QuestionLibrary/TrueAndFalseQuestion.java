package au.edu.uow.QuestionLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Processes True False type questions
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */
class TrueAndFalseQuestion implements Question
{

    // Properties
    private List<String> Questions;
    private List<String> Choices;
    private String Answer;

    public TrueAndFalseQuestion()
    {
        Questions = new ArrayList<String>();
        Choices = new ArrayList<String>();
        Answer = "";

        // Automatically Add Choices
        Choices.add("True");
        Choices.add("False");

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
        String lowerAnswer = Answer.toLowerCase();
        String lowerResult = Choices.get(ans-1).toLowerCase();

        // Remove new line
        lowerAnswer = lowerAnswer.replace("\n", "");

        return lowerAnswer.equals(lowerResult);
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
