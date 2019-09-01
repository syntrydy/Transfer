package com.revolut.apis.transfer.storage;

import com.revolut.apis.transfer.models.Account;
import com.revolut.apis.transfer.models.Customer;
import com.revolut.apis.transfer.models.Transaction;
import com.revolut.apis.transfer.models.common.TransactionStatus;
import com.revolut.apis.transfer.service.StorageService;
import com.revolut.apis.transfer.storage.data.StorageProvider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryStorageService implements StorageService {
  public InMemoryStorageService() {
  }

  @Override
  public List<Transaction> getTransactions() {
    return StorageProvider.getTransactions().values().stream().collect(Collectors.toList());
  }

  @Override
  public List<Account> getAccounts() {
    return StorageProvider.getAccounts().values().stream().collect(Collectors.toList());
  }

  @Override
  public List<Customer> getCustomers() {
    return StorageProvider.getCustomers().values().stream().collect(Collectors.toList());
  }

  @Override
  public Optional<Transaction> getTransaction(int transactionId) {
    return Optional.of(StorageProvider.getTransactions().get(transactionId));
  }

  @Override
  public Optional<Account> getAccount(String accountNumber) {
    return Optional.of(StorageProvider.getAccounts().get(accountNumber));
  }

  @Override
  public Optional<Customer> getCustomer(int customerId) {
    return Optional.of(StorageProvider.getCustomers().get(customerId));
  }

  @Override
  public Transaction addTransaction(Transaction transaction) {
    Account sendingAccount=transaction.getSendingAccount();
    Account receivingAccount=transaction.getReceivingAccount();
    if(sendingAccount !=null && receivingAccount!=null && transaction.getAmount()>0){
      sendingAccount=StorageProvider.getAccounts().get(sendingAccount.getAccountNumber());
      receivingAccount=StorageProvider.getAccounts().get(receivingAccount.getAccountNumber());
      synchronized(this){
        if(sendingAccount!=null && sendingAccount.canSendMoney(transaction.getAmount()) && receivingAccount!=null && receivingAccount.canReceiveMoney() ) {
          sendingAccount.setBalance(sendingAccount.getBalance() - transaction.getAmount());
          receivingAccount.setBalance(receivingAccount.getBalance() + transaction.getAmount());
          transaction=Transaction.builder()
            .withSendingAccount(sendingAccount).withReceivingAccount(receivingAccount)
            .withStatus(TransactionStatus.CLOSED).withAmount(transaction.getAmount()).isNew(true).get();
          StorageProvider.getAccounts().put(sendingAccount.getAccountNumber(), sendingAccount);
          StorageProvider.getAccounts().put(receivingAccount.getAccountNumber(), receivingAccount);
          StorageProvider.getTransactions().put(transaction.getId(), transaction);
          return transaction;
        }
      }
    }
    return null;
  }

  @Override
  public Account addAccount(Account account) {
    StorageProvider.getAccounts().put(account.getAccountNumber(),account);
    return account;
  }

  @Override
  public Customer addCustomer(Customer customer) {
    StorageProvider.getCustomers().put(customer.getUserName(),customer);
    return customer;
  }

  @Override
  public boolean updateTransaction(Transaction transaction) {
    Transaction exitingTransaction=StorageProvider.getTransactions().get(transaction.getId());
    if(exitingTransaction!=null){
      StorageProvider.getTransactions().put(transaction.getId(),transaction);
      return true;
    }else{
      return false;
    }
  }

  @Override
  public boolean updateAccount(Account account) {
    Account exitingAccount=StorageProvider.getAccounts().get(account.getAccountNumber());
    if(exitingAccount!=null){
      StorageProvider.getAccounts().put(account.getAccountNumber(),account);
      return true;
    }else{
      return false;
    }
  }

  @Override
  public boolean updateCustomer(Customer customer) {
    Customer exitingCustomer=StorageProvider.getCustomers().get(customer.getUserName());
    if(exitingCustomer!=null){
      StorageProvider.getCustomers().put(customer.getUserName(),customer);
      return true;
    }else{
      return false;
    }
  }

  @Override
  public boolean removeTransaction(int transactionId) {
    Transaction transaction=StorageProvider.getTransactions().remove(transactionId);
    return transaction!=null?true:false;
  }

  @Override
  public boolean removeAccount(String accountNumber) {
    Account account=StorageProvider.getAccounts().remove(accountNumber);
    return account!=null?true:false;
  }

  @Override
  public boolean removeCustomer(String userName) {
    Customer customer=StorageProvider.getCustomers().remove(userName);
    return customer!=null?true:false;
  }

}
