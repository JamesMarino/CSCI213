package banking.domain;
import java.util.*;


public class Customer {

  // Data Attributes
  private String   firstName;
  private String   lastName;
  // Association Attributes
  private List accounts;
  private int       numberOfAccounts = 0;

  
  public Customer(String f, String l) {
    firstName = f;
    lastName = l;
    accounts = new ArrayList();
  }

  public String getFirstName() {
    return firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void addAccount(Account acct) {
    accounts.add(acct);
  }
  public Account getAccount(int account_index) {
    return (Account) accounts.get(account_index);
  }
  public int getNumOfAccounts() {
    return accounts.size();
  }
}
