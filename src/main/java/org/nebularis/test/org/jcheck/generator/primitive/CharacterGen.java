/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nebularis.test.org.jcheck.generator.primitive;

import java.util.Random;

import org.nebularis.test.org.jcheck.Arbitrary;
import org.nebularis.test.org.jcheck.generator.Gen;

/**
 *
 * @author Hampus
 */
public class CharacterGen implements Gen<Character> 
{
    public Character arbitrary(Random random, long size)
    {
        return Character.valueOf((char)Arbitrary.random(random, 32, 255));
    }
}
