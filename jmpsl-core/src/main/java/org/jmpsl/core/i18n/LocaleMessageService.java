/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: LocaleMessageService.java
 * Last modified: 17/05/2023, 23:24
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

import org.springframework.stereotype.Service;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Map;

/**
 * Rest service for getting locale message based current spring context holder locale and message source from
 * <code>messages.properties</code> message source files.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Service
public class LocaleMessageService {

    private final LocaleConfigurerExtender localeConfigurerExtender;

    public LocaleMessageService(LocaleConfigurerExtender localeConfigurerExtender) {
        this.localeConfigurerExtender = localeConfigurerExtender;
    }

    /**
     * Method responsible for returning message based current spring context holder locale and message source pattern
     * passed in method arguments. Additionaly parsed template strings in message from attributes parameter.
     * 
     * @param placeholder message pattern declared in <code>message.properties</code> files from enum implements
     *                    {@link ILocaleEnumSet} interface
     * @param attributes template string as {@link Map} of key -> value attributes
     * @return localized message value or string pattern, if message does not exist
     * @author Miłosz Gilga
     * @since 1.0.2_04
     * 
     * @see #getMessage(ILocaleEnumSet placeholder)
     */
    public String getMessage(ILocaleEnumSet placeholder, Map<String, Object> attributes) {
        try {
            String resourceText = localeConfigurerExtender.messageSource()
                .getMessage(placeholder.getHolder(), null, LocaleContextHolder.getLocale());
            if (resourceText.isBlank()) {
                return placeholder.getHolder();
            }
            for (final Map.Entry<String, Object> param : attributes.entrySet()) {
                resourceText = resourceText.replace("{{" + param.getKey() + "}}", String.valueOf(param.getValue()));
            }
            return resourceText;
        } catch (NoSuchMessageException ex) {
            return placeholder.getHolder();
        }
    }

    /**
     * Method responsible for returning message based current spring context holder locale and message source pattern
     * passed in method arguments.
     *
     * @param placeholder message pattern declared in <code>message.properties</code> files from enum implements
     *                    {@link ILocaleEnumSet} interface
     * @return localized message value or string pattern, if message does not exist
     * @author Miłosz Gilga
     * @since 1.0.2_04
     */
    public String getMessage(ILocaleEnumSet placeholder) {
        return getMessage(placeholder, Map.of());
    }

    /**
     * Method responsible for returning message based current spring context holder locale and message source pattern
     * passed in method arguments.
     *
     * @param placeholder raw message pattern declared in <code>message.properties</code> files
     * @return localized message value or string pattern, if message does not exist
     * @author Miłosz Gilga
     * @since 1.0.2_04
     */
    public String getMessage(String placeholder) {
        try {
            String resourceText = localeConfigurerExtender.messageSource()
                .getMessage(placeholder, null, LocaleContextHolder.getLocale());
            if (resourceText.isBlank()) {
                return placeholder;
            }
            return resourceText;
        } catch (NoSuchMessageException ex) {
            return placeholder;
        }
    }
}
