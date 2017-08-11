package com.example.ssa.ssa.component.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SsaUtil {

    public String createHashedString() {
        Random rnd = new Random();
        int ran = rnd.nextInt(32);
        return Integer.toString(ran);
    }
}
