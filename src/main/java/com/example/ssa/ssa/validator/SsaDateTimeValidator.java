package com.example.ssa.ssa.validator;

import com.example.ssa.ssa.component.annotation.SsaDateTime;
import com.example.ssa.ssa.controller.form.PlanCreateForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SsaDateTimeValidator implements ConstraintValidator<SsaDateTime, PlanCreateForm> {
    private String message;

    public void initialize(SsaDateTime constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    /**
     * 開始日時が終了日時よりも前であるかをチェック
     */
    public boolean isValid(PlanCreateForm value, ConstraintValidatorContext context) {
        if (LocalDateTime.parse(value.getStartDate().concat(" ").concat(value.getStartTime().concat(":00")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .isAfter(LocalDateTime.parse(value.getEndDate().concat(" ").concat(value.getEndTime().concat(":00")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addBeanNode().addConstraintViolation();
            return false;
        }
    }
}
