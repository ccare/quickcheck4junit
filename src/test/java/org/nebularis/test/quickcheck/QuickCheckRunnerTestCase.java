package org.nebularis.test.quickcheck;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nebularis.test.org.jcheck.annotations.Configuration;
import org.nebularis.test.quickcheck.QuickCheckRunner;

import static junit.framework.Assert.assertTrue;

@RunWith(QuickCheckRunner.class)
@Configuration(tests=2)
public class QuickCheckRunnerTestCase {

	private static int count = 0;
    
	private static int count1 = 0;
	
	private static int count2 = 0;
	
	private static int count3 = 0;
	
	@Test
    public void checkSimpleTest() {
        count++;
		assertTrue(true);
    }

	@Test
	@Configuration(tests=5)
    public void checkConfigurationOverridenAtTestMethodLevel() {
        count1++;
		assertTrue(true);
    }

	
	@Test
	@Configuration(size=5)
    public void checkConfigurationOverridenAtTestMethodLevelWithoutTestPicksFromClassLevel() {
        count2++;
		assertTrue(true);
    }
	
	@Test
	@Configuration(size=5,tests=3)
    public void checkConfigurationBothOverridenAtTestMethodLevel() {
        count3++;
		assertTrue(true);
    }
	
	@AfterClass
	public static void confirmExecutionCount(){
		assertTrue(count == 2);
		assertTrue(count1 == 5);
		assertTrue(count2 == 2);
		assertTrue(count3 == 3);
	}
	
	
}
