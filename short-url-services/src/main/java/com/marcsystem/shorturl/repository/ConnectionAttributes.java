package com.marcsystem.shorturl.repository;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ConnectionAttributes {

    @Value("${redis.uri}")
    private String uri;

}
