/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: MessageResolverMethod.java
 * Last modified: 19/05/2023, 15:52
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
