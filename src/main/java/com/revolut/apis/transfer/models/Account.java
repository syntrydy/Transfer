package com.revolut.apis.transfer.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.revolut.apis.transfer.models.common.AccountStatus;
import com.revolut.apis.transfer.models.common.Currency;
import io.vertx.codegen.annotations.DataObject;

import java.io.Serializable;


@DataObject(generateConverter = true, publicConverter = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect
public class Account implements Serializable {
  private String accountNumber;
  private double balance;
  private AccountStatus status=AccountStatus.ACTIVE;
  private Currency currency=Currency.DOLLAR;
  private Customer owner;
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String accountNumber;
    private double balance;
    private AccountStatus status=AccountStatus.ACTIVE;
    private Currency currency=Currency.DOLLAR;
    private Customer owner;

    public Builder withAccountNumber(String number) {
      this.accountNumber = number;
      return this;
    }

    public Builder withBalance(double balance) {
      this.balance = balance;
      return this;
    }

    public Builder withState(AccountStatus status) {
      this.status = status;
      return this;
    }

    public Builder withCurrency(Currency currency) {
      this.currency = currency;
      return this;
    }

    public Builder withOwner(Customer customer) {
      this.owner = customer;
      return this;
    }

    public Account get() {
      Account account = new Account();
      account.accountNumber=this.accountNumber;
      account.balance=this.balance;
      account.status=this.status;
      account.currency=currency;
      account.owner=owner;
      return account;
    }
  }

  private Account(){
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance=balance;
  }
  public boolean canSendMoney(double amount){
    return this.getBalance()>=amount && this.status.name().equalsIgnoreCase(AccountStatus.ACTIVE.name());
  }
  public boolean canReceiveMoney(){
    return this.status.name().equalsIgnoreCase(AccountStatus.ACTIVE.name());
  }
  public AccountStatus getStatus() {
    return status;
  }
  public Currency getCurrency() {
    return currency;
  }

  public Customer getOwner() {
    return owner;
  }
}
