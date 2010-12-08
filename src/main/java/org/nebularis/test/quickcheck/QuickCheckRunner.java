package org.nebularis.test.quickcheck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.junit.Ignore;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.nebularis.test.org.jcheck.Configuration;
import org.nebularis.test.org.jcheck.internal.runners.statements.QuickCheckStatement;
import org.nebularis.test.org.jcheck.runners.JCheckRunner;
/**
 * This is a variant of the {@link JCheckRunner}. It differs in the following way
 * <ul>
 * <li>JCheckRunner stops execution the moment it identifies an assertFailure or Exception. But this Custom runner will continue to execute
 * all the tests and reports the failures and successes.
 * </li>
 * <li>
 * {@link org.nebularis.test.org.jcheck.annotations.Configuration#tests()} defined at the test method level overrides the one defined at the Class level, if any. 
 * </li>
 * <li>
 * {@link org.nebularis.test.org.jcheck.annotations.Configuration} is optional. It should otherwise execute as a normal test.
 * </li>
 * </ul>
 * @author dinu.07
 *
 */
public class QuickCheckRunner extends JCheckRunner {
	/**
	 * Calls the super class constructor
	 * @param klass
	 * @throws InitializationError
	 */
	public QuickCheckRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	/**
	 * {@link BlockJUnit4ClassRunner} runChild method is overridden.
	 */
	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		// create a Notifier for each of the method to be run
		Collection<EachTestNotifier> eachNotifiers = makeNotifier(method,
				notifier);
		for (EachTestNotifier eachNotifier : eachNotifiers) {
			if (method.getAnnotation(Ignore.class) != null) {
				runIgnored(eachNotifier);
			} else {
				runNotIgnored(method, eachNotifier);
			}
		}

	}

	private void runNotIgnored(FrameworkMethod method,
			EachTestNotifier eachNotifier) {
		eachNotifier.fireTestStarted();
		try {
			methodBlock(method).evaluate();
		} catch (AssumptionViolatedException e) {
			eachNotifier.addFailedAssumption(e);
		} catch (Throwable e) {
			eachNotifier.addFailure(e);
		} finally {
			eachNotifier.fireTestFinished();
		}
	}

	@Override
	protected Statement methodBlock(FrameworkMethod method) {
		Object test;
		try {
			test = new ReflectiveCallable() {
				@Override
				protected Object runReflectiveCall() throws Throwable {
					return createTest();
				}
			}.run();
		} catch (Throwable e) {
			return new Fail(e);
		}

		Statement statement = methodInvoker(method, test);
		statement = possiblyExpectingExceptions(method, test, statement);
		statement = withPotentialTimeout(method, test, statement);
		statement = withBefores(method, test, statement);
		statement = withAfters(method, test, statement);
		return statement;
	}

	private void runIgnored(EachTestNotifier eachNotifier) {
		eachNotifier.fireTestIgnored();
	}

	/**
	 * Returns a Collection of Notifiers
	 * 
	 * @param method
	 * @param notifier
	 * @return
	 */
	private Collection<EachTestNotifier> makeNotifier(FrameworkMethod method,
			RunNotifier notifier) {
		Collection<EachTestNotifier> notifiers = new ArrayList<EachTestNotifier>();
		Description description = describeChild(method);
		for (long i = 0; i < noOfTestsToBeExecuted(method); i++) {
			notifiers.add(new EachTestNotifier(notifier, description));
		}
		return notifiers;
	}

	/**
	 * Returns the number of times the test method has to be executed. Priority
	 * is high for method level configuration if it exists
	 * 
	 * @param method
	 * @return
	 */
	private long noOfTestsToBeExecuted(FrameworkMethod method) {
		long size = 1;
		// defined at the class level
		if (configuration.getMaxNumberOfTests() > 0) {
			size = configuration.getMaxNumberOfTests();
		}
		// overridden at the method level
		org.nebularis.test.org.jcheck.annotations.Configuration configAnnotation = method
				.getAnnotation(org.nebularis.test.org.jcheck.annotations.Configuration.class);
		if (configAnnotation != null) {
			if (configAnnotation.tests() > 0) {
				size = configAnnotation.tests();
			}
		}
		return size;
	}

	@Override
	protected Statement methodInvoker(FrameworkMethod method, Object test) {
		try {
			return new QuickCheckStatement(
					(Configuration) configuration.clone(), classGenerators,
					method, test);
		} catch (CloneNotSupportedException ex) {
			throw new RuntimeException("Unable to copy configuration.", ex);
		}
	}
}
