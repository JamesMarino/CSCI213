package exercise1.banking;

public class Account {

    private double Balance;

    public Account(double init_balance)
    {
        Balance = init_balance;
    }

    public double getBalance()
    {
        return Balance;
    }

    public void deposit(double amount)
    {
        Balance += amount;
    }

    public void withdraw(double amount)
    {
        Balance -= amount;
    }

}
