package org.nebularis.test.gen;

import java.util.Random;

import org.nebularis.test.org.jcheck.generator.Gen;

public class TwosGen implements Gen<Integer> 
{
    private static final Integer i = 2;
    public Integer arbitrary(Random random,long size)
    {
        return i;
    }
}
