package org.nebularis.test.org.jcheck.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Configuration {
    long size() default 0;
    int tests() default 0;
    int maxNumberOfFailedParams() default 0;
}
