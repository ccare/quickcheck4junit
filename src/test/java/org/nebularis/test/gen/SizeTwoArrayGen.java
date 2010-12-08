package org.nebularis.test.gen;

import java.util.Random;

import org.nebularis.test.org.jcheck.Arbitrary;
import org.nebularis.test.org.jcheck.generator.Gen;

public class SizeTwoArrayGen implements Gen<int[]> 
{
    public int[] arbitrary(Random random, long size)
    {
        size = Math.min(Integer.MAX_VALUE, size);
        
        int[] res = new int[2];
        res[0] = (int) Arbitrary.random(random, -size, size);
        res[1] = (int) Arbitrary.random(random, -size, size);
        
        return res;
    }
}
