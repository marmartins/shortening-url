package com.marcsystem.shorturl.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;


public class URLConverterTest {

    private String url = "https://www.google.com.br/search";

    @Test
    public void encode() throws Exception {

        /*
        * This number should be obtained from the long URL
        * */
        int num = 18198;

        assertThat(URLConverter.encode(num), notNullValue());
        assertThat(URLConverter.encode(num), equalTo("eTG"));
    }

    @Test
    public void decode() throws Exception {
        /*
        * The same String always return the same value
        * */
        assertThat(URLConverter.decode(url), greaterThan(0));
        assertThat(URLConverter.decode(url), equalTo(18198));
    }

}