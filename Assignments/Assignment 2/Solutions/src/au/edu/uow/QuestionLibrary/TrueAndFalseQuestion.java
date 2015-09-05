//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package au.edu.uow.QuestionLibrary;

import au.edu.uow.QuestionLibrary.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrueAndFalseQuestion implements Question {
    private List<String> question;
    private String ans;
    private static final List<String> choices = new ArrayList(Arrays.asList(new String[]{"True", "False"}));

    public TrueAndFalseQuestion(List<String> q, String ans) {
        this.question = q;
        this.ans = ans;
    }

    public List<String> getQuestion() {
        return this.question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public boolean compareAnswer(int ans) {
        return this.ans.equalsIgnoreCase("true") && ans == 1 || this.ans.equalsIgnoreCase("false") && ans == 2;
    }

    public int getAnswer() {
        return this.ans.equalsIgnoreCase("true")?1:2;
    }
}
