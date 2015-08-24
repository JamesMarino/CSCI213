package banking;

public class CheckingAccount extends Account {
  private static final double NO_PROTECTION = -1.0;

  private double overdraftProtection;

  public CheckingAccount(double bal, double protect) {
    super(bal);
    overdraftProtection = protect;
  }
  public CheckingAccount(double bal) {
    this(bal, NO_PROTECTION);
  }

  public boolean withdraw(double amount) {
    boolean result = true;

    if ( balance < amount ) {

      // Not enough balance to cover the amount requested to withdraw
      // Check if there is enough in the overdraft protection (if any)
      double overdraftNeeded = amount - balance;
      if (   (overdraftProtection == NO_PROTECTION)
	  || (overdraftProtection < overdraftNeeded)   ) {

	// No overdraft protection or not enough to cover the amount needed
	result = false;
      } else {

	// Yes, there is overdraft protection and enough to cover the amount
	balance = 0.0;
	overdraftProtection = overdraftProtection - overdraftNeeded;
      }

    } else {

      // Yes, there is enough balance to cover the amount
      // Proceed as usual
      balance = balance - amount;
    }

    return result;
  }
}
