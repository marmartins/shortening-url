package com.marcsystem.shorturl.services;

import com.marcsystem.shorturl.domain.Urls;
import com.marcsystem.shorturl.repository.URLConverterRepository;
import com.marcsystem.shorturl.utils.URLConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Log4j2
@Service
public class URLConverterServiceImpl implements URLConverterService {

    @Autowired
    private URLConverterRepository urlConverterRepository;

    public Urls insert(Urls urls) {

        if (isNull(urls.getShortUrl())) {
            int tempUrlId = URLConverter.decode(urls.getTargetUrl());
            urls.setShortUrl(URLConverter.encode(tempUrlId));
            urls.setId(URLConverter.decode(urls.getShortUrl()));

        } else {
            if (nonNull(findByKey(urls.getShortUrl()).getId())) {
                throw new IllegalArgumentException("Short URL already registered");
            }
            urls.setId(URLConverter.decode(urls.getShortUrl()));
        }

        urls.setCreateDate(LocalDate.now());

        log.debug("URLs to insert :: {}", urls);

        urlConverterRepository.insert(urls);
        return urls;
    }

    @Override
    public Urls findByKey(String key) {
        return urlConverterRepository.findByKey(key);
    }

    @Override
    public List<Urls> findAll() {
        return urlConverterRepository.findAll();
    }

    @Override
    public void remove(String key) {
        urlConverterRepository.remove(key);
    }
}
