package org.nebularis.test.quickcheck;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nebularis.test.quickcheck.QuickCheckRunner;

@RunWith(QuickCheckRunner.class)
public class AssertionErrorTestCase {
	/**
	 * Test that reversing a string doesn't give the same string back (which of
	 * course is a bad property since it can)
	 */
	@Test(expected = AssertionError.class)
	public void notAPropertyOfReverse(String str) {
		StringBuffer buff = new StringBuffer(str);
		if (!str.equals(buff.reverse().toString())) {
			fail();
		}
	}

	/**
	 * One might think that adding two numbers together will give a number
	 * greater than both of those two numbers...
	 */
	@Test(expected = AssertionError.class)
	public void notAPropertyOfAdd(int i, int j) {
		assertTrue(i + j < i && i + j < j);
	}

}
