/*
 *
 * def-proxy
 *
 * Copyright (c) 2010-2011
 * Tim Watson (watson.timothy@gmail.com), Charles Care (c.p.care@gmail.com).
 * All Rights Reserved.
 *
 * This file is provided to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain
 * a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.nebularis.test;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.Description;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import java.util.ArrayList;
import java.util.List;

public class AbstractJMockTestSupport extends org.jmock.Expectations {
    private final Mockery context = new JUnit4Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private boolean stubbed = false;

    protected void confirmExpectations() {
        if (!stubbed) context.checking(this);
    }

    protected void justIgnore(final Object... mocks) {
        for (final Object mock : mocks) {
            ignoring(mock);
        }
        confirmExpectations();
    }

    public <T> T stub(final Class<T> classToMock) {
        stubbed = true;
        return context.mock(classToMock);
    }

    public <T> T mock(final Class<T> classToMock) {
        return context.mock(classToMock);
    }

    public <T> T mock(final Class<T> classToMock, final String mockName) {
        return context.mock(classToMock, mockName);
    }

    public <T> T dummy(final Class<T> classToMock) {
        return ignoring(mock(classToMock));
    }

    public void applyMockAssertions() {
        context.assertIsSatisfied();
    }

    protected static Action returnSuccessiveValues(final Object... objects) {
        final String num = String.valueOf(objects.length);
        final List<Object> returnValues = new ArrayList<Object>();
        for (Object object : objects) {
            returnValues.add(object);
        }
        return new Action() {
            @Override
            public void describeTo(final Description description) {
            }

            @Override
            public Object invoke(final Invocation invocation) throws Throwable {
                if (returnValues.isEmpty()) {
                    throw new IllegalStateException("invocation count exceeded the " + num + " object(s) passed in");
                }
                final Object retval = returnValues.remove(0);
                if (retval != null) {
                    invocation.checkReturnTypeCompatibility(retval);
                }
                return retval;
            }
        };
    }
}