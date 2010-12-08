package org.nebularis.test.org.jcheck.generator.primitive;

import java.util.Random;

import org.nebularis.test.org.jcheck.Arbitrary;
import org.nebularis.test.org.jcheck.generator.Gen;

public class IntegerGen implements Gen<Integer>
{
    public Integer arbitrary(Random random, long size)
    {
        long maxSize = Math.min(size, Integer.MAX_VALUE);
        long minSize = Math.max(-size, Integer.MIN_VALUE);
        
        return Integer.valueOf((int) Arbitrary.random(random, minSize, maxSize));
    }
}
