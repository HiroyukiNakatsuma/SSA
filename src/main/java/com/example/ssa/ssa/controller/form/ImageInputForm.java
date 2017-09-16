package com.example.ssa.ssa.controller.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageInputForm {
    private MultipartFile[] files;
}
