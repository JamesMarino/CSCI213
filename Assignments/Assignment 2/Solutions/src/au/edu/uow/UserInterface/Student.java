//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package au.edu.uow.UserInterface;

public class Student {
    private String name;
    private int score = 0;

    public Student() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void recordScore(boolean isCorrect) {
        if(isCorrect) {
            ++this.score;
        }

    }

    public int getScore() {
        return this.score;
    }
}
