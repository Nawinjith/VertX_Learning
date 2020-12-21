package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;

  public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) throws Exception {
  
      Promise<String> dbVerticleDeployment = Promise.promise();
      vertx.deployVerticle(new RedisVerticle(), dbVerticleDeployment);
  
      dbVerticleDeployment.future().compose(id -> {
  
        Promise<String> ServerVerticle = Promise.promise();
        vertx.deployVerticle(
          "com.example.HttpServer",new DeploymentOptions()
            .setInstances(1),
            ServerVerticle);
  
        return ServerVerticle.future();

      }).onComplete(ar -> {
  
        vertx.deployVerticle("com.example.CounterHandler");
        vertx.deployVerticle("com.example.CounterRepository");

        if (ar.succeeded()) {
          promise.complete();
        } else {
          promise.fail(ar.cause());
        }
      });
    }
  }