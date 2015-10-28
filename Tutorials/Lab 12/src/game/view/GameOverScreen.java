//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package game.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class GameOverScreen extends JComponent {
    private boolean isWin;
    private int luckyNumber;

    public GameOverScreen(boolean var1) {
        this.isWin = var1;
        this.luckyNumber = (int)(Math.random() * 4.0D);
    }

    public void paintComponent(Graphics var1) {
        Graphics2D var2 = (Graphics2D)var1;
        Font var4;
        FontMetrics var5;
        int var7;
        if(!this.isWin) {
            this.setBackground(Color.BLACK);
            var2.setColor(this.getBackground());
            var2.fillRect(0, 0, this.getWidth(), this.getHeight());
            var2.setPaint(Color.RED);
            var4 = new Font("SansSerif", 1, 40);
            var2.setFont(var4);
            var5 = var2.getFontMetrics(var4);
            int var6 = this.getWidth() / 2 - var5.stringWidth("Java Won !") / 2;
            var7 = this.getHeight() / 2 + var5.getHeight() / 2;
            var2.drawString("Java Won !", var6, var7);
            var2.setPaint(Color.ORANGE);
        } else {
            this.setBackground(Color.RED);
            String var3 = "";
            String var15 = "";
            String var16 = "";
            String var17 = "";
            switch(this.luckyNumber) {
                case 0:
                    this.setBackground(Color.BLUE);
                    var3 = "You have 3 hours in the final exam to answer the following questoins:";
                    var15 = "    20 multiple choice questions: 20 marks";
                    var16 = "    5 short answer questions: 10 marks";
                    var17 = "    4 coding questions: 20 marks";
                    break;
                case 1:
                    this.setBackground(Color.GRAY);
                    var3 = "How to prepare the Final Exam?";
                    var15 = "    Revise the topics and discussions in the lectures";
                    var16 = "       along with a good Java book";
                    var17 = "    Revise the tasks you have done in assignments";
                    break;
                case 2:
                    this.setBackground(Color.GREEN);
                    var3 = "How to pass the Subject?";
                    var15 = "    Total marks (assignments, labs and exam) >= 50";
                    var16 = "        && ";
                    var17 = "    Final Exam marks >= 20 (40% of the exam marks)";
                    break;
                case 3:
                    this.setBackground(Color.RED);
                    var3 = "Exam questions cover following topics: ";
                    var15 = "    Multiple choice and short answer questions: Weeks 1-13 lectures";
                    var16 = "    Program Coding: Java language features and basic tasks done in assignments";
                    var17 = "    Topics pointed out as important need to be remenbered";
            }

            var2.setColor(this.getBackground());
            var2.fillRect(0, 0, this.getWidth(), this.getHeight());
            var2.setPaint(Color.WHITE);
            Font var8 = new Font("SansSerif", 1, 40);
            var2.setFont(var8);
            FontMetrics var9 = var2.getFontMetrics(var8);
            int var10 = this.getWidth() / 2 - var9.stringWidth("You are ready to conquer the final exam !") / 2;
            int var11 = this.getHeight() / 2 + var9.getHeight() / 2;
            var11 -= this.getHeight() / 6;
            var2.drawString("You are ready to conquer the final exam !", var10, var11);
            var2.setPaint(Color.ORANGE);
            var8 = new Font("SansSerif", 1, 20);
            var2.setFont(var8);
            var9 = var2.getFontMetrics(var8);
            int var12 = var9.getHeight();
            var2.drawString(var3, var10, (int)((double)var11 + 1.1D * (double)var12));
            var2.drawString(var15, var10, (int)((double)var11 + 2.2D * (double)var12));
            var2.drawString(var16, var10, (int)((double)var11 + 3.3000000000000003D * (double)var12));
            var2.drawString(var17, var10, (int)((double)var11 + 4.4D * (double)var12));
            var2.setPaint(Color.BLACK);
        }

        var4 = new Font("SansSerif", 1, 15);
        var2.setFont(var4);
        var5 = var2.getFontMetrics(var4);
        var7 = this.getWidth() - var5.stringWidth("Ctl-C or Esc to exit") - 20;
        int var18 = this.getHeight() - var5.getHeight() - 20;
        var2.drawString("Ctl-C or Esc to exit", var7, var18);
    }
}
