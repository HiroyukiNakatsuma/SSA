package com.example.ssa.ssa;

import com.example.ssa.ssa.component.properties.UrlProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SsaConfig {

    @Bean
    @ConfigurationProperties(prefix = "ssa.urls")
    public UrlProperties urlProperties() {
        return new UrlProperties();
    }

}
