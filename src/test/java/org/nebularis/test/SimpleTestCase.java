package org.nebularis.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nebularis.test.org.jcheck.Arbitrary;
import org.nebularis.test.org.jcheck.annotations.Configuration;
import org.nebularis.test.org.jcheck.annotations.Generator;
import org.nebularis.test.org.jcheck.generator.NullGen;
import org.nebularis.test.org.jcheck.generator.PercentageGen;
import org.nebularis.test.org.jcheck.generator.primitive.IntegerGen;
import org.nebularis.test.org.jcheck.runners.JCheckRunner;

/**
 * 
 * @author Hampus
 */
@RunWith(JCheckRunner.class)
@Configuration(size = 50)
public class SimpleTestCase {
	@Test
	public void arrayIsNotNull(int[][] i) {
		for (int j = 0; j < i.length; ++j) {
			for (int k = 0; k < i[j].length; ++k) {
				assertNotNull(i[j][k]);
			}
		}
	}

	@Test
	public void classConfigurationTest(int i) {
		assertTrue("@Configuration for class (or int generator) failed",
				i >= -50 && i <= 50);
	}

	@Test
	@Configuration(size = 5)
	public void methodConfigurationTest(int i) {
		assertTrue("@Configuration for methods (or in generator) failed",
				i >= -5 && i <= 5);
	}

	@Test
	public void testReverse(String str) {
		StringBuffer buff = new StringBuffer(str);
		assertEquals(str, buff.reverse().reverse().toString());
	}

	@Test
	@Generator(klass = double.class, generator = PercentageGen.class)
	public void testNullGen(double d) {
		final int ITERATIONS = 10000;

		Random rand = new Random();
		NullGen<Integer> ngen = new NullGen<Integer>(new IntegerGen(), d);
		int nulls = 0;
		for (int i = 0; i < ITERATIONS; ++i) {
			if (ngen.arbitrary(rand, 100) == null) {
				nulls++;
			}
		}

		assertEquals(nulls / (double) ITERATIONS, d, 0.05);
	}

	@Test
	public void testArbitraryRandom() {
		Random random = new Random(System.currentTimeMillis());

		for (int i = 0; i < 10000; ++i) {
			long rand = Arbitrary.random(random, -3, 3);
			assertTrue("Should not be less than -3 or greater than 3",
					rand >= -3 && rand <= 3);

			rand = Arbitrary.random(random, 1, 1);
			assertTrue("Should not be anything other than 1", rand == 1);
		}
	}
}
