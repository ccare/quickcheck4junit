package org.nebularis.test.org.jcheck.exceptions;

import java.util.Arrays;

/**
 *
 * @author Hampus
 */
public class AssertionFailedError extends junit.framework.AssertionFailedError
{
    private String parameters;
    private int numtests;
    private Throwable error;

    public AssertionFailedError(Object[] params, int numtests, Throwable error)
    {
        this.parameters = Arrays.toString(params);
        this.numtests = numtests;
        this.error = error;
    }
    
    @Override
    public String getMessage()
    {
        return String.format("%s after %d tests with input parameters %s.", 
                             error.getMessage(), numtests, parameters);
    }
}
