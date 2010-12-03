package com.nebularis.quickcheck4junit;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(QuickCheckRunner.class)
public class QuickCheckRunnerTestCase {

    @Test
    public void checkSimpleTest() {
        assertTrue(true);
    }

}
