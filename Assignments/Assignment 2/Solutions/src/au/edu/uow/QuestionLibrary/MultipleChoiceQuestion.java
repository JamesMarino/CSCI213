//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package au.edu.uow.QuestionLibrary;

import au.edu.uow.QuestionLibrary.Question;
import java.util.List;

public class MultipleChoiceQuestion implements Question {
    private List<String> question;
    private List<String> choices;
    private int ans;

    public MultipleChoiceQuestion(List<String> q, List<String> c, int ans) {
        this.question = q;
        this.choices = c;
        this.ans = ans;
    }

    public List<String> getQuestion() {
        return this.question;
    }

    public List<String> getChoices() {
        return this.choices;
    }

    public boolean compareAnswer(int ans) {
        return this.ans == ans;
    }

    public int getAnswer() {
        return this.ans;
    }
}
