package org.nebularis.test.org.jcheck.runners;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.nebularis.test.org.jcheck.Configuration;
import org.nebularis.test.org.jcheck.annotations.Generator;
import org.nebularis.test.org.jcheck.annotations.UseGenerators;
import org.nebularis.test.org.jcheck.generator.Gen;
import org.nebularis.test.org.jcheck.internal.runners.statements.JCheckStatement;

public class JCheckRunner extends BlockJUnit4ClassRunner 
{
    protected Configuration configuration;
    protected final Map<Class<?>, Gen<?>> classGenerators;
    
    public JCheckRunner(Class<?> klass) throws InitializationError
    {
    	super(klass);
    	configuration = new Configuration(new Random(System.currentTimeMillis()));
    	
        org.nebularis.test.org.jcheck.annotations.Configuration config = getClassAnnotation(org.nebularis.test.org.jcheck.annotations.Configuration.class);
        if (config != null) {
            if (config.size() != 0) {
                configuration.setSize(config.size());
            }

            if (config.tests() != 0) {
                configuration.setMaxNumberOfTests(config.tests());
            }
        }

        classGenerators = new HashMap<Class<?>, Gen<?>>();

        Generator generator = getClassAnnotation(Generator.class);
        if (generator != null) {
            addGenerator(generator);
        }

        UseGenerators useGenerators = getClassAnnotation(UseGenerators.class);
        if (useGenerators != null) {
           for (Generator gen : useGenerators.value()) {
               addGenerator(gen);
           }
        }
    }
    
    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test)
    {
        try {
            return new JCheckStatement((Configuration) configuration.clone(), 
                                       classGenerators,
                                       method, test);
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Unable to copy configuration.", ex);
        }
    }

    @Override
    protected void validateTestMethods(List<Throwable> errors)
    {
        List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Test.class);

        for (FrameworkMethod eachTestMethod : methods) {
            eachTestMethod.validatePublicVoid(false, errors);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Annotation> T getClassAnnotation(Class<T> annotation)
    {
        Annotation[] classAnnotations = getTestClass().getAnnotations();
        for (Annotation classAnnotation : classAnnotations) {
            if (classAnnotation.annotationType() == annotation) {
                return (T)classAnnotation;
            }
        }
        
        return null;
    }
    
    private void addGenerator(Generator generator) {
        Class<?> klass = generator.klass();
        Class<? extends Gen<?>> genClass = generator.generator();

        // This might not be possible, but I'm unsure.
        if (klass == null || genClass == null) {
            throw new RuntimeException("Bad @Generator found.");
        }

        if (classGenerators.containsKey(klass)) {
            throw new RuntimeException("Duplicate generators found.");
        }

        try {
            Gen<?> gen = genClass.newInstance();
            classGenerators.put(klass, gen);
        }
        catch (InstantiationException ex) {
            throw new RuntimeException(String.format("Unable to instantiate generator %s.", genClass.getName()), ex);
        }
        catch (IllegalAccessException ex) {
            throw new RuntimeException(String.format("Unable to instantiate generator %s.", genClass.getName()), ex);
        }
    }
}
