package com.revolut.apis.transfer.models;


import io.vertx.junit5.VertxExtension;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;

@ExtendWith(VertxExtension.class)
public class TransactionTest {

  @Test
  public void newInstanceWithoutAmountTest(){
    Transaction transaction=Transaction.builder().get();
    Assert.assertTrue(transaction.getAmount()==0);
  }

  @Test
  public void newInstanceTest(){
    Date date = new Date();
    double amount = 2500;
    Account account1= Account.builder().withAccountNumber("1111").withBalance(amount).get();
    Account account2= Account.builder().withAccountNumber("2222").withBalance(0).get();

    Transaction transaction=Transaction.builder().withDate(date)
      .withSendingAccount(account1).withReceivingAccount(account2)
      .withAmount(amount).get();

    Assert.assertNotNull(transaction);
    Assert.assertEquals(transaction.getDate(),date);
    Assert.assertEquals((double)transaction.getAmount(),(double)amount,0);
    Assert.assertEquals(transaction.getSendingAccount().getBalance(),amount,0);
  }

}
