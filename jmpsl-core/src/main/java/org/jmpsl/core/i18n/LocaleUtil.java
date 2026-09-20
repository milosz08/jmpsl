/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: LocaleUtil.java
 * Last modified: 28/03/2023, 23:14
 * Project name: jmps-library
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package org.jmpsl.core.i18n;

import org.springframework.util.Assert;

import java.util.Map;

/**
 * Locale class with utilities static method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class LocaleUtil {

    private LocaleUtil() {
    }

    /**
     * Method responsible for replacing locale message variable placeholders with actual values stored in Map as
     * key (placeholder) and value (variable). If message or variables {@link Map} is null, throws
     * {@link IllegalArgumentException}. Otherwise return prepared string.
     *
     * @param message string message with placeholders, ex. <i>"This is a test with {{param1}} variable."</i>
     * @param variables {@link Map} collection of the variables, ex. <i>Map.of("param1", "my testing")</i>
     * @return prepared message, ex. <i>"This is a test with my testing variable."</i>
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed message or variables are null
     */
    public static String extractVariablesFromMessage(String message, Map<String, Object> variables) {
        Assert.notNull(message, "Message object cannot be null");
        Assert.notNull(variables, "Variables map cannot be null");
        for (final Map.Entry<String, Object> variable : variables.entrySet()) {
            message = message.replace("{{" + variable.getKey() + "}}", String.valueOf(variable.getValue()));
        }
        return message;
    }
}
