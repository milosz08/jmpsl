/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: LocaleUtilTest.java
 * Last modified: 20/02/2023, 00:09
 * Project name: jmps-library
 *
 * Licensed under the MIT license; you may not use this file except in compliance with the License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL
 * COPIES OR SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 */

package org.jmpsl.communication.locale;

import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocaleUtilTest {

    @Test
    void extractVariablesFromMessage_test() {
        final Map<String, Object> testingParameters = Map.of(
            "param1", "test1",
            "param2", "test2"
        );
        final String testingMessage = "This is a test message with {{param1}} and {{param2}}.";
        final String resultMessage = "This is a test message with test1 and test2.";

        String testResult = LocaleUtil.extractVariablesFromMessage(testingMessage, testingParameters);
        assertEquals(resultMessage, testResult);
    }
}
