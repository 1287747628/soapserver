package com.fonsview.soapserver.vo;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldAlias {

    /**
     * The name of the class or field alias.
     */
    public String value() default "";
}
