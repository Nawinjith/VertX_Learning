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

    // @Test
    // @DisplayName("⏱ Count 3 timer ticks")
    // void countThreeTicks(Vertx vertx, VertxTestContext testContext) {
    //     AtomicInteger counter = new AtomicInteger();
    //     vertx.setPeriodic(100, id -> {
    //         if (counter.incrementAndGet() == 3) {
    //             testContext.completeNow();
    //         }
    //     });
    // }


    @Test
    @DisplayName("Deploy MainVerticle")
    void deployMainVerticle(Vertx vertx, VertxTestContext testContext) {
        vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
    }


    @Test
    @DisplayName(" Update Number")
    public void updateNumber(Vertx vertx) throws Exception {

        SharedData data = vertx.sharedData();
        Counter store = new Counter(data);
        EventBus eventBus = vertx.eventBus();
       

        LocalMap<String, String> map1 = data.getLocalMap("mymap1");
        map1.put("key", "10");
        
        LocalMap<String, String> counter = data.getLocalMap("mymap1");
        Integer initial_value = Integer.valueOf(counter.get("key"));

        store.update(initial_value);

        UpdateData counterHandler = new UpdateData(eventBus, store);

        counterHandler.clientToServer();
        Optional<Integer> updated_value = store.get();
        Integer new_value = updated_value.get();

        // Assert.assertEquals(11,new_value);
        // assertThat(new_value).isEqualTo(11);
        System.out.println(new_value);
    }
}