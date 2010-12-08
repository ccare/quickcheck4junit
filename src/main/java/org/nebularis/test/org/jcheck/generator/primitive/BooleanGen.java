/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nebularis.test.org.jcheck.generator.primitive;

import java.util.Random;

import org.nebularis.test.org.jcheck.generator.Gen;

/**
 *
 * @author Hampus
 */
public class BooleanGen implements Gen<Boolean> 
{
    public Boolean arbitrary(Random random, long size)
    {
        return Boolean.valueOf(random.nextBoolean());
    }
}
