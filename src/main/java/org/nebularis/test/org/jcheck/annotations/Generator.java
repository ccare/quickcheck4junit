package org.nebularis.test.org.jcheck.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.nebularis.test.org.jcheck.generator.Gen;

/**
 * 
 * @author Hampus
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Generator {
	int position() default -1;

	Class<? extends Gen<?>> generator();

	Class<?> klass() default Object.class;
}
