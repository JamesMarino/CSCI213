//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package au.edu.uow.QuestionLibrary;

import java.util.List;

public interface Question {
    List<String> getQuestion();

    List<String> getChoices();

    boolean compareAnswer(int var1);

    int getAnswer();
}
