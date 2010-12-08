package org.nebularis.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nebularis.test.gen.OnesGen;
import org.nebularis.test.gen.SizeTwoArrayGen;
import org.nebularis.test.gen.TwoFourSixGen;
import org.nebularis.test.gen.TwosGen;
import org.nebularis.test.org.jcheck.annotations.Generator;
import org.nebularis.test.org.jcheck.annotations.UseGenerators;
import org.nebularis.test.org.jcheck.runners.JCheckRunner;

@RunWith(JCheckRunner.class)
@Generator(klass = Integer.class, generator = TwosGen.class)
public class GeneratorTestCase {
	@Test
	@UseGenerators({ @Generator(generator = OnesGen.class, klass = int.class) })
    public void testMethodUseGenerator(int i)
    {
        assertEquals("Should be one", 1, i);
    }

    @Test
    @Generator(position = 0, generator = OnesGen.class)
    public void testMethodGenerator(Integer i)
    {
        assertEquals("Should be one", 1, i.intValue());
    }

    @Test
    public void testClassGenerator(Integer i)
    {
        assertEquals(2, i.intValue());
    }
    
    @Test
    @Generator(klass = int.class, 
               generator = TwoFourSixGen.class)
    public void testElementGen(int i)
    {
        assertTrue(i == 2 || i == 4 || i == 6);
    }
    
    @Test
    @Generator(klass = int[].class, 
               generator = SizeTwoArrayGen.class)
    public void testCustomArrayGen(int[] i)
    {
        assertTrue(i.length == 2);
    }
}
