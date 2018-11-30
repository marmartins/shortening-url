package com.marcsystem.shorturl.services;

import com.marcsystem.shorturl.domain.Urls;
import com.marcsystem.shorturl.repository.URLConverterRepository;
import com.marcsystem.shorturl.utils.ExpiredTimeConverter;
import com.marcsystem.shorturl.utils.URLConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Log4j2
@Service
public class URLConverterServiceImpl implements URLConverterService {

    @Value("${max.custom.key.size}")
    private static int MAX_CUSTOM_URL = 0;

    @Value("${min.custom.key.size}")
    private static int MIN_CUSTOM_URL = 0;

    @Autowired
    private URLConverterRepository urlConverterRepository;

    public Urls insertNew(Urls urls) {
        urls.setShortUrl(URLConverter.ENCODE.apply(urls.getTargetUrl()));
        return insert(urls);
    }

    public Urls insertToSpecified(Urls urls) {
        if(isNull(urls.getShortUrl()) || "".equals(urls.getShortUrl())) {
            throw new IllegalArgumentException("URL alias is required to this function");
        }
        if ((MIN_CUSTOM_URL > 0 && urls.getShortUrl().length() < MIN_CUSTOM_URL)
                || (MAX_CUSTOM_URL > 0 && urls.getShortUrl().length() > MAX_CUSTOM_URL)) {
            throw new IllegalArgumentException("URL alias informed size invalid. It should be a value between ["
                    + MIN_CUSTOM_URL + " - " + MAX_CUSTOM_URL + "]");
        }
        if (nonNull(findByKey(urls.getShortUrl()).getShortUrl())) {
            throw new IllegalArgumentException("URL alias already registered");
        }
        return insert(urls);
    }

    private Urls insert(Urls urls) {
        urls.setCreateDate(LocalDate.now());
        urls.setExpiredTime(ExpiredTimeConverter.getInstance().getTime());
        log.debug("URLs to insertNew :: {}", urls);
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
