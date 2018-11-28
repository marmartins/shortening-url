package com.marcsystem.shorturl.domain;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UrlsTest {

    @Test
    public void contructorTest() throws Exception {
        Urls urls = new Urls(5025, "eTG" , "https://www.google.com.br/search", LocalDate.now());
        assertThat(urls.getId(), notNullValue());
        assertThat(urls.getShortUrl(), notNullValue());
        assertThat(urls.getTargetUrl(), notNullValue());

        assertThat(5025, equalTo(urls.getId()));
        assertThat("eTG", equalTo(urls.getShortUrl()));
        assertThat("https://www.google.com.br/search", equalTo(urls.getTargetUrl()));
    }
}