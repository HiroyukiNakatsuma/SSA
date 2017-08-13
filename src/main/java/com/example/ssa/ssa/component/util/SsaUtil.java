package com.example.ssa.ssa.component.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class SsaUtil {

    public String createHashedString() {
        return RandomStringUtils.randomAlphanumeric(32);
    }
}
