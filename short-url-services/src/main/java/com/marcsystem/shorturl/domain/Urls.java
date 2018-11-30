package com.marcsystem.shorturl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcsystem.shorturl.annotation.RedisEntity;
import com.marcsystem.shorturl.annotation.RedisIndex;
import com.marcsystem.shorturl.annotation.RedisKey;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RedisEntity(keyPrefix="url")
public class Urls implements RedisDTO{

    @RedisKey
    @RedisIndex
    private String shortUrl;

    private String targetUrl;

    @JsonIgnore
    @RedisIndex(name="date")
    private LocalDate createDate;

    @JsonIgnore
    private Long expiredTime;

}
