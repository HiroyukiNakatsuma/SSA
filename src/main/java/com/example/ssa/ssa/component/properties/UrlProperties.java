package com.example.ssa.ssa.component.properties;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UrlProperties {

    @NotNull
    private String base;
    @NotNull
    private Image image;

    @Data
    public static class Image {
        @NotNull
        private String photo;
    }

}
