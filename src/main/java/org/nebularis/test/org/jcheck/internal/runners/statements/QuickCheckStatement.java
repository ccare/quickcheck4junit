package org.nebularis.test.org.jcheck.internal.runners.statements;

import java.util.Map;

import junit.framework.AssertionFailedError;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.nebularis.test.org.jcheck.Configuration;
import org.nebularis.test.org.jcheck.exceptions.ImplicationFalseError;
import org.nebularis.test.org.jcheck.generator.Gen;

/**
 * This is an extension to the {@link JCheckStatement}. This is responsible for
 * building the {@link Statement} that contains the logic for executing the test
 * case.
 * 
 * @author dinu.07
 * 
 */
public class QuickCheckStatement extends JCheckStatement {
	/**
	 * Calls the super class
	 * @param configuration
	 * @param classGenerators
	 * @param method
	 * @param test
	 */
	public QuickCheckStatement(Configuration configuration,
			Map<Class<?>, Gen<?>> classGenerators, FrameworkMethod method,
			Object test) {
		super(configuration, classGenerators, method, test);
	}

	@Override
	public void evaluate() throws Throwable {
		// get the size that determines the boundary for Generator's generated values
		long size = configuration.getSize();
		// check whether at the method level, the size parameter is being specified
		// if so override with that value
		org.nebularis.test.org.jcheck.annotations.Configuration configAnnotation = method
				.getAnnotation(org.nebularis.test.org.jcheck.annotations.Configuration.class);
		if (configAnnotation != null) {
			if (configAnnotation.size() != 0) {
				size = configAnnotation.size();
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

		for (int failed = 1;; ++failed) {
			for (int pNo = 0; pNo < paramClasses.length; ++pNo) {
				paramList[pNo] = arbitrary(paramClasses[pNo], pNo,
						configuration.getRandom(), size);
			}

			try {
				method.invokeExplosively(test, paramList);
				break;
			} catch (ImplicationFalseError ex) {
				if (failed >= configuration.getMaxNumberOfFailedParams()) {
					throw new AssertionFailedError(String.format(
							"Arguments exhausted after %d test (%d tries). %s",
							1, failed, ex.getMessage()));
				}
			} catch (AssertionError ex) {
				if (paramList.length > 0) {
					throw new org.nebularis.test.org.jcheck.exceptions.AssertionFailedError(
							paramList, 1, ex);
				} else {
					throw ex;
				}
			}
		}
	}

}
