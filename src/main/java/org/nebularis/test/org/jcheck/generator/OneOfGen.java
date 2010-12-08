package org.nebularis.test.org.jcheck.generator;

import java.util.Random;

public class OneOfGen<T> implements Gen<T>
{
    private Gen<T>[] generators;

    public OneOfGen(Gen<T> ... generators)
    {
        this.generators = generators;
    }
    
    public T arbitrary(Random random,long size)
    {
        Gen<T> generator = generators[random.nextInt(generators.length)];
        return generator.arbitrary(random, size);
    }
}
