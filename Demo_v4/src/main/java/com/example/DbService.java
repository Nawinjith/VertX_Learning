package com.example;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;

public class DbService  {

    private static final Logger logger = LoggerFactory.getLogger(UpdateData.class);

    private Redis redisClient;
    private RedisAPI redis;
    JsonObject obj = new JsonObject();

    DbService(){
        this.redisClient = Redis.createClient(Vertx.currentContext().owner(), "redis://localhost:6379");
        this.redis = RedisAPI.api(this.redisClient);

    }


    public void getCurrentValues(){

        this.redis.hgetall("counter:1",res -> {
//           System.out.print(res.result().get("counterA").toString());
           this.obj.put("counterA",res.result().get("counterA").toString());
           this.obj.put("counterB",res.result().get("counterB").toString());
           this.obj.put("counterC",res.result().get("counterC").toString());
//           System.out.print(this.obj);
           logger.info(this.obj);

        });

    }

    public JsonObject  incrementValues(Integer counterType) {


        //next step - include MULTI/ EXEC here for sequential execution with thread safety and blocking in transaction
        //System.out.print(counterType);
        if(counterType == 0){
            redis.hincrby("counter:1","counterA","1",res -> {
                if(res.succeeded())
                 logger.info("Counter A Incremented");
            });
        }else if(counterType == 1) {
            redis.hincrby("counter:1","counterB","1",res -> {
                if(res.succeeded())
                    logger.info("Counter B Incremented");
            });
        }else {
            redis.hincrby("counter:1","counterC","1",res -> {
                if(res.succeeded())
                    logger.info("Counter C Incremented");
            });
        }

        return this.obj;
    }

}
