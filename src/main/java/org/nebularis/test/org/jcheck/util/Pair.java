package org.nebularis.test.org.jcheck.util;

public final class Pair<T1, T2>
{
    private final T1 first;
    private final T2 second;

    /** Creates a new instance of Pair */
    public Pair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }
    
    public T1 fst()
    {
        return first;
    }
    
    public T2 snd()
    {
        return second;
    }
    
    public static <T1, T2> Pair<T1, T2> make(T1 first, T2 second)
    {
        return new Pair<T1, T2>(first, second);
    }
}
