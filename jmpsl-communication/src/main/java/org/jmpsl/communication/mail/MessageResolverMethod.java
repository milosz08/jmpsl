/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: MessageResolverMethod.java
 * Last modified: 25/05/2023, 15:38
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

package org.jmpsl.communication.mail;

import freemarker.template.*;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Class which representing Freemarker template method for handling i18n messages. Sample of use in .tfl file:
 * <pre>{@code
 *     <#assign testingMessage = i18n("testing.property", [ fullName, fullName ])>
 *     <p>${testingMessage}</p>
 * }</pre>
 *
 * @author Miłosz Gilga
 * @since 1.0.2_04
 *
 * @see JmpslMailService
 */
public class MessageResolverMethod implements TemplateMethodModelEx {

    private final Locale locale;
    private final MessageSource messageSource;

    public MessageResolverMethod(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    /**
     * Executable method representing function in Freemarker template (i18n) for handling messages from message bundle
     * file.
     *
     * @param arguments a {@link List} of {@link TemplateModel}-s, containing the arguments passed to the method. First
     *                  is the i18n identifier and seconds is the array of placeholders (optional)
     * @return parsed message from messages bundle file
     * @author Miłosz Gilga
     * @since 1.0.2_04
     *
     * @throws TemplateModelException if passed wrong number of arguments or invalid code value
     */
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.size() < 1 || arguments.size() > 2) {
            throw new TemplateModelException("Wrong number of arguments");
        }
        final String code = ((SimpleScalar) arguments.get(0)).getAsString();
        if (Objects.isNull(code)|| code.isEmpty()) {
            throw new TemplateModelException("Invalid code value '" + code + "'");
        }
        if (arguments.size() == 2) {
            final SimpleSequence args = ((SimpleSequence) arguments.get(1));
            final String[] argsParsed = new String[args.size()];
            for (int i = 0; i < args.size(); i++) {
                argsParsed[i] = ((SimpleScalar) args.get(i)).getAsString();
            }
            return messageSource.getMessage(code, argsParsed, locale);
        }
        return messageSource.getMessage(code, null, locale);
    }
}
