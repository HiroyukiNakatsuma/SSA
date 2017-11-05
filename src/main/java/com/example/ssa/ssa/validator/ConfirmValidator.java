package com.example.ssa.ssa.validator;

import com.example.ssa.ssa.component.annotation.Confirm;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 指定したフィールドと確認用のフィールドが一致しているかをチェックする。
 * 確認用のフィールドは、"confirm" + "指定のフィールド名"である必要がある。
 */
public class ConfirmValidator implements ConstraintValidator<Confirm, Object> {
    private String field;

    private String confirmField;

    private String message;

    public void initialize(Confirm constraintAnnotation) {
        field = constraintAnnotation.field();
        confirmField = "confirm" + StringUtils.capitalize(field);
        message = constraintAnnotation.message();
    }

    /**
     * 指定したフィールドと確認用のフィールドが一致しているかをチェック
     * どちらかが null の場合は、false
     */
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        boolean matched = ObjectUtils.nullSafeEquals(
                beanWrapper.getPropertyValue(field),
                beanWrapper.getPropertyValue(confirmField));
        if (matched) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(field).addConstraintViolation();
            return false;
        }
    }
}
