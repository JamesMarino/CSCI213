package au.edu.uow.UserInterface;

import au.edu.uow.QuestionLibrary.Question;
import java.util.List;
import java.util.Scanner;

/**
 * Allows user access to Question Interface
 * @author James Marino
 */
public class UserInterface
{

    /**
     * User access into the quiz, asks questions
     * @param quiz List of questions
     * @param student Current student
     */
    public void startQuiz(List<Question> quiz, Student student)
    {
        Scanner reader = new Scanner(System.in);
        int answer;
        int questionCount = 1;
        int choiceCount = 1;

        System.out.print("\n");

        for (Question current : quiz) {

            System.out.println("Question No " + questionCount + ":" + "\n");
            System.out.print(current.getQuestion().iterator().next() + "\n");
            System.out.println("Answer Choices: ");

            for (String choice : current.getChoices()) {
                System.out.println(choiceCount + ": " + choice);
                choiceCount++;
            }

            System.out.print("\nChoose your answer: ");

            do {
                answer = reader.nextInt();
                if ((answer < 1) || (answer > choiceCount-1)) {
                    System.out.print("Invalid Choice. Choose your answer: ");
                }
            } while ((answer < 1) || (answer > choiceCount-1));

            System.out.print("\n");

            // Store response
            student.recordScore(current.compareAnswer(answer));

            // Reset choice count
            choiceCount = 1;

            questionCount++;

        }

        // Set size of quiz
        student.setQuestionCount(quiz.size());
    }

    /**
     * Display final Marks
     * @param student Current Student
     */
    public void showStudentMarks(Student student)
    {
        System.out.println("Result of " + student.getName() + ": " +
                            student.getScore() + " out of " + student.getQuestionCount());
    }

    /**
     * Getter - Student
     * @return current Student
     */
    public Student getStudent()
    {
        Scanner reader = new Scanner(System.in);
        System.out.print("Your name: ");
        String name = reader.nextLine();

        Student currentStudent;
        currentStudent = new Student();
        currentStudent.setName(name);

        return currentStudent;
    }

}
