package com.yanshen.common.core.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义枚举校验注解
 *
 * @author zhcl
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValue.EnumValueValidator.class})
public @interface EnumValue {

    // 默认错误消息
    String message() default "必须为指定值";

    String[] strValues() default {};

    int[] intValues() default {};

    // 分组
    Class<?>[] groups() default {};

    // 负载
    Class<? extends Payload>[] payload() default {};

    class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

        private String[] strValues;
        private int[] intValues;

        /**
         * Initializes the validator in preparation for
         * {@link #isValid(Object, ConstraintValidatorContext)} calls.
         * The constraint annotation for a given constraint declaration
         * is passed.
         * <p>
         * This method is guaranteed to be called before any use of this instance for
         * validation.
         * <p>
         * The default implementation is a no-op.
         *
         * @param constraintAnnotation annotation instance for a given constraint declaration
         */
        @Override
        public void initialize(EnumValue constraintAnnotation) {
            strValues = constraintAnnotation.strValues();
            intValues = constraintAnnotation.intValues();
        }

        /**
         * Implements the validation logic.
         * The state of {@code value} must not be altered.
         * <p>
         * This method can be accessed concurrently, thread-safety must be ensured
         * by the implementation.
         *
         * @param value   object to validate
         * @param context context in which the constraint is evaluated
         * @return {@code false} if {@code value} does not pass the constraint
         */
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value instanceof String) {
                for (String s : strValues) {
                    if (s.equals(value)) {
                        return true;
                    }
                }
            } else if (value instanceof Integer) {
                for (int s : intValues) {
                    if (s == ((Integer) value).intValue()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
