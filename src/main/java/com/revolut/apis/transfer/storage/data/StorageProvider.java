package com.revolut.apis.transfer.storage.data;

import com.revolut.apis.transfer.models.Account;
import com.revolut.apis.transfer.models.Customer;
import com.revolut.apis.transfer.models.Transaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StorageProvider {
  public static Map<Integer, Transaction> transactions=new ConcurrentHashMap<>();
  public static Map<String, Customer> customers=new ConcurrentHashMap<>();
  public static Map<String, Account> accounts=new ConcurrentHashMap<>();

  public static Map<Integer, Transaction> getTransactions() {
    return transactions;
  }

  public static Map<String, Customer> getCustomers() {
    return customers;
  }

  public static Map<String, Account> getAccounts() {
    return accounts;
  }
}
