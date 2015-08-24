package domain;

import java.util.*;

public class Customer {
    // Data Attributes
    private List<Account> account;
    private String   firstName;
    private String   lastName;

    private int accountCount;
    private static int MAX_ACCOUNTS = 2;

    public Customer(String f, String l) {
        firstName = f;
        lastName = l;

        account = new ArrayList<>();
        accountCount = 0;
    }


    public boolean addAccount(Account acc)
    {
        if (accountCount < 2) {
            account.add(acc);
            accountCount++;
            return true;
        } else {
            return false;
        }
    }

    public Account getAccount(int i)
    {
        return account.get(i);
    }

    public Iterator getAccounts()
    {
        Iterator<Account> iter = account.iterator();
        return iter;
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
