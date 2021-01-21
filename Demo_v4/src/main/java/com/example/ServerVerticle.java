package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;

public class ServerVerticle extends AbstractVerticle {

  private final Logger logger = LoggerFactory.getLogger(ServerVerticle.class);

  @Override
  public void start(Promise<Void> promise) {
      Router router = Router.router(vertx);

      PermittedOptions outboundPermitted1 = new PermittedOptions()
              .setAddressRegex("number.updates");

      PermittedOptions inboundPermitted1 = new PermittedOptions()
              .setAddressRegex("update.number");

      SockJSBridgeOptions options = new SockJSBridgeOptions()
              .addInboundPermitted(inboundPermitted1)
              .addOutboundPermitted(outboundPermitted1);


      SharedData data = vertx.sharedData();

      EventBus eventBus = vertx.eventBus();
      UpdateData counterHandler = new UpdateData(eventBus);

      SockJSHandler sockJSHandler = SockJSHandler.create(vertx);

      router.mountSubRouter("/eventbus", sockJSHandler.bridge(options, counterHandler));

        int httpPort;

        try {
        httpPort = Integer.parseInt(System.getProperty("http.port", "8080"));
        } catch (NumberFormatException nfe) {
        httpPort = 8080;
        }

        vertx.createHttpServer().requestHandler(router).listen(httpPort, http -> {

        if (http.succeeded()) {
                // Promise.complete();
                logger.info("HTTP Server Started on port : 8080");
                } else {
                // Promise.fail(http.cause());
                logger.info("Failed to start HTTP Server");
                }
        });

      router.route().handler(StaticHandler.create());



}

}