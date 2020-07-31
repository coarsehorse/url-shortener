package com.java.urlshortener.controller;

import com.java.urlshortener.payload.request.ShortenUrlRequest;
import com.java.urlshortener.payload.response.ShortenUrlResponse;
import com.java.urlshortener.payload.util.Utils;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by coarse_horse on 30/07/2020
 */
@RestController
public class UrlController {
    
    @Value("${app.slug.length}")
    private Integer SLUG_LENGTH;
    
    @Value("${app.slug.attemptsToGen}")
    private Integer SLUG_ATTEMPTS_TO_GEN;
    
    // To make it simple, lets store slugs in memory for the first time
    private final static Map<String, String> slugMap = new ConcurrentHashMap<>();
    
    @PostMapping("/shortenUrl")
    public ShortenUrlResponse shortenUrl(@Valid @RequestBody ShortenUrlRequest payload,
                                         HttpServletRequest request) {
        String sourceUrl = payload.getSourceUrl();
        
        // Gen slug
        String slug = null;
        for (int i = 0; i < SLUG_ATTEMPTS_TO_GEN; i++) {
            slug = Utils.genRandomSlug(SLUG_LENGTH);
            if (slugMap.get(slug) == null) break;
        }
        if (slug == null) {
            throw new RuntimeException("Sorry, exceeded max attempts to generate a random url");
        }
        
        // Save slug
        slugMap.put(slug, sourceUrl);
        
        // Construct full url with slug
        String requestUrl = request.getRequestURL().toString();
        String host = Utils.extractHostFromUrl(requestUrl)
            .getOrElseThrow(() -> new RuntimeException("Cannot extract host from request url"));
        String slugUrl = host + "/" + slug;
        
        return new ShortenUrlResponse(sourceUrl, slugUrl);
    }
    
    @GetMapping("/{slug}")
    public void redirectOnSlug(@PathVariable String slug, HttpServletResponse response) {
        // Find redirection url
        String redirectionUrl = Option.of(slugMap.get(slug))
            .getOrElseThrow(() -> new RuntimeException("Bad slug"));
    
        // Redirect
        response.setHeader("Location", redirectionUrl);
        response.setStatus(302);
    }
}
