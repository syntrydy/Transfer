package com.revolut.apis.transfer;

import com.revolut.apis.transfer.handlers.AccountsHandler;
import com.revolut.apis.transfer.handlers.CustomersHandler;
import com.revolut.apis.transfer.handlers.TransactionsHandler;
import com.revolut.apis.transfer.models.Transaction;
import com.revolut.apis.transfer.models.common.TransactionStatus;
import com.revolut.apis.transfer.storage.data.StorageProvider;
import com.revolut.apis.transfer.utils.ActionType;
import com.revolut.apis.transfer.utils.Constants;
import com.revolut.apis.transfer.utils.EventBusAddress;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.api.contract.RouterFactoryOptions;
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.Arrays;
import java.util.Date;

public class TransferVerticle extends AbstractVerticle {
  HttpServer server;
  Logger logger = LoggerFactory.getLogger("=============TransactionService");
  public void start(Future future) {
    OpenAPI3RouterFactory.create(this.vertx, "src/main/resources/transfer.yaml", openAPI3RouterFactoryAsyncResult -> {
      if (openAPI3RouterFactoryAsyncResult.failed()) {
        Throwable exception = openAPI3RouterFactoryAsyncResult.cause();
        logger.error("Oops, something went wrong during factory initialization", exception);
      }
      OpenAPI3RouterFactory routerFactory = openAPI3RouterFactoryAsyncResult.result();
      routerFactory.setOptions(new RouterFactoryOptions().setMountResponseContentTypeHandler(true));
      final EventBus eventBus = vertx.eventBus();
      routerFactory.addHandlerByOperationId("getTransactions", new TransactionsHandler(eventBus, ActionType.LIST));
      routerFactory.addHandlerByOperationId("getTransaction", new TransactionsHandler(eventBus, ActionType.GET));
      routerFactory.addHandlerByOperationId("addTransaction", new TransactionsHandler(eventBus, ActionType.ADD));
      routerFactory.addHandlerByOperationId("updateTransaction", new TransactionsHandler(eventBus, ActionType.EDIT));
      routerFactory.addHandlerByOperationId("removeTransaction", new TransactionsHandler(eventBus, ActionType.DELETE));

      routerFactory.addHandlerByOperationId("getAccounts", new AccountsHandler(eventBus,ActionType.LIST));
      routerFactory.addHandlerByOperationId("getAccount", new AccountsHandler(eventBus,ActionType.GET));
      routerFactory.addHandlerByOperationId("getAccounts", new AccountsHandler(eventBus,ActionType.LIST));
      routerFactory.addHandlerByOperationId("addAccount", new AccountsHandler(eventBus,ActionType.ADD));
      routerFactory.addHandlerByOperationId("removeAccount", new AccountsHandler(eventBus,ActionType.DELETE));

      routerFactory.addHandlerByOperationId("getCustomers", new CustomersHandler(eventBus,ActionType.LIST));
      routerFactory.addHandlerByOperationId("getCustomer", new CustomersHandler(eventBus,ActionType.GET));
      routerFactory.addHandlerByOperationId("addCustomer", new CustomersHandler(eventBus,ActionType.ADD));
      routerFactory.addHandlerByOperationId("updateCustomer", new CustomersHandler(eventBus,ActionType.EDIT));
      routerFactory.addHandlerByOperationId("removeCustomer", new CustomersHandler(eventBus,ActionType.DELETE));
      Router router = routerFactory.getRouter();
      router.route().handler(BodyHandler.create());

      router.errorHandler(500, rc -> rc.response().setStatusCode(500).end(rc.failure() + "\n" + Arrays.toString(rc.failure().getStackTrace())));
      server = vertx.createHttpServer(new HttpServerOptions().setPort(8080).setHost("localhost"));
      server.requestHandler(router).listen((ar) ->  {
        if (ar.succeeded()) {
          logger.info("Server started on port " + ar.result().actualPort());
          future.complete();
        } else {
          logger.error("Oops, something went wrong during server initialization", ar.cause());
          future.fail(ar.cause());
        }
      });
      addFirstTransaction(eventBus);
    });
  }

  private void addFirstTransaction(EventBus eventBus){
    Transaction transaction=Transaction.builder().withAmount(10)
      .withSendingAccount(StorageProvider.accounts.get(Constants.DEFAULT_SENDING_ACCOUNT_NUMBER))
      .withReceivingAccount(StorageProvider.accounts.get(Constants.DEFAULT_RECEIVING_ACCOUNT_NUMBER))
      .withDate(new Date()).isNew(true)
      .withStatus(TransactionStatus.CLOSED).get();
    Handler<AsyncResult<Message<Object>>> asyncResultHandler = reply -> {
      if (reply.succeeded()) {

      } else {

      }
    };
    eventBus.send(EventBusAddress.ADD_TRANSACTION, JsonObject.mapFrom(transaction), asyncResultHandler);
  }

  public void stop() {

    this.server.close();
  }
}
