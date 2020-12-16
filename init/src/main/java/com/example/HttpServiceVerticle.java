package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

public class HttpServiceVerticle extends AbstractVerticle {

    private FreeMarkerTemplateEngine templateEngine;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        

        // private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);

        vertx.deployVerticle(new ResponseVerticle());

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        router.get("/api/hello").handler(this::indexHandler);
        router.post("/api/hello/:name").handler(this::indexHandler_named);
        // router.post("/submit").consumes("*/json").handler(this::incrementNum);
        router.post("/submit").handler(this::incrementNum);

        templateEngine = FreeMarkerTemplateEngine.create(vertx);

        int httpPort;

        try {
            httpPort = Integer.parseInt(System.getProperty("http.port", "8080"));
        } catch (NumberFormatException nfe) {
            httpPort = 8080;
        }

        vertx.createHttpServer().requestHandler(router).listen(httpPort, http -> {

            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8080");
              } else {
                startPromise.fail(http.cause());
              }
            });

    }

    void indexHandler(RoutingContext ctx){
        vertx.eventBus().request("hello.vertx.addr", "",  reply -> {
            templateEngine.render(ctx.data(), "templates/index.ftl", ar -> {
                if (ar.succeeded()) {
                  ctx.response().putHeader("Content-Type", "text/html");
                  ctx.response().end(ar.result());
                } else {
                  ctx.fail(ar.cause());
                }
              });
            // ctx.request().response().end((String)reply.result().body());
        });
    }

    void indexHandler_named(RoutingContext ctx){
        String name = ctx.pathParam("name");
        System.out.println(name);
        vertx.eventBus().request("hello.named.addr", name ,  reply -> {
            ctx.request().response().end((String)reply.result().body());
         });
    }

    void incrementNum(RoutingContext ctx){
        // System.out.println(ctx.getBodyAsJson());

        // int num = Integer.parseInt(ctx.request().getParam("number"));
        String num = ctx.request().getParam("number");

        // System.out.println(num);

        vertx.eventBus().request("number.update", num ,  reply -> {
            
            ctx.request().response().end((String)reply.result().body());
            
            
         });
        

        
    }



  }