package org.nebularis.test.org.jcheck;

import java.util.Random;

public final class Configuration implements Cloneable
{
    private final Random random;
    private int maxNumberOfTests;
    private int maxNumberOfFailedParams;
    private long size;

    public Configuration(Random random)
    {
        this(random, 1, 500, 100);
    }
    
    public Configuration(Random random, int maxNumberOfTests,
                         int maxNumberOfFailedParams, long size)
    {
        this.random = random;
        this.maxNumberOfTests = maxNumberOfTests;
        this.maxNumberOfFailedParams = maxNumberOfFailedParams;
        this.size = size;
    }
    
    public void setSize(long size)
    {
        this.size = size;
    }
    
    public void setMaxNumberOfTests(int tests)
    {
        maxNumberOfTests = tests;
    }
      
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Configuration config = (Configuration) super.clone();
        return config;
    }

    public Random getRandom()
    {
        return random;
    }

    public int getMaxNumberOfTests()
    {
        return maxNumberOfTests;
    }

    public int getMaxNumberOfFailedParams()
    {
        return maxNumberOfFailedParams;
    }

    public long getSize()
    {
        return size;
    }
}
