package com.marcsystem.shorturl.utils;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;

public class URLConverter {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();
    /**
     * Number of last characters from original URL
     */
    @Value("${last.url.char.max}")
    public static int NUM_OF_LAST_URL_CHARS = 6;

    /**
     * Extract the ID from longURL
     */
    private static final Function<Long, String> DECODE_FROM_ID = (id) -> {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(ALPHABET.charAt(id.intValue() % BASE));
            id /= BASE;
        }
        return sb.reverse().toString();
    };

    /**
     * Create a short URL from specified ID + last NUM_OF_LAST_URL_CHARS from original URL
     */
    private static final BiFunction<String, Long, String> SHORT_URL =
            (longUrl, id) -> DECODE_FROM_ID.apply(id) + (NUM_OF_LAST_URL_CHARS > 0
                    ? new StringBuilder(longUrl.substring(longUrl.length() - NUM_OF_LAST_URL_CHARS)).reverse()
                    : "");

    /**
     * Create a Short ULR
     */
    public static final Function<String, String> ENCODE = (str) -> {
        Long id = 0L;
        for(char c : str.toCharArray())
            if (ALPHABET.contains("" + c))
                id += ALPHABET.indexOf(c) * BASE;
        return "" + SHORT_URL.apply(str, id) ;
    };
}
