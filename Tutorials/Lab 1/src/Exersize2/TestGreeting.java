/*
 * Observe the time stamp changes of .class files. Which .class file has changed?
 * A: Only Exersize2.TestGreeting.java has been recompiled
 */

public class TestGreeting
{
    public static void main(String[] args)
    {
        if (args.length > 0) {
            Greeting hello = new Greeting(args[0]);
            hello.greet("World");
        }
    }
}