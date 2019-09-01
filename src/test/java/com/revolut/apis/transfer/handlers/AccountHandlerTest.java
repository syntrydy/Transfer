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
public class AccountHandlerTest {

  private AccountsHandler accountsHandler;

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
    accountsHandler = new AccountsHandler(eventBus, ActionType.LIST);
  }

  @Test
  public void addAccountTest(){
    accountsHandler = new AccountsHandler(eventBus,ActionType.ADD);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));

    accountsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.ADD_ACCOUNT),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

  @Test
  public void updateAccountTest(){
    accountsHandler = new AccountsHandler(eventBus,ActionType.EDIT);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));

    accountsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.EDIT_ACCOUNT),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

  @Test
  public void getAccountTest(){
    accountsHandler = new AccountsHandler(eventBus,ActionType.GET);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));
    Mockito.when(parameters.pathParameter("accountNumber")).thenReturn(RequestParameter.create("222"));

    accountsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.GET_ACCOUNT),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

  @Test
  public void getAccountsTest(){
    accountsHandler = new AccountsHandler(eventBus,ActionType.LIST);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));

    accountsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.GET_ACCOUNTS),Mockito.any(Object.class),Mockito.any(Handler.class));
  }

  @Test
  public void deleteAccountTest(){
    accountsHandler = new AccountsHandler(eventBus,ActionType.DELETE);
    Mockito.when(context.get("parsedParameters")).thenReturn(parameters);
    Mockito.when(context.response()).thenReturn(response);
    Mockito.when(parameters.body()).thenReturn(RequestParameter.create(parameters));
    Mockito.when(parameters.pathParameter("accountNumber")).thenReturn(RequestParameter.create("222"));

    accountsHandler.handle(context);;

    Mockito.verify(eventBus).send(Mockito.eq(EventBusAddress.DELETE_ACCOUNT),Mockito.any(Object.class),Mockito.any(Handler.class));
  }
}
