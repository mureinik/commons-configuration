/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Iterator;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@code DisabledListDelimiterHandler}. Note that some
 * functionality of the base class is tested, too.
 *
 * @version $Id$
 */
public class TestDisabledListDelimiterHandler
{
    /** An array with some test values. */
    private static final Object[] VALUES = {
            20130630213801L, "A test value", 5
    };

    /** Constant for a test string value. */
    private static final String STR_VALUE = "  A test, string; value! ";

    /** The instance to be tested. */
    private DisabledListDelimiterHandler handler;

    @Before
    public void setUp() throws Exception
    {
        handler = new DisabledListDelimiterHandler();
    }

    /**
     * Checks whether the passed in iterator contains the expected values.
     *
     * @param it the iterator to test
     */
    private static void checkIterator(Iterator<?> it)
    {
        for (Object o : VALUES)
        {
            assertEquals("Wrong value", o, it.next());
        }
        assertFalse("Iterator has too many objects", it.hasNext());
    }

    /**
     * Tests whether the values of an array can be extracted.
     */
    @Test
    public void testParseArray()
    {
        checkIterator(handler.parse(VALUES));
    }

    /**
     * Tests whether the values of an Iterable object can be extracted.
     */
    @Test
    public void testParseIterable()
    {
        checkIterator(handler.parse(Arrays.asList(VALUES)));
    }

    /**
     * Tests whether the values of an Iterator object can be extracted.
     */
    @Test
    public void testParseIterator()
    {
        checkIterator(handler.parse(Arrays.asList(VALUES).iterator()));
    }

    /**
     * Tests whether a simple string value can be parsed.
     */
    @Test
    public void testParseSimpleValue()
    {
        Iterator<?> it = handler.parse(STR_VALUE);
        assertEquals("Wrong value", STR_VALUE, it.next());
        assertFalse("Too many values", it.hasNext());
    }

    /**
     * Tests whether a null value can be parsed.
     */
    @Test
    public void testParseNull()
    {
        assertFalse("Got a value", handler.parse(null).hasNext());
    }

    /**
     * Tests whether a string value is correctly escaped. The string should not
     * be modified.
     */
    @Test
    public void testEscapeStringValue()
    {
        assertEquals("Wrong escaped string", STR_VALUE,
                handler.escape(STR_VALUE, ListDelimiterHandler.NOOP_TRANSFORMER));
    }

    /**
     * Tests whether the transformer is correctly invoked when escaping a
     * string.
     */
    @Test
    public void testEscapeStringValueTransformer()
    {
        ValueTransformer trans = EasyMock.createMock(ValueTransformer.class);
        String testStr = "Some other string";
        EasyMock.expect(trans.transformValue(testStr)).andReturn(STR_VALUE);
        EasyMock.replay(trans);
        assertEquals("Wrong escaped string", STR_VALUE,
                handler.escape(testStr, trans));
        EasyMock.verify(trans);
    }

    /**
     * Tests whether a non-string value is correctly escaped. The object should
     * not be modified.
     */
    @Test
    public void testEscapeNonStringValue()
    {
        Object value = 42;
        assertEquals("Wrong escaped object", value,
                handler.escape(value, ListDelimiterHandler.NOOP_TRANSFORMER));
    }

    /**
     * Tests whether the transformer is correctly called when escaping a non
     * string value.
     */
    @Test
    public void testEscapeNonStringValueTransformer()
    {
        ValueTransformer trans = EasyMock.createMock(ValueTransformer.class);
        Object value = 42;
        EasyMock.expect(trans.transformValue(value)).andReturn(STR_VALUE);
        EasyMock.replay(trans);
        assertEquals("Wrong escaped object", STR_VALUE,
                handler.escape(value, trans));
        EasyMock.verify(trans);
    }

    /**
     * Tests escapeList(). This operation is not supported.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testEscapeList()
    {
        handler.escapeList(Arrays.asList(VALUES),
                ListDelimiterHandler.NOOP_TRANSFORMER);
    }
}