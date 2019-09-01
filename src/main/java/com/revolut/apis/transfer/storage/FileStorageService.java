package com.revolut.apis.transfer.storage;

import com.revolut.apis.transfer.models.Account;
import com.revolut.apis.transfer.models.Customer;
import com.revolut.apis.transfer.models.Transaction;
import com.revolut.apis.transfer.service.StorageService;

import java.util.List;
import java.util.Optional;

public class FileStorageService implements StorageService {


  @Override
  public List<Transaction> getTransactions() {
    return null;
  }

  @Override
  public List<Account> getAccounts() {
    return null;
  }

  @Override
  public List<Customer> getCustomers() {
    return null;
  }

  @Override
  public Optional<Transaction> getTransaction(int transactionId) {
    return Optional.empty();
  }

  @Override
  public Optional<Account> getAccount(String accountNumber) {
    return Optional.empty();
  }

  @Override
  public Optional<Customer> getCustomer(int customerId) {
    return Optional.empty();
  }

  @Override
  public Transaction addTransaction(Transaction transaction) {
    return null;
  }

  @Override
  public Account addAccount(Account account) {
    return null;
  }

  @Override
  public Customer addCustomer(Customer customer) {
    return null;
  }

  @Override
  public boolean updateAccount(Account account) {
    return false;
  }

  @Override
  public boolean updateTransaction(Transaction transaction) {
    return false;
  }

  @Override
  public boolean updateCustomer(Customer customer) {
    return false;
  }

  @Override
  public boolean removeTransaction(int transactionId) {
    return false;
  }

  @Override
  public boolean removeAccount(String accountNumber) {
    return false;
  }

  @Override
  public boolean removeCustomer(String customerId) {
    return false;
  }
}
