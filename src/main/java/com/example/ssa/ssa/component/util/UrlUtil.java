package com.example.ssa.ssa.component.util;

import org.springframework.stereotype.Component;

@Component
public class UrlUtil {
    private static final String BASE_URL = "http://localhost:8080";

    public String getBaseUrl() {
        return BASE_URL;
    }
}
