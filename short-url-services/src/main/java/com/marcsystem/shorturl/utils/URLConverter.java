package com.marcsystem.shorturl.utils;

public class URLConverter {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    public static String encode(int id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(ALPHABET.charAt(id % BASE));
            id /= BASE;
        }
        return sb.reverse().toString();
    }

    public static int decode(String str) {
        int id = str.length();

        for(char c : str.toCharArray())
            if (ALPHABET.contains("" + c))
                id += ALPHABET.indexOf(c) * BASE;

        return id;
    }

}
