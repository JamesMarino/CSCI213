package banking.domain;

public class Customer {

  // Data Attributes
  private String   firstName;
  private String   lastName;
  // Association Attributes
  private Account[] accounts = new Account[5];
  private int       numberOfAccounts = 0;

  public Customer(String f, String l) {
    firstName = f;
    lastName = l;
  }

  public String getFirstName() {
    return firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void addAccount(Account acct) {
    int i = numberOfAccounts++;
    accounts[i] = acct;
  }
  public Account getAccount(int account_index) {
    return accounts[account_index];
  }
  public int getNumOfAccounts() {
    return numberOfAccounts;
  }
}
