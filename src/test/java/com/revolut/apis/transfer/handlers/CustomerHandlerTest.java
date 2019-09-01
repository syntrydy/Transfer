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
public class CustomerHandlerTest {

  private CustomersHandler transactionsHandler;

  @Mock
  private RoutingContext context;

  @Mock
  private RequestParameters parameters;

  @Mock
  private EventBus eventBus;

  @Mock
  private HttpServerResponse response;

  @Before
  public void setUp(){
    transactionsHandler = new CustomersHandler(eventBus, ActionType.LIST);
  }

  @Test
  public void addCustomerTest(){
    transactionsHandler = new CustomersHandler(eventBus,ActionType.ADD);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));

    transactionsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.ADD_CUSTOMER),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

  @Test
  public void updateCustomerTest(){
    transactionsHandler = new CustomersHandler(eventBus,ActionType.EDIT);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));

    transactionsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.EDIT_CUSTOMER),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

  @Test
  public void getCustomerTest(){
    transactionsHandler = new CustomersHandler(eventBus,ActionType.GET);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));
    Mockito.when(parameters.pathParameter("transactionId")).thenReturn(RequestParameter.create("222"));

    transactionsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.GET_CUSTOMER),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

  @Test
  public void getCustomersTest(){
    transactionsHandler = new CustomersHandler(eventBus,ActionType.LIST);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));

    transactionsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.GET_CUSTOMERS),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

  @Test
  public void deleteCustomerTest(){
    transactionsHandler = new CustomersHandler(eventBus,ActionType.DELETE);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));
    Mockito.when(parameters.pathParameter("username")).thenReturn(RequestParameter.create("222"));

    transactionsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.DELETE_CUSTOMER),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

}
