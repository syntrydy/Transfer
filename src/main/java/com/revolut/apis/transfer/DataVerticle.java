package com.revolut.apis.transfer;

import com.revolut.apis.transfer.models.Account;
import com.revolut.apis.transfer.models.Customer;
import com.revolut.apis.transfer.models.Transaction;
import com.revolut.apis.transfer.models.common.AccountStatus;
import com.revolut.apis.transfer.models.common.Currency;
import com.revolut.apis.transfer.models.common.TransactionStatus;
import com.revolut.apis.transfer.service.StorageService;
import com.revolut.apis.transfer.storage.InMemoryStorageService;
import com.revolut.apis.transfer.storage.data.StorageProvider;
import com.revolut.apis.transfer.utils.Constants;
import com.revolut.apis.transfer.utils.EventBusAddress;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.Date;

public class DataVerticle extends AbstractVerticle {
  Logger logger = LoggerFactory.getLogger("==============DataService");
  StorageService storageService;

  @Override
  public void start() {
    storageService = new InMemoryStorageService();
    final EventBus eventBus = vertx.eventBus();
    eventBus.consumer(EventBusAddress.ADD_TRANSACTION).handler(objectMessage -> {
      logger.info("Adding new transaction!");
      JsonObject json = (JsonObject) objectMessage.body();
      objectMessage.reply(Json.encode(storageService.addTransaction(json.mapTo(Transaction.class))));
      logger.info("Done!");
    });

    eventBus.consumer(EventBusAddress.EDIT_TRANSACTION).handler(objectMessage -> {
      logger.info("Editing new transaction!");
      JsonObject json = (JsonObject) objectMessage.body();
      objectMessage.reply(Json.encode(storageService.updateTransaction(json.mapTo(Transaction.class))));
      logger.info("Done!");
    });

    eventBus.consumer(EventBusAddress.GET_TRANSACTION).handler(objectMessage -> {
      logger.info("Fetching transaction!");
      JsonObject json = (JsonObject) objectMessage.body();
      objectMessage.reply(Json.encode(storageService.getTransaction(json.getInteger("id")).get()));
      logger.info("Done!");
    });

    eventBus.consumer(EventBusAddress.DELETE_TRANSACTION).handler(objectMessage -> {
      logger.info("Removing transaction!");
      JsonObject json = (JsonObject) objectMessage.body();
      objectMessage.reply(Json.encode(storageService.removeTransaction(json.getInteger("id"))));
      logger.info("Done!");
    });
    eventBus.consumer(EventBusAddress.GET_TRANSACTIONS).handler(objectMessage -> {
      logger.info("------Getting all transactions !-------");
      objectMessage.reply(Json.encode(storageService.getTransactions()));
      logger.info("-----Done!----");
    });
    eventBus.consumer(EventBusAddress.GET_ACCOUNTS).handler(objectMessage -> {
      logger.info("Getting all accounts !");
      objectMessage.reply(Json.encode(storageService.getAccounts()));
      logger.info("Done!");
    });
    eventBus.consumer(EventBusAddress.EDIT_ACCOUNT).handler(objectMessage -> {
      logger.info("Editing an account!");
      JsonObject json = (JsonObject) objectMessage.body();
      objectMessage.reply(Json.encode(storageService.updateAccount(json.mapTo(Account.class))));
      logger.info("Done!");
    });

    eventBus.consumer(EventBusAddress.GET_ACCOUNT).handler(objectMessage -> {
      logger.info("Fetching transaction!");
      JsonObject json = (JsonObject) objectMessage.body();
      objectMessage.reply(Json.encode(storageService.getAccount(json.getString("accountNumber")).get()));
      logger.info("Done!");
    });
    eventBus.consumer(EventBusAddress.GET_CUSTOMERS).handler(objectMessage -> {
      logger.info("Getting all customers !");
      objectMessage.reply(Json.encode(storageService.getCustomers()));
      logger.info("Done!");
    });
    createDefaultAccounts();
    createDefaultCustomers();
    logger.info("Data service started!");
  }



  private void createDefaultAccounts(){
    Account sendingAccount=Account.builder().withAccountNumber(Constants.DEFAULT_SENDING_ACCOUNT_NUMBER).
      withBalance(10_000_000).
      withCurrency(Currency.DOLLAR).
      withState(AccountStatus.ACTIVE).get();
    Account receivingAccount=Account.builder().withAccountNumber(Constants.DEFAULT_RECEIVING_ACCOUNT_NUMBER).
      withBalance(0).
      withCurrency(Currency.DOLLAR).
      withState(AccountStatus.ACTIVE).get();
    StorageProvider.accounts.put(sendingAccount.getAccountNumber(),sendingAccount);
    StorageProvider.accounts.put(receivingAccount.getAccountNumber(),receivingAccount);
  }

  private void createDefaultCustomers(){
    Customer sender=Customer.builder().withUserName("james")
      .withFistName("Cashme").withLastName("Tony").withPhoneNumber("+237838388828828")
      .withAddress("23 Austin").withEmail("james@cashmere.com")
      .withAccount(StorageProvider.accounts.get(Constants.DEFAULT_SENDING_ACCOUNT_NUMBER)).get();
    Customer receiver=Customer.builder().withUserName("smith")
      .withFistName("debaro").withLastName("Gandy").withPhoneNumber("+237848848844")
      .withAddress("64 AV NY").withEmail("smith@grandy.com")
      .withAccount(StorageProvider.accounts.get(Constants.DEFAULT_RECEIVING_ACCOUNT_NUMBER
      )).get();
    StorageProvider.customers.put(sender.getUserName(),sender);
    StorageProvider.customers.put(receiver.getUserName(),receiver);
  }

  private void createDefaultTransactions(){
    Transaction transaction=Transaction.builder().withAmount(10)
      .withSendingAccount(StorageProvider.accounts.get(Constants.DEFAULT_SENDING_ACCOUNT_NUMBER))
      .withReceivingAccount(StorageProvider.accounts.get(Constants.DEFAULT_RECEIVING_ACCOUNT_NUMBER))
      .withDate(new Date()).isNew(true)
      .withStatus(TransactionStatus.CLOSED).get();
    StorageProvider.transactions.put(transaction.getId(),transaction);
  }
}
