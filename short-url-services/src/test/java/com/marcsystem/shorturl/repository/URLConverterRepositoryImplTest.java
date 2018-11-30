package com.marcsystem.shorturl.repository;

import com.marcsystem.shorturl.domain.Urls;
import com.marcsystem.shorturl.utils.URLConverter;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(MockitoJUnitRunner.class)
public class URLConverterRepositoryImplTest {

    @Mock
    private ConnectionManager connectionManager;

    @Mock
    private StatefulRedisConnection<String, String> connection;

    @Mock
    private RedisCommands<String, String> commands;

    private URLConverterRepositoryImpl urlConverterRepository;

    @Before
    public void setup() throws Exception {
        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.sync()).thenReturn(commands);
        urlConverterRepository = new URLConverterRepositoryImpl();
        setField(urlConverterRepository, "connectionManager", connectionManager);
    }

    @Test
    public void findByKey() throws Exception {
        String key = "AAA";
        configureMap(key);

        Urls foundUrls = urlConverterRepository.findByKey(key);
        assertThat(foundUrls.getShortUrl(), equalTo(key));
        assertThat(foundUrls.getTargetUrl(), equalTo("http://myurl.com"));
    }

    @Test
    public void findAll() throws Exception {
        String key = "AAA";
        configureMap(key);

        when(commands.keys(any())).thenReturn(Arrays.asList(new String[]{key}));

        List<Urls> foundUrls = urlConverterRepository.findAll();

        assertThat(foundUrls.size(), equalTo(1));
        assertThat(foundUrls.get(0).getShortUrl(), equalTo(key));
        assertThat(foundUrls.get(0).getTargetUrl(), equalTo("http://myurl.com"));
    }

    private Map<String, String> configureMap(String key) {
        Map<String, String> values = new HashMap<>();
        values.put("shortUrl", key);
        values.put("targetUrl", "http://myurl.com");
        when(commands.hgetall(any())).thenReturn(values);
        return values;
    }

    @Test
    public void remove() throws Exception {
        String key = "AAA";
        urlConverterRepository.remove(key);

        verify(commands, atLeastOnce()).del(any());
    }

    @Test
    public void createNewInstance() throws Exception {
        String key = "AAA";
        Urls urls = new Urls(key, "http://myurl.com", LocalDate.now(), 1L);
        urlConverterRepository.insert(urls);

        verify(commands, atLeastOnce()).hmset(any(), any());
    }

}