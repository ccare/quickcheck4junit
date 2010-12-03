package org.nebularis.test.quickcheck;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nebularis.test.quickcheck.QuickCheckRunner;

import static junit.framework.Assert.assertTrue;

@RunWith(QuickCheckRunner.class)
public class QuickCheckRunnerTestCase {

    @Test
    public void checkSimpleTest() {
        assertTrue(true);
    }

}
