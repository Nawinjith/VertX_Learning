package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

public class HttpServer extends AbstractVerticle {

    private FreeMarkerTemplateEngine templateEngine;
    private final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    
    @Override
    public void start(Promise<Void> Promise) throws Exception {

        Router router = Router.router(vertx);

        router.get("/").handler(this::indexHandler);
        router.post("/submit").handler(this::updateNumber);

        templateEngine = FreeMarkerTemplateEngine.create(vertx);

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

    void indexHandler(RoutingContext ctx){

        templateEngine.render(ctx.data(), "templates/index.ftl", ar -> {
            if (ar.succeeded()) {
                ctx.response().putHeader("Content-Type", "text/html");
                ctx.response().end(ar.result());
            } else {
                ctx.fail(ar.cause());
            }
        });
    }


    private void updateNumber(RoutingContext ctx) {

        System.out.println(ctx.request());
        String currNumber = ctx.request().getParam("number");
        logger.info("Number Entered : "+currNumber);
        
        vertx.eventBus().request("number.update", currNumber ,  reply -> {
        
            ctx.request().response().end((String)reply.result().body());

        });
    
    }

  }
