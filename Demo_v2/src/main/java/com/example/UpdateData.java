package com.example;

import java.util.Optional;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

public class UpdateData implements Handler<BridgeEvent> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateData.class);
    private final EventBus eventBus;
    private final Counter store;

    UpdateData(EventBus eventBus, Counter store) {
        this.eventBus = eventBus;
        this.store = store;
    }

    @Override
    public void handle(BridgeEvent event) {
        if (event.type() == BridgeEventType.SOCKET_CREATED)
            logger.info("New Socket Created");

        if (event.type() == BridgeEventType.SEND){
            clientToServer();
        }
//

        event.complete(true);
    }


    public void clientToServer() {
        Optional<Integer> counter = store.get();

        if (counter.isPresent()) {
            Integer cur_value = counter.get();
            Integer new_value = counter.get() + 1;

            store.update(new_value);
            eventBus.publish("number.updates", new_value);

            logger.info("Current Value : "+cur_value);
            logger.info("Number Updated to : "+new_value);
        } else {
            Integer new_value = 1;
            store.update(new_value);
            eventBus.publish("number.updates", new_value);
            logger.info("Current Value : 0 ");
            logger.info("Number Updated to : "+new_value);
        }
    }



}