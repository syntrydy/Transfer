package com.revolut.apis.transfer.service;

import com.revolut.apis.transfer.models.Account;
import com.revolut.apis.transfer.models.Customer;
import com.revolut.apis.transfer.models.Transaction;
import io.vertx.ext.web.api.generator.WebApiServiceGen;

import java.util.List;
import java.util.Optional;

@WebApiServiceGen
public interface StorageService {

  List<Transaction> getTransactions();
  List<Account> getAccounts();
  List<Customer> getCustomers();

  Optional<Transaction> getTransaction(int transactionId);
  Optional<Account> getAccount(String accountNumber);
  Optional<Customer> getCustomer(int customerId);

  Transaction addTransaction(Transaction transaction);
  Account addAccount(Account account);
  Customer addCustomer(Customer customer);


  boolean updateAccount(Account account);
  boolean updateTransaction(Transaction transaction);
  boolean updateCustomer(Customer customer);

  boolean removeTransaction(int transactionId);
  boolean removeAccount(String accountNumber);
  boolean removeCustomer(String customerId);

}
