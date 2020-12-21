package com.example;

import io.vertx.core.AbstractVerticle;
// import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.util.Arrays;

public class RedisVerticle extends AbstractVerticle {

  @Override
  public void start() {

    //get host and port.
    // String host = Vertx.currentContext().config().getString("host");
    // if (host == null) {
        
    //   host = "127.0.0.1";
    // }

    // Create the redis client
    // Redis client = Redis.createClient(vertx, new RedisOptions().addConnectionString(host));
    // RedisAPI redis = RedisAPI.api(client);
    
    // System.out.println(host);
    // System.out.println(client);
    // System.out.println(redis);

    // client.connect()
    //   .compose(conn -> {
    //     System.out.println("Heree");
    //     return redis.set(Arrays.asList("key", "value"))
    //       .compose(v -> {
    //         System.out.println("key stored");
    //         return redis.get("key");
    //       });
    //   }).onComplete(ar -> {
    //   if (ar.succeeded()) {
    //     System.out.println("Retrieved value: " + ar.result());
    //   } else {
    //     System.out.println("Connection Failed " + ar.cause());
    //   }
    // });
    // promise.complete();
  }
}

