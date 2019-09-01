package com.revolut.apis.transfer.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revolut.apis.transfer.models.common.TransactionStatus;
import io.vertx.codegen.annotations.DataObject;

import java.io.Serializable;
import java.util.Date;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
@DataObject(generateConverter = true, publicConverter = false)
public class Transaction implements Serializable {


  public static TransactionBuilder builder() {
    return new TransactionBuilder();
  }

  public static final class TransactionBuilder {
    private int id;
    private Date date=new Date();
    private Account sendingAccount;
    private Account receivingAccount;
    private double amount;
    private TransactionStatus status=TransactionStatus.STARTED;

    public TransactionBuilder withSendingAccount(Account sender) {
      this.sendingAccount = sender;
      return this;
    }

    public TransactionBuilder withReceivingAccount(Account receiver) {
      this.receivingAccount = receiver;
      return this;
    }

    public TransactionBuilder withDate(Date date) {
      this.date = date;
      return this;
    }
    public TransactionBuilder isNew(boolean isNew) {
      if(isNew){
        this.id = generateNewId();
      }
      return this;
    }

    private int generateNewId() {
      return (int)(Math.random() * 1000);
    }

    public TransactionBuilder withAmount(double amount) {
      this.amount = amount;
      return this;
    }

    public TransactionBuilder withStatus(TransactionStatus status) {
      this.status = status;
      return this;
    }


    public Transaction get() {
      Transaction transaction = new Transaction();
      transaction.date = this.date;
      transaction.amount = this.amount;
      transaction.sendingAccount = this.sendingAccount;
      transaction.receivingAccount = this.receivingAccount;
      transaction.status = this.status;
      transaction.id = this.id;
      return transaction;
    }
  }

  @JsonProperty
  private int id;
  @JsonProperty
  private Date date;
  @JsonProperty
  private Account sendingAccount;
  @JsonProperty
  private Account receivingAccount;
  @JsonProperty
  private double amount;
  @JsonProperty
  private TransactionStatus status= TransactionStatus.STARTED;

  private Transaction() {
  }

  public Date getDate() {
    return date;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public Account getSendingAccount() {
    return sendingAccount;
  }

  public Account getReceivingAccount() {
    return receivingAccount;
  }

  public double getAmount() {
    return amount;
  }

  public int getId() {
    return id;
  }

}
