 package org.nebularis.test.org.jcheck.generator.primitive;

import java.util.Random;

import org.nebularis.test.org.jcheck.Arbitrary;
import org.nebularis.test.org.jcheck.generator.Gen;

public class ByteGen implements Gen<Byte>
{
    public Byte arbitrary(Random random, long size)
    {
        long maxSize = Math.min(size, Byte.MAX_VALUE);
        long minSize = Math.max(-size, Byte.MIN_VALUE);
        
        return Byte.valueOf((byte) Arbitrary.random(random, minSize, maxSize));
    }
}
