package org.nebularis.test.org.jcheck.generator.primitive;

import java.math.BigDecimal;
import java.util.Random;

import org.nebularis.test.org.jcheck.generator.Gen;

public class BigDecimalGen implements Gen<BigDecimal>
{
    private static final BigIntegerGen intGen = new BigIntegerGen();
    
    public BigDecimal arbitrary(Random random, long size)
    {
        return new BigDecimal(intGen.arbitrary(random, size));
    }
}
