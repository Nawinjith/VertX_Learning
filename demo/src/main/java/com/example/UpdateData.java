package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class UpdateData extends AbstractVerticle {
  
    @Override
    public void start(Promise<Void> Promise) {

        System.out.println("In UpdateData");

        vertx.eventBus().consumer("number.update",msg -> {

            String number = (String)msg.body();
            int newVal = Integer.parseInt(number) + 1;

            // System.out.println(newVal);

            JsonObject payload = new JsonObject()
                // .put("id", ID)
                .put("num", newVal);

            vertx.eventBus().publish("number.updates", payload);

            msg.reply(String.format("New value : %d ",newVal));

            Promise.complete();
        });
    }
  
}