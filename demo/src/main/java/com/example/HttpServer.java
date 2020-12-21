package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class HttpServer extends AbstractVerticle {

  private final Logger logger = LoggerFactory.getLogger(HttpServer.class);

  @Override
  public void start(Promise<Void> Promise) {
    Router router = Router.router(vertx);

    router.route("/eventbus/*").handler(eventBusHandler());
    router.route().handler(staticHandler());


    int httpPort;

    try {
        httpPort = Integer.parseInt(System.getProperty("http.port", "8080"));
    } catch (NumberFormatException nfe) {
        httpPort = 8080;
    }

    vertx.createHttpServer().requestHandler(router).listen(httpPort, http -> {

        if (http.succeeded()) {
            Promise.complete();
            logger.info("HTTP Server Started on port : 8080");
          } else {
            Promise.fail(http.cause());
          }
        });
}

private SockJSHandler eventBusHandler() {
    BridgeOptions options = new BridgeOptions()
            .addOutboundPermitted(new PermittedOptions().setAddressRegex("out"))
            .addInboundPermitted(new PermittedOptions().setAddressRegex("in"));

    SharedData data = vertx.sharedData();
    CounterRepository repository = new CounterRepository(data);
    EventBus eventBus = vertx.eventBus();
    CounterHandler counterHandler = new CounterHandler(eventBus, repository);

    SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
    return sockJSHandler;
}

private StaticHandler staticHandler() {
  return StaticHandler.create()
          .setCachingEnabled(false);
}
}