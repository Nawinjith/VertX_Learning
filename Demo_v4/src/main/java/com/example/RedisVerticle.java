package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.client.*;

import java.util.Arrays;
import java.util.List;


public class RedisVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(RedisVerticle.class);
    private Redis redisClient;
    private RedisAPI redis;

//    RedisVerticle(){
//        this.redisClient = Redis.createClient(vertx, "redis://localhost:6379");
//        this.redis = RedisAPI.api(this.redisClient);
//    }

    @Override
    public void start() {

        String args[] = new String[] { "counter","0" };
        List<String> list = Arrays.asList(args);

//        Redis redisClient = Redis.createClient(vertx, "redis://localhost:6379");
//        redisClient = Redis.createClient(vertx, "redis://localhost:6379");
        redis = RedisAPI.api(this.redisClient);

//        redisClient.send(Request.cmd(Command.PING), res->{
////            System.out.println(res.result());
//            if(res.succeeded())
//                logger.info("Redis Connected");
//        });

//        RedisAPI redis = RedisAPI.api(redisClient);

        this.redis.set(list,handler->{
            logger.info("Key-Counter Set " + handler.result());
        });


        this.redis.get("counter",handler ->{
            int currentVal = handler.result().toInteger();
            logger.info("Current Counter Value " + currentVal);
        });

//        redis.incr("counter" , res -> {
//            logger.info("Number Incremented to " + res.result().toInteger());
//        });

    }

    public int incrementValues() {


        this.redis.get("counter",handler -> {
                int currentVal = handler.result().toInteger();
                int newVal = currentVal+1;
                String newValue = String.valueOf(newVal);

                String args[] = new String[] { "counter",newValue };
                List<String> list = Arrays.asList(args);

                this.redis.set(list,res -> {
                    logger.info("New value Set " + res.result());
                });

                logger.info("Number Incremented ");

        });


        return 0;
    }


}

