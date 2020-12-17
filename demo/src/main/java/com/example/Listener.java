package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;

public class Listener extends AbstractVerticle {
    
    private final Logger logger = LoggerFactory.getLogger(Listener.class);
  
    @Override
    public void start() {
  
      vertx.eventBus().<JsonObject>consumer("number.updates", msg -> {

        JsonObject body = msg.body();
        // System.out.println(body);

        String id = body.getString("ID");
        String number = body.getString("num");

        logger.info("Number Incremented to : "+number);
        
        
      });

      
    }
  }

