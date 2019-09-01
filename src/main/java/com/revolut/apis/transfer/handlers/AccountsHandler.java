package com.revolut.apis.transfer.handlers;

import com.revolut.apis.transfer.utils.ActionType;
import com.revolut.apis.transfer.utils.Constants;
import com.revolut.apis.transfer.utils.EventBusAddress;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.api.RequestParameters;

public class AccountsHandler implements Handler<RoutingContext> {
  public static final String ACCOUNT_NUMBER = "accountNumber";
  Logger logger = LoggerFactory.getLogger(AccountsHandler.class);
  private EventBus eventBus;
  private ActionType actionType;

  public AccountsHandler(EventBus eventBus,ActionType type) {
    this.eventBus = eventBus;
    this.actionType=type;
  }

  @Override
  public void handle(RoutingContext routingContext) {

    switch (actionType){
      case ADD:
        handleAccountAdd(routingContext);
        break;
      case EDIT:
        handleAccountUpdate(routingContext);
        break;
      case DELETE:
        handleAccountDelete(routingContext);
        break;
      case LIST:
        handleAccountList(routingContext);
        break;
      case GET:
        handleAccountGet(routingContext);
        break;
    }
    logger.info("Done");
  }

  private void handleAccountGet(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    String accountNumber= String.valueOf(params.pathParameter(ACCOUNT_NUMBER));
    JsonObject value=new JsonObject();
    value.put(ACCOUNT_NUMBER,accountNumber);
    sendMessage(routingContext, value,EventBusAddress.GET_ACCOUNT);
  }

  private void handleAccountDelete(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    String accountNumber= String.valueOf(params.pathParameter(ACCOUNT_NUMBER));
    JsonObject value=new JsonObject();
    value.put(ACCOUNT_NUMBER,accountNumber);
    sendMessage(routingContext, value,EventBusAddress.DELETE_ACCOUNT);
  }
  private void handleAccountList(RoutingContext routingContext) {
    sendMessage(routingContext, new JsonObject(),EventBusAddress.GET_ACCOUNTS);
  }

  private void handleAccountAdd(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    sendMessage(routingContext, params.body().getJsonObject(),EventBusAddress.ADD_ACCOUNT);
  }
  private void handleAccountUpdate(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    sendMessage(routingContext, params.body().getJsonObject(),EventBusAddress.EDIT_ACCOUNT);
  }

  private void sendMessage(RoutingContext routingContext, Object message,String address) {
    eventBus.send(address, message, reply -> {
      if (reply.succeeded()) {
        routingContext
          .response()
          .setStatusCode(200)
          .setStatusMessage("OK")
          .end(reply.result().body().toString());
      } else {
        routingContext
          .response()
          .setStatusCode(500)
          .setStatusMessage("NOT FOUND")
          .end("SERVER ERROR");
      }
    });
  }
}
