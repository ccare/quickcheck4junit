package org.nebularis.test.org.jcheck.generator.primitive;

import java.util.Random;

import org.nebularis.test.org.jcheck.Arbitrary;
import org.nebularis.test.org.jcheck.generator.Gen;

public class ShortGen implements Gen<Short>
{
    public Short arbitrary(Random random, long size)
    {
        long maxSize = Math.min(size, Short.MAX_VALUE);
        long minSize = Math.max(-size, Short.MIN_VALUE);
        
        return Short.valueOf((short) Arbitrary.random(random, minSize, maxSize));
    }
}
