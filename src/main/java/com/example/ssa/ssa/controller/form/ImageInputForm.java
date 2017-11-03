package com.example.ssa.ssa.controller.form;

import com.example.ssa.ssa.component.annotation.UploadFileNotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageInputForm {
    @UploadFileNotEmpty
    private MultipartFile[] files;
}
