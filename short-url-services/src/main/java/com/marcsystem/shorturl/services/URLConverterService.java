package com.marcsystem.shorturl.services;

import com.marcsystem.shorturl.domain.Urls;

import java.util.List;

public interface URLConverterService {

    Urls insertNew(Urls urls);

    Urls insertToSpecified(Urls urls);

    Urls findByKey(String key);

    List<Urls> findAll();

    void remove(String key);
}
