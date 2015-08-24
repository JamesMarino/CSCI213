package domain;

import java.util.*;

public class Bank {
    private static int MAX_CUSTOMERS = 10;
    private static double SAVINGS_RATE = 3.5;

    private static final Bank bankInstance = new Bank();

    private List<Customer> customers;

    public static Bank getBank() {
        return bankInstance;
    }

    private Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(String f, String l) {
        customers.add(new Customer(f, l));
    }
    public Customer getCustomer(int customer_index) {
        return customers.get(customer_index);
    }
    public Iterator getCustomers() {
        Iterator<Customer> iter = customers.iterator();
        return iter;
    }
    public int getNumOfCustomers() {
        return customers.size();
    }
}
