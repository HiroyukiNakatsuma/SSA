package com.example.ssa.ssa.component.properties;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UrlProperties {

    @NotNull
    private String root;
    @NotNull
    private String photo;

}
