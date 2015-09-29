import au.edu.uow.QuestionLibrary.Question;
import au.edu.uow.QuestionLibrary.QuestionLibrary;
import au.edu.uow.UserInterfaceGUI.QuizGUIFrame;
import au.edu.uow.UserInterfaceGUI.Student;

import java.util.List;

public class JavaQuizGUI
{
    public static void main(String[] args)
    {
        QuizGUIFrame frame = new QuizGUIFrame("Java Quiz");

        frame.addWelcomeScreen();
        frame.showFrame();

        /*
        frame.showQuestion();
        frame.showFrame();

        frame.showMarksResult();
        frame.showFrame();

        QuestionLibrary myQuestionDB = new QuestionLibrary();
        boolean questionList = myQuestionDB.buildLibrary("/Users/james/UOW/CSCI213 (Java)/Assignments/Assignment 3/Solutions/src/data/questions.xml");


        List<Question> quiz = myQuestionDB.makeQuiz(5);

        Student student = new Student();
        student.setName("Blah");


        System.out.println(questionList);
        */

    }
}
