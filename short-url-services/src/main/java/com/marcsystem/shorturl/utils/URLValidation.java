package com.marcsystem.shorturl.utils;

import java.util.regex.Pattern;

public class URLValidation {

    private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

    private final Pattern urlPattern = Pattern.compile(URL_REGEX);

    private static final URLValidation INSTANCE = new URLValidation();

    private URLValidation() {
    }

    public static URLValidation getInstance() {
        return INSTANCE;
    }

    public boolean isValid(String url) {
        return urlPattern.matcher(url).matches();
    }
}
