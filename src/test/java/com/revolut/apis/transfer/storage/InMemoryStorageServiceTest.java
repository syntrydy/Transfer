package com.revolut.apis.transfer.storage;


import org.junit.Before;
import org.junit.Test;

public class InMemoryStorageServiceTest {

  private InMemoryStorageService inMemoryStorageService;

  @Before
  public void setUp(){
    inMemoryStorageService = new InMemoryStorageService();
  }

  @Test
  public void getTransactionsTest() {

    inMemoryStorageService.getTransactions();

    //TODO

  }
}
