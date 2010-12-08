package org.nebularis.test;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nebularis.test.org.jcheck.annotations.Configuration;
import org.nebularis.test.org.jcheck.runners.JCheckRunner;

@RunWith(JCheckRunner.class)
public class ConfigurationTestCase
{
    private int count = 0;
    private static int count2 = 0;
    
    @Test
    @Configuration(tests=3)
    public void configurationSetNumberOfTests(int i)
    {
        count++;
        assertTrue("Should not run more than 3 tests", count < 4);
    }
    
    @Test
    public void configurationDontSetNumberOfTests(int i)
    {
        count2++;
    }
    
    @AfterClass
    public static void configurationDontSetNumberOfTestsCheckIt()
    {
        assertTrue(count2 == 1);
    }
}
