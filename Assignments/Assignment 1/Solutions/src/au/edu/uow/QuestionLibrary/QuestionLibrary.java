package au.edu.uow.QuestionLibrary;

import java.util.*;

/**
 * Handles question management
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */
public class QuestionLibrary
{
    // Private Properties
    private static List<Question> questions;

    /**
     * Builds whole question list from file
     * @param qFile File name
     * @return success of read
     */
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

    /**
     * Getter - Final question list
     * @param noOfQuestions to make from full question list
     * @return the quiz to be used
     */
    public static List<Question> makeQuiz(int noOfQuestions)
    {

        // Shuffle the deck
        long seed = System.nanoTime();
        Collections.shuffle(questions, new Random(seed));

        List<Question> quiz = new ArrayList<Question>();
        Iterator<Question> iterator = questions.iterator();

        for (int i = 0; i < noOfQuestions; i++) {
            if (iterator.hasNext()) {
                quiz.add(iterator.next());
            }
        }

        return quiz;
    }
}
