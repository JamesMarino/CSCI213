package au.edu.uow.QuestionLibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.*;

// XML Reading
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public class QuestionLibrary
{
    // Private Properties
    private static List<Question> questions;

    public static boolean buildLibrary(String qFile)
    {

        questions = new ArrayList<>();

        MultipleChoiceQuestion firstQuestion = new MultipleChoiceQuestion();
        firstQuestion.setQuestion("Are memes gay?");
        firstQuestion.setAnswer("true");

        MultipleChoiceQuestion secondQuestion = new MultipleChoiceQuestion();
        secondQuestion.setQuestion("Samanantha yo Gay");
        secondQuestion.setAnswer("false");

        questions.add(firstQuestion);
        questions.add(secondQuestion);

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
