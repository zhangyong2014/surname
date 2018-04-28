package com.forward.surname.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mings on 2018/4/28.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldName {
    String filedName() default "";
}
