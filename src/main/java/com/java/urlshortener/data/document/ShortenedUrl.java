package com.java.urlshortener.data.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by coarse_horse on 01/08/2020
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShortenedUrl extends BaseDocument {

    private String sourceUrl;
    @Indexed
    private String slug;
    private LocalDateTime creationDate;
}
