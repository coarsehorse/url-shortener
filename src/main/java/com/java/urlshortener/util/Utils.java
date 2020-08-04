package com.java.urlshortener.util;

import io.vavr.control.Option;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by coarse_horse on 30/07/2020
 */
public class Utils {
    
    private final static Pattern HOST_PATTERN = Pattern.compile("(https?://[^/]+)/?.*");
    
    /**
     * Generates random slug usi.
     *
     * @param length password length.
     * @return generated password.
     */
    public static String genRandomSlug(Integer length) {
        // ASCII codes range
        int leftLimit = 48; // '0'
        int rightLimit = 122; // 'z'
        return new SecureRandom().ints(leftLimit, rightLimit + 1)
            .filter(i -> (i < 58 || i > 64) && (i < 91 || i > 94) && i != 96)
            .limit(length)
            .mapToObj(i -> Character.toString((char) i))
            .collect(Collectors.joining());
    }
    
    public static Option<String> extractHostFromUrl(String url) {
        Matcher matcher = HOST_PATTERN.matcher(url);
        if (!matcher.matches()) {
            return Option.none();
        }
        return Option.of(matcher.group(1));
    }
}
