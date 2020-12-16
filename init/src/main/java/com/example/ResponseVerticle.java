package com.example;

import java.util.UUID;

import io.vertx.core.AbstractVerticle;

public class ResponseVerticle extends AbstractVerticle {

    String verticleId = UUID.randomUUID().toString();
    
    @Override
    public void start(){

        //indexHandler
        vertx.eventBus().consumer("hello.vertx.addr",msg -> {
            // System.out.println("Hello at Index Handler");
            msg.reply(String.format("Hello from %s", verticleId));
        });

        // indexHandler_named
        vertx.eventBus().consumer("hello.named.addr",msg -> {
            String name = (String)msg.body();
            msg.reply(String.format("Hello %s from %s", name,verticleId));
        });


        //increment
        vertx.eventBus().consumer("number.update",msg -> {

            String number = (String)msg.body();
            int val = Integer.parseInt(number);
            System.out.println(++val);

            msg.reply(String.format("New value : %d ",val));
        });

      

    }
}