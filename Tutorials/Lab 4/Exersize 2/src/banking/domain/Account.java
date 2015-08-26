package banking.domain;

public class Account {

  protected double   balance;

  public Account(double bal) {
    balance = bal;
  }

  public double getBalance() {
    return balance;
  }
  public boolean deposit(double amount) {
    balance = balance + amount;
    return true;
  }
  public void withdraw(double amount) throws OverdraftException {
    if ( balance < amount ) {
      throw new OverdraftException ("Insufficient funds",balance-amount);
    } else {
      balance = balance - amount;
    }
  }
}
