/**
 * Copyright (C) cedarsoft GmbH.
 *
 * Licensed under the GNU General Public License version 3 (the "License")
 * with Classpath Exception; you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *         http://www.cedarsoft.org/gpl3ce
 *         (GPL 3 with Classpath Exception)
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation. cedarsoft GmbH designates this
 * particular file as subject to the "Classpath" exception as provided
 * by cedarsoft GmbH in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact cedarsoft GmbH, 72810 Gomaringen, Germany,
 * or visit www.cedarsoft.com if you need additional information or
 * have any questions.
 */

package org.nebularis.test;

import org.jmock.integration.junit4.JMock;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nebularis.test.annotations.RunnerConfig;
import org.nebularis.test.org.jcheck.annotations.Configuration;
import org.nebularis.test.org.jcheck.annotations.Generator;
import org.nebularis.test.org.jcheck.annotations.UseGenerators;
import org.nebularis.test.org.jcheck.exceptions.AssertionFailedError;
import org.nebularis.test.org.jcheck.generator.primitive.IntegerGen;
import org.nebularis.test.org.jcheck.generator.primitive.StringGen;
import org.nebularis.test.quickcheck.QuickCheckRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@RunWith(QuickCheckRunner.class)
@RunnerConfig(JMock.class)
public class CombinedRunnerTestCase extends AbstractJMockTestSupport {

	
	private static int successCount = 0;
	private static int failureCount = 0;
	
    @Test
    public void testCanRunNormalJUnit() {
        assertTrue(true);
    }

    
    
    @Test
    public void testCanRunNormalJMock() {
        final CharSequence converter = mock(CharSequence.class);
        one(converter).length();
        will(returnValue(59));
        confirmExpectations();

        assertThat(converter.length(), is(equalTo(59)));
    }

    
    @Test
    @Configuration(tests=3)
    public void testCanRunNormalJMockWithConfigParams() {
        final CharSequence converter = mock(CharSequence.class);
        one(converter).length();
        will(returnValue(59));
        confirmExpectations();

        assertThat(converter.length(), is(equalTo(59)));
    }
    

    @Test
    @Configuration(tests=3,size=5)
    @Generator(generator=StringGen.class)
    public void testCanRunNormalJMockWithGenerator(String str) {
        final CharSequence converter = mock(CharSequence.class);
        one(converter).length();
        will(returnValue(str.length()));
        confirmExpectations();

        assertThat(converter.length(), is(equalTo(str.length())));
    }

    @Test
    @Configuration(tests=4,size=5)
     @UseGenerators({
                @Generator(position=0,
                           generator=StringGen.class),
                @Generator(position=1,
                           generator=IntegerGen.class)})    
    public void testCanRunNormalJMockWithUseGenerator(String str, int num) {
        final CharSequence converter = mock(CharSequence.class);
        one(converter).length();
        will(returnValue(str.length() + num));
        confirmExpectations();

        assertThat(converter.length(), is(equalTo(str.length() + num)));
    }

    
//    @Test
//    @Configuration(tests=10,size=5)
//    @Generator(generator=IntegerGen.class)
//    public void testCheckWhetherAllTestsExecutesEvenIfFailuresHappen(int a) {
//    	
//    	try {
//    		assertTrue(a > 0);
//    		successCount++;
//    	} catch (AssertionFailedError afe) {
//    		failureCount++;
//    	}
//    }
//    
//    @AfterClass
//    public static void confirmCounts() {
//    	assertTrue(successCount + failureCount == 10);
//    }
    
}