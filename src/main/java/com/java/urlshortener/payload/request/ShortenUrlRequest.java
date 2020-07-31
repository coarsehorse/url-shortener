package com.java.urlshortener.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

/**
 * Created by coarse_horse on 30/07/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlRequest {
    
    @NotBlank
    @URL
    private String sourceUrl;
}
