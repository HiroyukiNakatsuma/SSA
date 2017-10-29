package com.example.ssa.ssa.validator;

import com.example.ssa.ssa.component.annotation.UploadFileNotEmpty;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UploadFileNotEmptyValidator implements
        ConstraintValidator<UploadFileNotEmpty, MultipartFile> {

    @Override
    public void initialize(UploadFileNotEmpty constraint) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file != null
                && StringUtils.hasLength(file.getOriginalFilename())
                && !file.isEmpty();
    }

}