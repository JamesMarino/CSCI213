package banking;

public class Customer {
  // Data Attributes
  private Account[]  account;
  private String   firstName;
  private String   lastName;

  private int accountCount;
  private static int MAX_ACCOUNTS = 2;

  public Customer(String f, String l) {
    firstName = f;
    lastName = l;

      accountCount = 0;
      account = new Account[MAX_ACCOUNTS];
  }


  public boolean addAccount(Account acc)
  {
      if (accountCount < 2) {
          account[accountCount] = acc;
          accountCount++;
          return true;
      } else {
          return false;
      }
  }

  public Account getAccount(int i)
  {
    return account[i];
  }

  public int getNumOfAccounts()
  {
    return accountCount;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

    /*
  public void setAccount(Account acct)
  {
    account = acct;
  }
  */
}
