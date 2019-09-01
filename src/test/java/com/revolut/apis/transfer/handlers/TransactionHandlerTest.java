package com.revolut.apis.transfer.handlers;


import com.revolut.apis.transfer.utils.ActionType;
import com.revolut.apis.transfer.utils.EventBusAddress;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.api.RequestParameter;
import io.vertx.ext.web.api.RequestParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionHandlerTest {

  private TransactionsHandler transactionsHandler;

  @Mock
  private RoutingContext context;

  @Mock
  private RequestParameters parameters;

  @Mock
  private EventBus  eventBus;

  @Mock
  private HttpServerResponse response;

  @Before
  public void setUp(){
    transactionsHandler = new TransactionsHandler(eventBus,ActionType.LIST);
  }

  @Test
  public void addTransactionTest(){
    transactionsHandler = new TransactionsHandler(eventBus,ActionType.ADD);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));

    transactionsHandler.handle(context);;

   Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.ADD_TRANSACTION),Mockito.eq(null),Mockito.any(Handler.class));
  }

  @Test
  public void updateTransactionTest(){
    transactionsHandler = new TransactionsHandler(eventBus,ActionType.EDIT);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));

    transactionsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.EDIT_TRANSACTION),Mockito.eq(null),Mockito.any(Handler.class));
  }

  @Test
  public void getTransactionTest(){
    transactionsHandler = new TransactionsHandler(eventBus,ActionType.GET);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));
    Mockito.when(parameters.pathParameter("transactionId")).thenReturn(RequestParameter.create("222"));

    transactionsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.GET_TRANSACTION),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

  @Test
  public void getTransactionsTest(){
    transactionsHandler = new TransactionsHandler(eventBus,ActionType.LIST);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));

    transactionsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.GET_TRANSACTIONS),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

  @Test
  public void deleteTransactionTest(){
    transactionsHandler = new TransactionsHandler(eventBus,ActionType.DELETE);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));
    Mockito.when(parameters.pathParameter("transactionId")).thenReturn(RequestParameter.create("222"));

    transactionsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.DELETE_TRANSACTION),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

}
