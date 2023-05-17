/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: LocaleMessageService.java
 * Last modified: 17/03/2023, 15:08
 * Project name: jmps-library
 *
 * Licensed under the MIT license; you may not use this file except in compliance with the License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL COPIES OR
 * SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
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
     * @since 1.0.2_03
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
     * @since 1.0.2_03
     */
    public String getMessage(ILocaleEnumSet placeholder) {
        return getMessage(placeholder, Map.of());
    }
}
