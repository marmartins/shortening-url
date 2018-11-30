package com.marcsystem.shorturl.repository;

import com.marcsystem.shorturl.domain.Urls;
import com.marcsystem.shorturl.utils.URLConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Repository
public class URLConverterRepositoryImpl extends BaseRepository<Urls> implements URLConverterRepository {

    @Override
    public Urls findByKey(String key) {
        Urls urlsTemplate = new Urls();
        urlsTemplate.setShortUrl(key);
        updateIndexes(urlsTemplate);

        return findOne(urlsTemplate);
    }

    @Override
    public List<Urls> findAll() {
        return findAll(new Urls());
    }

    @Override
    public void remove(String key) {
        Urls urlsTemplate = new Urls();
        urlsTemplate.setShortUrl(key);
        log.info("Removing URL form ID: {}", key);
        super.remove(extractRedisKey(urlsTemplate, false).getRedisKey());
    }

    @Override
    protected Urls createNewInstance() {
        return new Urls();
    }
}
