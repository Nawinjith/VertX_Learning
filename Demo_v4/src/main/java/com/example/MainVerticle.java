package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) throws Exception {

//        Promise<String> dbVerticleDeployment = Promise.promise();
//        vertx.deployVerticle(new RedisVerticle(), dbVerticleDeployment);
//
//        dbVerticleDeployment.future().compose(id -> {

            Promise<String> serverVerticleDeployment = Promise.promise();
            vertx.deployVerticle(
                    "com.example.ServerVerticle",new DeploymentOptions()
                            .setInstances(1),
                    serverVerticleDeployment);

//            return serverVerticleDeployment.future();
//
//        }).onComplete(ar -> {
//
//            if (ar.succeeded()) {
//                promise.complete();
//            } else {
//                promise.fail(ar.cause());
//            }
//        });
    }
}