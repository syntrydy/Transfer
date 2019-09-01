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

public class CustomersHandler implements Handler<RoutingContext> {
  public static final String USERNAME = "username";
  Logger logger = LoggerFactory.getLogger(CustomersHandler.class);
  private EventBus eventBus;
  private ActionType actionType;

  public CustomersHandler(EventBus eventBus,ActionType type) {
    this.eventBus = eventBus;
    this.actionType=type;
  }

  @Override
  public void handle(RoutingContext routingContext) {

    switch (actionType){
      case ADD:
        handleCustomerAdd(routingContext);
        break;
      case EDIT:
        handleCustomerUpdate(routingContext);
        break;
      case DELETE:
        handleCustomerDelete(routingContext);
        break;
      case LIST:
        handleCustomerList(routingContext);
        break;
      case GET:
        handleCustomerGet(routingContext);
        break;
    }
    logger.info("Done");
  }

  private void handleCustomerGet(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    String username= String.valueOf(params.pathParameter(USERNAME));
    JsonObject value=new JsonObject();
    value.put(USERNAME,username);
    sendMessage(routingContext, value,EventBusAddress.GET_CUSTOMER);
  }

  private void handleCustomerDelete(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    String userName= String.valueOf(params.pathParameter(USERNAME));
    JsonObject value=new JsonObject();
    value.put(USERNAME,userName);
    sendMessage(routingContext, value,EventBusAddress.DELETE_CUSTOMER);
  }
  private void handleCustomerList(RoutingContext routingContext) {
    sendMessage(routingContext, new JsonObject(),EventBusAddress.GET_CUSTOMERS);
  }

  private void handleCustomerAdd(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    logger.info(""+params);
    sendMessage(routingContext, params.body().getJsonObject(),EventBusAddress.ADD_CUSTOMER);
  }
  private void handleCustomerUpdate(RoutingContext routingContext) {
    RequestParameters params = routingContext.get(Constants.PARSED_PARAMETERS);
    sendMessage(routingContext, params.body().getJsonObject(),EventBusAddress.EDIT_CUSTOMER);
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
