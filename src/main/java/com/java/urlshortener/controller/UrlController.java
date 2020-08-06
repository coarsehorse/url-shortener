package com.java.urlshortener.controller;

import com.java.urlshortener.data.document.ShortenedUrl;
import com.java.urlshortener.data.repository.ShortenedUrlRepository;
import com.java.urlshortener.payload.request.ShortenUrlRequest;
import com.java.urlshortener.payload.response.ShortenUrlResponse;
import com.java.urlshortener.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * Created by coarse_horse on 30/07/2020
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UrlController {
    
    @Value("${app.slug.length}")
    private Integer SLUG_LENGTH;
    
    @Value("${app.slug.attemptsToGen}")
    private Integer SLUG_ATTEMPTS_TO_GEN;
    
    private final ShortenedUrlRepository shUrlRepository;
    
    @PostMapping("/shortenUrl")
    public ShortenUrlResponse shortenUrl(
        @Valid @RequestBody ShortenUrlRequest payload,
        HttpServletRequest request
    ) {
        String sourceUrl = payload.getSourceUrl();
        
        // Gen slug
        String slug = null;
        for (int i = 0; i < SLUG_ATTEMPTS_TO_GEN; i++) {
            slug = Utils.genRandomSlug(SLUG_LENGTH);
            if (shUrlRepository.findBySlug(slug).isEmpty()) break;
        }
        if (slug == null) {
            throw new RuntimeException("Sorry, exceeded max attempts to generate a random url");
        }
        
        // Save slug
        shUrlRepository.save(new ShortenedUrl(sourceUrl, slug, LocalDateTime.now()));
        
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
        String redirectionUrl = shUrlRepository.findBySlug(slug)
            .map(ShortenedUrl::getSourceUrl)
            .getOrElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad slug"));
    
        // Redirect
        response.setHeader("Location", redirectionUrl);
        response.setStatus(302);
    }
}
