package org.nebularis.test.annotations;

import org.jmock.integration.junit4.JMock;
import org.junit.runner.Runner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RunnerConfig {

    Class<? extends Runner>[] value();
}
