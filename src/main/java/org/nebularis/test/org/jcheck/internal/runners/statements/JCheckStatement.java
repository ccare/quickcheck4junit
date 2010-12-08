/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nebularis.test.org.jcheck.internal.runners.statements;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import junit.framework.AssertionFailedError;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.nebularis.test.org.jcheck.Arbitrary;
import org.nebularis.test.org.jcheck.Configuration;
import org.nebularis.test.org.jcheck.annotations.Generator;
import org.nebularis.test.org.jcheck.annotations.UseGenerators;
import org.nebularis.test.org.jcheck.exceptions.ImplicationFalseError;
import org.nebularis.test.org.jcheck.generator.Gen;
import org.nebularis.test.org.jcheck.generator.primitive.ArrayGen;

public class JCheckStatement extends Statement
{
    protected Configuration configuration;
    protected FrameworkMethod method;
    protected Object test;
    
    private Map<Integer, Gen<?>> fromPositionGenerators = new HashMap<Integer, Gen<?>>();
    private Map<Class<?>, Gen<?>> fromClassGenerators = new HashMap<Class<?>, Gen<?>>();
    private Map<Class<?>, Gen<?>> classGenerators = new HashMap<Class<?>, Gen<?>>();
    
    public JCheckStatement(Configuration configuration,
                           Map<Class<?>, Gen<?>> classGenerators, 
                           FrameworkMethod method, 
                           Object test)
    {
        this.configuration = configuration;
        this.classGenerators = classGenerators;
        this.method = method;
        this.test = test;
        
        collectGenerators();
    }
    
    @Override
    public void evaluate() throws Throwable
    {
        int maxNumberOfTests = configuration.getMaxNumberOfTests();
        long size = configuration.getSize();

        org.nebularis.test.org.jcheck.annotations.Configuration configAnnotation = method.getAnnotation(org.nebularis.test.org.jcheck.annotations.Configuration.class);
        if (configAnnotation != null) {
            if (configAnnotation.size() != 0) {
                size = configAnnotation.size();
            }
            
            if (configAnnotation.tests() != 0) {
                maxNumberOfTests = configAnnotation.tests();
            }
        }

        Class<?>[] paramClasses = method.getMethod().getParameterTypes();
        Object[] paramList = new Object[paramClasses.length];

        // If this is a test that has no parameters, only
        // run it once as JUnit normally does
        if (paramClasses.length == 0) {
            method.invokeExplosively(test, paramList);
            return;
        }
        
        for (int tests = 0; tests < maxNumberOfTests; ++tests) {
            for (int failed = 1; ; ++failed) {
                for (int pNo = 0; pNo < paramClasses.length; ++pNo) {
                    paramList[pNo] = arbitrary(paramClasses[pNo], pNo, 
                                               configuration.getRandom(), size);
                }

                try {
                    method.invokeExplosively(test, paramList);
                    break;
                }
                catch (ImplicationFalseError ex) {
                    if (failed >= configuration.getMaxNumberOfFailedParams()) {
                        throw new AssertionFailedError(String.format("Arguments exhausted after %d test (%d tries). %s", 
                                                                     tests,
                                                                     failed,
                                                                     ex.getMessage()));
                    }
                }
                catch(AssertionError ex) {
                    if (paramList.length > 0) {
                    	throw new org.nebularis.test.org.jcheck.exceptions.AssertionFailedError(paramList, tests, ex);
                    }
                    else {
                        throw ex;
                    }
                }
            }
        }
    }
    
    private void collectGenerators()
    {
        UseGenerators useGenerators = method.getAnnotation(UseGenerators.class);
        if (useGenerators != null) {
            for (Generator generator : useGenerators.value()) {
                collectGenerator(generator);
            }
        }

        Generator generator = method.getAnnotation(Generator.class);
        collectGenerator(generator);
    }
    
    private void collectGenerator(Generator generator) {
        if (generator != null) {
            if (generator.position() < 0) {
                Class<?> klass = generator.klass();

                // I'm unsure if this can happen
                if (klass == null) {
                    throw new RuntimeException("Bad @Generator found.");
                }

                Class<? extends Gen<?>> genClass = generator.generator();
                try {
                    Gen<?> gen = genClass.newInstance();
                    fromClassGenerators.put(klass, gen);
                }
                catch (Exception ex) {
                    throw new RuntimeException(String.format("Unable to instantiate generator %s.", genClass.getName()), ex);
                }
            }
            else {
                Integer position = generator.position();
                if (position >= method.getMethod().getParameterTypes().length) {
                    throw new RuntimeException(String.format("Position %d given for a generator is greater than the number of arguments to %s.",
                                                             position, method.getName()));
                }

                Class<? extends Gen<?>> genClass = generator.generator();
                try {
                    Gen<?> gen = genClass.newInstance();
                    fromPositionGenerators.put(position, gen);
                }
                catch (Exception ex) {
                    throw new RuntimeException(String.format("Unable to instantiate generator %s.", genClass.getName()), ex);
                }
            }
        }
    }
    
    protected Object arbitrary(Class<?> paramClass, int position, 
                             Random random, long size)
    {
        Gen<?> generator = generator(paramClass, position);
        return generator.arbitrary(random, size);
    }

    private Gen<?> generator(Class<?> paramClass, int position)
    {
        Gen<?> generator = fromPositionGenerators.get(position);
        if (generator != null) {
            return generator;
        }

        generator = fromClassGenerators.get(paramClass);
        if (generator != null) {
            return generator;
        }

        generator = classGenerators.get(paramClass);
        if (generator != null) {
            return generator;
        }

        if (paramClass.isArray()) {
            return generatorForArray(paramClass, position);
        }
        
        generator = Arbitrary.getStandardGenerator(paramClass);
        if (generator != null) {
            return generator;
        }

        throw new RuntimeException(String.format("Unable to find a matching generator for class %s.",
                                                 paramClass.getName()));
    }

    private Gen<?> generatorForArray(Class<?> paramClass, int position)
    {
        int dimension = 0;
        Class<?> component = paramClass;
        while (true) {
            Class<?> tmp = component.getComponentType();
            if (tmp != null) {
                dimension++;
                component = tmp;
            }
            else {
                break;
            }
        }

        return new ArrayGen(dimension, component, 
                            generator(component, position));
    }
}
