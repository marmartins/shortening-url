package com.marcsystem.shorturl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcsystem.shorturl.domain.Urls;
import com.marcsystem.shorturl.services.URLConverterServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebMvcTest(value = URLConverterController.class, secure = false)
public class URLConverterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private URLConverterServiceImpl urlConverterService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void convertUrl() throws Exception {
        Urls newURL = createNewURL(1);
        when(urlConverterService.insert(any())).thenReturn(newURL);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/converturl")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"targetUrl\":\"http://www.targeturl.com/1\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(newURL), result.getResponse().getContentAsString(), false);
    }

    @Test
    public void invalidConvertUrl() throws Exception {
        Urls newURL = createNewURL(1);
        when(urlConverterService.insert(any())).thenReturn(newURL);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/converturl")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"targetUrl\":\"invalid/1\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    }

    @Test
    public void routerToSpecifiedUrl() throws Exception {
        Urls newURL = createNewURL(1);
        when(urlConverterService.insert(any())).thenReturn(newURL);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/routerurl")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"shortUrl\":\"AABB\", \"targetUrl\":\"http://www.targeturl.com/1\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(newURL), result.getResponse().getContentAsString(), false);
    }

    @Test
    public void findAll() throws Exception {
        List<Urls> urlsList = new ArrayList<>();
        urlsList.add(createNewURL(1));
        urlsList.add(createNewURL(2));

        when(urlConverterService.findAll()).thenReturn(urlsList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/urls").accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(urlsList), result.getResponse().getContentAsString(), false);

    }

    private Urls createNewURL(Integer id) {
        Urls urls = new Urls();
        urls.setId(id);
        urls.setShortUrl("ShortURL_" + id);
        urls.setTargetUrl("http://www.targeturl.com/" + id);
        return urls;
    }

}