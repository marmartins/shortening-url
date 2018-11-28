package com.marcsystem.shorturl.repository;

import com.marcsystem.shorturl.domain.Urls;

import java.util.List;

public interface URLConverterRepository {

    void insert(Urls urls);

    Urls findByKey(String key);

    List<Urls> findAll();

    void remove(String key);
}
