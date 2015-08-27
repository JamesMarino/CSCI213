package au.edu.uow.QuestionLibrary;

import java.util.*;

public class QuestionLibrary
{
    // Private Properties
    private static List<Question> questions;

    public static boolean buildLibrary(String qFile)
    {

        XMLHandler xmlFile = new XMLHandler(qFile);

        if(xmlFile.readFile()) {
            if (xmlFile.createLibrary()) {
                questions = xmlFile.getLibrary();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static List<Question> makeQuiz(int noOfQuestions)
    {

        // Shuffle the deck
        long seed = System.nanoTime();
        Collections.shuffle(questions, new Random(seed));

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
