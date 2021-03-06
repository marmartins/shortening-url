package com.marcsystem.shorturl.controller;

import com.marcsystem.shorturl.domain.Urls;
import com.marcsystem.shorturl.services.URLConverterServiceImpl;
import com.marcsystem.shorturl.utils.URLValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Api(value = "URLConverter", description = "Allow to converter and user shortening URLs")
@Log4j2
@RestController
public class URLConverterController {

	@Autowired
	private URLConverterServiceImpl urlConverterService;

	@ApiOperation(value = "Create new Short URL", response = Urls.class)
	@PostMapping("/converturl")
	public ResponseEntity<Urls> convertUrl(
			@ApiParam(value = "Long Url to convert", required = true)
			@RequestBody Urls longUrl, HttpServletRequest request) {
		log.info("URL to shortening: {}", longUrl);

		if(!URLValidation.getInstance().isValid(longUrl.getTargetUrl())) {
			throw new IllegalArgumentException("Invalid targetURL");
		}
		Urls urls = urlConverterService.insertNew(longUrl);
		return getUrlsResponseEntity(request, urls);
	}


	@ApiOperation(value = "Register the informed Short URL to respective long one ", response = Urls.class)
	@PostMapping("/routerurl")
	public ResponseEntity<Urls> routerToSpecifiedUrl(
			@ApiParam(value = "Long Url to convert and the reference short URL", required = true)
			@RequestBody Urls longUrl, HttpServletRequest request) {

		if(!URLValidation.getInstance().isValid(longUrl.getTargetUrl())) {
			throw new IllegalArgumentException("Invalid targetURL");
		}
		Urls urls = urlConverterService.insertToSpecified(longUrl);
		return getUrlsResponseEntity(request, urls);
	}

	@ApiOperation(value = "Redirect a short URL ", response = RedirectView.class)
	@GetMapping("/u/{key}")
	public RedirectView redirectUrl(
			@ApiParam(value = "Short URL to redirect", required = true)
			@PathVariable String key) {

		Urls urls = urlConverterService.findByKey(key);

		RedirectView view = new RedirectView();
		view.setUrl(urls.getTargetUrl());
		return view;
	}

	@ApiOperation(value = "Delete a short URL ", response = String.class)
	@GetMapping("/del/{key}")
	public ResponseEntity<String> deleteUrl(
			@ApiParam(value = "Short Url to delete", required = true)
			@PathVariable String key) {
		urlConverterService.remove(key);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Return all URLs ", response = Urls.class, responseContainer = "List")
	@GetMapping("/urls")
	public ResponseEntity<List<Urls>> findAll() {
		List<Urls> urls = urlConverterService.findAll();
		if(urls.isEmpty()) {
			return ResponseEntity.notFound().build();

		} else {
			return ResponseEntity.ok(urls);
		}

	}

	private ResponseEntity<Urls> getUrlsResponseEntity(HttpServletRequest request, Urls urls) {
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI().toString();
		String localUrl = url.substring(0, url.indexOf(uri));
		localUrl = !localUrl.endsWith("/") ? localUrl + "/u/" : "u/";

		urls.setTargetUrl(localUrl + urls.getShortUrl());
		return ResponseEntity.ok(urls);
	}

}
