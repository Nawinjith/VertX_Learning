package com.example;


import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

public class UpdateData implements Handler<BridgeEvent> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateData.class);
    private final EventBus eventBus;
    private DbService conn = new DbService();
    private int counterType;

    UpdateData(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void handle(BridgeEvent event) {
        if (event.type() == BridgeEventType.SOCKET_CREATED){
            logger.info("New Socket Created");
        }


        if (event.type() == BridgeEventType.SEND){

            logger.info("Current Counter Values : " );
            conn.getCurrentValues();

            eventBus.<JsonObject>consumer("update.number",msg ->{
                this.counterType = msg.body().getInteger("counter_type");
//                System.out.print(this.counterType);
            });

            JsonObject resobj = conn.incrementValues(this.counterType);
            eventBus.publish("number.updates", resobj);
            logger.info("Incremented Counter Values :");
            conn.getCurrentValues();
        }

        event.complete(true);
    }




}