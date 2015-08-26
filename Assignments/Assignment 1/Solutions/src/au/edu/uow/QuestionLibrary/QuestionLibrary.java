package au.edu.uow.QuestionLibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionLibrary
{
    // Private Properties
    private static List<Question> questions;

    public static boolean buildLibrary(String qFile)
    {

        // This needs to be automatic proccess
        questions = new ArrayList<>();

        MultipleChoiceQuestion firstQuestion = new MultipleChoiceQuestion();
        firstQuestion.setQuestion("Are memes delicious?");
        firstQuestion.setAnswer("true");

        questions.add(firstQuestion);

        MultipleChoiceQuestion secondQuestion = new MultipleChoiceQuestion();
        secondQuestion.setQuestion("Samanantha?");
        secondQuestion.setAnswer("false");

        questions.add(secondQuestion);

        TrueAndFalseQuestion thirdQuestion = new TrueAndFalseQuestion();
        thirdQuestion.setQuestion("question three");
        thirdQuestion.setAnswer("ans1");

        thirdQuestion.addChoice("ans2");
        thirdQuestion.addChoice("ans1");
        thirdQuestion.addChoice("ans3");
        thirdQuestion.addChoice("ans4");

        questions.add(thirdQuestion);

        return true;
    }

    public static List<Question> makeQuiz(int noOfQuestions)
    {

        List<Question> quiz = new ArrayList<>();
        Iterator<Question> iterator = questions.iterator();

        for (int i = 0; i < noOfQuestions; i++) {
            if (iterator.hasNext()) {
                quiz.add(iterator.next());
            }
        }

        return quiz;
    }
}
