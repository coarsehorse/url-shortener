package com.java.urlshortener.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by coarse_horse on 30/07/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlResponse {
    
    private String sourceUrl;
    private String shortenedUrl;
}
