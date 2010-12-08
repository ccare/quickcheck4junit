package org.nebularis.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nebularis.test.org.jcheck.annotations.Configuration;
import org.nebularis.test.quickcheck.QuickCheckRunner;

@RunWith(QuickCheckRunner.class)
@Configuration(tests = 2)
public class NormalTestCase {

	@Test
	public void checkWhetherNormalBlockRunsFine() {
		assertTrue(true);
	}

	@Test
	public void checkWhetherNormalBlockRunsFine1() {
		assertFalse(false);
	}
}
