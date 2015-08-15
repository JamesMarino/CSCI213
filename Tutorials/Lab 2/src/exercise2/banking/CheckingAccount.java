package exercise2.banking;

public class CheckingAccount extends Account {

    private double overdraftProtection;

    public CheckingAccount(double balance)
    {
        super(balance);
    }

    public CheckingAccount(double balance, double protect)
    {
        super(balance);
        overdraftProtection = protect;
    }

    public boolean withdraw(double amount)
    {
        // balance = 400;
        // amount = 500;
        // balance < amount then we have to check whether the overdraft can cover the difference 500 - 400 = 100
        // A >= B is equal to !(A < B)

        double difference = amount - balance;

        if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            if (difference <= overdraftProtection) {
                overdraftProtection -= difference;
                balance = 0;
                return true;
            } else {
                return false;
            }
        }
    }

}
