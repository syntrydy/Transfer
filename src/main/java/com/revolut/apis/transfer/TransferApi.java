package com.revolut.apis.transfer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class TransferApi extends AbstractVerticle {
  Logger logger = LoggerFactory.getLogger("=============MainVerticle");
  @Override
  public void start(Future<Void> future){
    CompositeFuture.all(deployHelper(TransferVerticle.class.getName()),
      deployHelper(DataVerticle.class.getName())).setHandler(result -> {
      if(result.succeeded()){
        future.complete();
      } else {
        future.fail(result.cause());
      }
    });
  }

  public static void main(String[] args) {
    Launcher.executeCommand("run", TransferApi.class.getName());
  }


  private Future<Void> deployHelper(String name){
    final Future<Void> future = Future.future();
    vertx.deployVerticle(name, res -> {
      if(res.failed()){
        logger.error("Failed to deploy verticle " + name);
        future.fail(res.cause());
      } else {
        logger.info("Deployed verticle " + name);
        future.complete();
      }
    });
    return future;
  }

}
