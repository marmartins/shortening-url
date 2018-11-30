package com.marcsystem.shorturl.utils;

import org.junit.Test;

import static com.marcsystem.shorturl.utils.URLConverter.NUM_OF_LAST_URL_CHARS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;


public class URLConverterTest {

    private String url = "https://www.google.com/search";

    @Test
    public void decodeTest() throws Exception {
        NUM_OF_LAST_URL_CHARS = 6;
        /*
        * The same String always return the same value
        * */
		assertThat(URLConverter.ENCODE.apply(url), equalTo("eBahcraes"));
    }
    @Test
    public void generatindDuplicateKeyTest() throws Exception {
        NUM_OF_LAST_URL_CHARS = 0;
        String urlToDuplicateKey = "https://www.ggoole.com/sherac";

        assertThat(URLConverter.ENCODE.apply(url), equalTo("eBa"));
        assertThat(URLConverter.ENCODE.apply(urlToDuplicateKey), equalTo("eBa"));
    }

}