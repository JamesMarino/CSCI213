//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package au.edu.uow.UserInterface;

import au.edu.uow.QuestionLibrary.Question;
import au.edu.uow.UserInterface.Student;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Scanner in;
    private int qCounter = 1;

    public UserInterface() {
        this.in = new Scanner(System.in);
    }

    public Student getStudent() {
        Student student = new Student();
        System.out.print("Your name:");
        student.setName(this.in.nextLine());
        return student;
    }

    public void startQuiz(List<Question> quiz, Student student) {
        Iterator var4 = quiz.iterator();

        while(var4.hasNext()) {
            Question question = (Question)var4.next();
            System.out.println("\nQuestion No " + this.qCounter++ + ":\n");
            Iterator ans = question.getQuestion().iterator();

            while(ans.hasNext()) {
                String cNumber = (String)ans.next();
                System.out.println(cNumber);
            }

            System.out.println("\nAnswer Choices:");
            int var9 = 1;
            Iterator e = question.getChoices().iterator();

            while(e.hasNext()) {
                String var10 = (String)e.next();
                System.out.println(Integer.toString(var9++) + ": " + var10);
            }

            System.out.print("\nChoose your answer:");
            int var11 = 0;

            while(var11 == 0) {
                try {
                    var11 = this.in.nextInt();
                } catch (InputMismatchException var8) {
                    System.out.println("Please type a number as your choice!");
                    this.in.next();
                }
            }

            student.recordScore(question.compareAnswer(var11));
        }

    }

    public void showStudentMarks(Student student) {
        System.out.println("Result of " + student.getName() + ": " + student.getScore() + " out of " + (this.qCounter - 1));
    }
}
