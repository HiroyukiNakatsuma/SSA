package com.example.ssa.ssa.validator;

import com.example.ssa.ssa.component.annotation.UploadFileNotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class UploadFileNotEmptyForListValidator implements
        ConstraintValidator<UploadFileNotEmpty, MultipartFile[]> {

    private final UploadFileNotEmptyValidator validator = new UploadFileNotEmptyValidator();

    @Override
    public void initialize(UploadFileNotEmpty constraintAnnotation) {
        validator.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile[] files, ConstraintValidatorContext context) {
        return files.length > 0 && Arrays.stream(files).allMatch(file -> !validator.isValid(file, context));
    }

}
