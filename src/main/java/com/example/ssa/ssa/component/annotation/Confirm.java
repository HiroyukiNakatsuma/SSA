package com.example.ssa.ssa.component.annotation;

import com.example.ssa.ssa.validator.ConfirmValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 指定したフィールドと確認用のフィールドが一致しているかをチェックする
 */
@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConfirmValidator.class)
public @interface Confirm {
    String message() default "{confirm.not.match}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Confirm[] value();
    }
}
