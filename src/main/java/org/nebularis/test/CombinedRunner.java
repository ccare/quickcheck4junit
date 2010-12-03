package org.nebularis.test;


import org.apache.commons.beanutils.BeanUtils;
import org.jmock.integration.junit4.JMock;
import org.junit.internal.runners.InitializationError;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.nebularis.test.annotations.RunnerConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: carecx
 * Date: 03-Dec-2010
 * Time: 12:21:40
 * To change this template use File | Settings | File Templates.
 */
public class CombinedRunner extends Runner {

    final Runner[] delegates;

    public CombinedRunner(Class<?> clazz) throws org.junit.runners.model.InitializationError, InitializationError, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        final RunnerConfig config = clazz.getAnnotation(RunnerConfig.class);
        final Class<? extends Runner>[] runnerClasses = config.value();
        delegates = new Runner[runnerClasses.length];
        for (int i = 0; i< runnerClasses.length; i++) {
            final Class<? extends Runner> runnerClass = runnerClasses[i];
            final Constructor<? extends Runner> constructor = runnerClass.getConstructor(Class.class);
            Runner delegate = constructor.newInstance(clazz);
            delegates[i] = delegate;
        }
    }

    @Override
    public Description getDescription() {
        return delegates[0].getDescription();
    }

    @Override
    public void run(RunNotifier notifier) {
        for (Runner r : delegates) {
            r.run(notifier);
        }
    }
}
