package banking.domain;
import java.util.*;

public class Bank {
  private static int MAX_CUSTOMERS = 10;
  private static double SAVINGS_RATE = 3.5;
  private static final Bank bank_instance = new Bank();

  private List customers;
  
  private Bank() {
    customers = new ArrayList();
  }

 
  public void addCustomer(String f, String l) {
    customers.add(new Customer(f,l));
  }
  public Customer getCustomer(int customer_index) {
    return (Customer )customers.get(customer_index);
  }
  public int getNumOfCustomers() {
    return customers.size();
  }

  public void sortCustomers ()
  {



    Collections.sort(customers);
  }

  public static Bank getBank(){
	return bank_instance;}
}

