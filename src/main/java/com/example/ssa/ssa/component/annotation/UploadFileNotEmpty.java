package com.example.ssa.ssa.component.annotation;

import com.example.ssa.ssa.validator.UploadFileNotEmptyForListValidator;
import com.example.ssa.ssa.validator.UploadFileNotEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UploadFileNotEmptyValidator.class, UploadFileNotEmptyForListValidator.class})
public @interface UploadFileNotEmpty {
    String message() default "{image.upload.empty}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        UploadFileNotEmpty[] value();
    }

}
