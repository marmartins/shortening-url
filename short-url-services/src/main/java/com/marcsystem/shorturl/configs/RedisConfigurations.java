package com.marcsystem.shorturl.configs;

import com.marcsystem.shorturl.repository.ConnectionAttributes;
import com.marcsystem.shorturl.repository.ConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfigurations {

    @Bean
    ConnectionManager getConnectionManager(){
        return new ConnectionManager(getConnectionAtributes());
    }

    @Bean
    ConnectionAttributes getConnectionAtributes() {
        return new ConnectionAttributes();
    }


}
