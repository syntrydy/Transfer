package com.revolut.apis.transfer.handlers;

import com.revolut.apis.transfer.utils.ActionType;
import com.revolut.apis.transfer.utils.Constants;
import com.revolut.apis.transfer.utils.EventBusAddress;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.api.RequestParameters;

public class TransactionsHandler implements Handler<RoutingContext> {
  public static final String TRANSACTION_ID = "transactionId";
  public static final String ID = "id";
  Logger logger = LoggerFactory.getLogger(TransactionsHandler.class);
  private EventBus eventBus;
  private ActionType actionType;

  public TransactionsHandler(EventBus eventBus,ActionType type) {
    this.eventBus = eventBus;
    this.actionType=type;
  }

  @Override
  public void handle(RoutingContext routingContext) {

    switch (actionType){
      case ADD:
        handleTransactionAdd(routingContext);
        break;
      case EDIT:
        handleTransactionUpdate(routingContext);
        break;
      case DELETE:
        handleTransactionDelete(routingContext);
        break;
      case LIST:
        handleTransactionList(routingContext);
        break;
      case GET:
        handleTransactionGet(routingContext);
        break;
    }
    logger.info("Done");
  }

  private void handleTransactionGet(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    int id= Integer.valueOf(String.valueOf(params.pathParameter(TRANSACTION_ID)));
    JsonObject value=new JsonObject();
    value.put(ID,id);
    sendMessage(routingContext, value,EventBusAddress.GET_TRANSACTION);
  }

  private void handleTransactionDelete(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    int id= Integer.valueOf(String.valueOf(params.pathParameter(TRANSACTION_ID)));
    JsonObject value=new JsonObject();
    value.put(ID,id);
    sendMessage(routingContext, value,EventBusAddress.DELETE_TRANSACTION);
  }
  private void handleTransactionList(RoutingContext routingContext) {
    sendMessage(routingContext, new JsonObject(),EventBusAddress.GET_TRANSACTIONS);
  }

  private void handleTransactionAdd(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    sendMessage(routingContext, params.body().getJsonObject(),EventBusAddress.ADD_TRANSACTION);
  }
  private void handleTransactionUpdate(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    sendMessage(routingContext, params.body().getJsonObject(),EventBusAddress.EDIT_TRANSACTION);
  }

  private void sendMessage(RoutingContext routingContext, Object message,String address) {
    Handler<AsyncResult<Message<Object>>> asyncResultHandler = reply -> {
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
    };
    eventBus.send(address, message, asyncResultHandler);
  }
}
