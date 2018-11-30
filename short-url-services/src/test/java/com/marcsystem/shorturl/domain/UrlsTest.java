package com.marcsystem.shorturl.domain;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UrlsTest {

    @Test
    public void contructorTest() throws Exception {
        Urls urls = new Urls("eTG" , "https://www.google.com.br/search", LocalDate.now(), 1L);
        assertThat(urls.getShortUrl(), notNullValue());
        assertThat(urls.getTargetUrl(), notNullValue());
        assertThat(urls.getCreateDate(), notNullValue());
        assertThat(urls.getExpiredTime(), notNullValue());

        assertThat("eTG", equalTo(urls.getShortUrl()));
        assertThat("https://www.google.com.br/search", equalTo(urls.getTargetUrl()));
        assertThat(1L, equalTo(urls.getExpiredTime()));
    }
}