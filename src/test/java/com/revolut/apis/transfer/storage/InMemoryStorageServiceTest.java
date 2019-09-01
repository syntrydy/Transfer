package com.revolut.apis.transfer.storage;


import com.revolut.apis.transfer.storage.data.StorageProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StorageProvider.class)
public class InMemoryStorageServiceTest {

  private InMemoryStorageService inMemoryStorageService;

  @Before
  public void setUp(){
    PowerMockito.mockStatic(StorageProvider.class);
    inMemoryStorageService = new InMemoryStorageService();
  }

  @Test
  public void getTransactionsTest() {

    inMemoryStorageService.getTransactions();

    Mockito.when(StorageProvider.getTransactions()).thenCallRealMethod();
  }
}
