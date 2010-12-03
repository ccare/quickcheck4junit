package org.nebularis.test.quickcheck;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class QuickCheckRunner extends BlockJUnit4ClassRunner {

    public QuickCheckRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }
}
