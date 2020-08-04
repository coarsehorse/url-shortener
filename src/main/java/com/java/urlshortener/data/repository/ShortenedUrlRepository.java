package com.java.urlshortener.data.repository;

import com.java.urlshortener.data.document.ShortenedUrl;
import io.vavr.control.Option;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by coarse_horse on 01/08/2020
 */
public interface ShortenedUrlRepository extends MongoRepository<ShortenedUrl, String> {
    
    Option<ShortenedUrl> findBySlug(String slug);
}
