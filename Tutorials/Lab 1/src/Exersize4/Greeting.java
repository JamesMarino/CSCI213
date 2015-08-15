public class Greeting
{
    private String salutation;

    Greeting(String s)
    {
        salutation = s;
    }

    public void greet(String whom)
    {
        if (validate()) {
            System.out.println("You can not say that!");
        } else {
            System.out.println(salutation + " " + whom);
        }
    }

    private boolean validate()
    {
        return salutation.equals("Hi") || salutation.equals("Hello");
    }
}
