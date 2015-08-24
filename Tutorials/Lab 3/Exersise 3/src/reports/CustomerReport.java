package reports;

import domain.*;
import java.text.NumberFormat;
import java.util.*;

public class CustomerReport {

  public void generateReport() {
    NumberFormat currency_format = NumberFormat.getCurrencyInstance();



    Bank         bank = Bank.getBank(); 

    Customer     customer;

    // Iterator
    Iterator<Bank> iterBank = bank.getCustomers();
    Iterator<Customer> iterCustomer;
    Iterator<Account> iterAccount;

    System.out.println("\t\t\tCUSTOMERS REPORT");
    System.out.println("\t\t\t================");

    iterCustomer = bank.getCustomers();

          while (iterBank.hasNext()){

            iterBank.next();
            customer = iterCustomer.next();

            System.out.println();
            System.out.println("Customer: "
                   + customer.getLastName() + ", "
                   + customer.getFirstName());


            while (iterCustomer.hasNext()){
              iterAccount = customer.getAccounts();
                    Account account = iterAccount.next();
                    String  account_type = "";

                    // Determine the account type
                    if ( account instanceof SavingsAccount ) {
                      account_type = "Savings Account";
                    } else if ( account instanceof CheckingAccount ) {
                      account_type = "Checking Account";
                    } else {
                      account_type = "Unknown Account Type";
                    }

                    // Print the current balance of the account
                    System.out.println("    " + account_type + ": current balance is "
                             + currency_format.format(account.getBalance()));
                  }
          }
  }

}
