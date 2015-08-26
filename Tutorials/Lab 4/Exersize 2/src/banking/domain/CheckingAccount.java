package banking.domain;

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

  public void withdraw(double amount) throws OverdraftException {
    double result = balance - amount;

    if ( balance < amount ) {

      
      if (   (overdraftProtection == NO_PROTECTION)
	  || (overdraftProtection <amount - balance)   ) {

	throw new OverdraftException("No overdraft Protection", overdraftProtection);
	// No overdraft protection or not enough to cover the amount needed
	
      } else {

	// Yes, there is overdraft protection and enough to cover the amount
	throw new OverdraftException ("Insufficient funds for overdraft protection", result);
      }

    } else {

      // Yes, there is enough balance to cover the amount
      // Proceed as usual
      balance = balance - amount;
    }
  }
}
