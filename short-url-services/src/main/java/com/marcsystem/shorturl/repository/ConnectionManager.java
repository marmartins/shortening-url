package com.marcsystem.shorturl.repository;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

@Log4j2
public class ConnectionManager {


    private GenericObjectPool<StatefulRedisConnection<String, String>> pool;
    private ConnectionAttributes attributes;

    public ConnectionManager(ConnectionAttributes attributes) {
        this.attributes = attributes;
        log.info("Connection Manager created to :: {}", attributes);
    }

    public StatefulRedisConnection<String, String> getConnection() throws Exception {
        if(pool == null) {
            log.info("Initializing Redis connection to URI <{}>", attributes);
            try {
                RedisClient client = RedisClient.create(RedisURI.create(attributes.getUri()));
                pool = ConnectionPoolSupport
                        .createGenericObjectPool(() -> client.connect(), new GenericObjectPoolConfig());
            } catch(Exception e) {
                throw new IllegalStateException("Was not possible to Connect on Redis using the URI <" + attributes + ">", e);
            }
        }
        return pool.borrowObject();
    }
}
