package com.marcsystem.shorturl.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class URLValidationTest {

    private String url = "https://www.google.com.br/search";

    @Test
    public void isValid() throws Exception {
        assertThat(URLValidation.getInstance().isValid(url), equalTo(true));
    }

    @Test
    public void isNotValid() throws Exception {
        assertThat(URLValidation.getInstance().isValid(url.substring(4)), equalTo(false));
    }

}