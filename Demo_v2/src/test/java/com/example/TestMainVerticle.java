package com.example;

import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

    @Test
    @DisplayName("⏱ Count 3 timer ticks")
    void countThreeTicks(Vertx vertx, VertxTestContext testContext) {
        AtomicInteger counter = new AtomicInteger();
        vertx.setPeriodic(100, id -> {
            if (counter.incrementAndGet() == 3) {
                testContext.completeNow();
            }
        });
    }

    @Test
    @DisplayName("⬆️ Deploy MainVerticle")
    void deployMainVerticle(Vertx vertx, VertxTestContext testContext) {
        vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
    }

    @Test
    @DisplayName("⬆️ Update Number")
    void updateNumber(Vertx vertx) {

//        SharedData data = vertx.sharedData();
//        Counter store = new Counter(data);
//        Integer initValue = store.update(10);
//        UpdateData counterHandler = new UpdateData(eventBus, store);

        Map<String, String> TestMap = Collections.EMPTY_MAP;
        TestMap.put("key","10");




        hotel.incrementIfconditionmet(true);
        assertEquals(hotel.getPeopleInHotel(),initValue+1);
    }
}