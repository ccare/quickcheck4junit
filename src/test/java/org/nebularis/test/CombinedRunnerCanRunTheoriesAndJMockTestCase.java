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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.nebularis.test.annotations.RunnerConfig;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit test for simple App.
 */
//@RunWith(CombinedRunner.class)
//@RunnerConfig({Theories.class})
@RunWith(Theories.class)
public class CombinedRunnerCanRunTheoriesAndJMockTestCase extends AbstractJMockTestSupport {

    static boolean theoryRan = false;
    static boolean mockRan = false;

    @DataPoint
    public static Integer DATA = 123;

    @Theory
    public void testCanRunTheory(Integer i) {
        theoryRan = true;
        assertThat(i, is(equalTo(DATA)));
    }

    @Test
    public void testCanRunNormalJMock() {
        mockRan = true;
        final CharSequence converter = mock(CharSequence.class);
        one(converter).length();
        will(returnValue(59));
        confirmExpectations();

        assertThat(converter.length(), is(equalTo(59)));
    }

    @AfterClass
    public static void checkTestsRan() {
        assertTrue("My theory-based test did not run", theoryRan);
        assertTrue("My mock-based test did not run", mockRan);
    }


}