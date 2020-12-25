package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;


@ExtendWith(VertxExtension.class)
public class TestMainVerticle {


    @Test
    @DisplayName("Update Number - Zero")
    public void updateNumber(Vertx vertx) {

        SharedData data = vertx.sharedData();
        Counter store = new Counter(data);
        EventBus eventBus = vertx.eventBus();
       
        LocalMap<String, String> map1 = data.getLocalMap("mymap1");
        map1.put("key", "0");
        
        LocalMap<String, String> counter = data.getLocalMap("mymap1");
        Integer initial_value = Integer.valueOf(counter.get("key"));

        store.update(initial_value);

        UpdateData counterHandler = new UpdateData(eventBus, store);

        counterHandler.clientToServer();
        Optional<Integer> updated_value = store.get();
        Integer new_value = updated_value.get();


        assertEquals(1, new_value);
        
    }
}