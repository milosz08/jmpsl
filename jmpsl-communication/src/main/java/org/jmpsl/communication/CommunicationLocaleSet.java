/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: CommunicationLocaleSet.java
 * Last modified: 17/03/2023, 10:31
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

package org.jmpsl.communication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.jmpsl.core.i18n.ILocaleEnumSet;

/**
 * Enum set storing all placeholders of internationalization messages for communication JMPS library module.
 * Implementing {@link ILocaleEnumSet} interface from JMPSL core module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 * @see ILocaleEnumSet
 */
@Getter
@RequiredArgsConstructor
public enum CommunicationLocaleSet implements ILocaleEnumSet {
    UNABLE_TO_SEND_EMAIL_EXC                        ("jmpsl.communication.exception.UnableToSendEmailException");

    /**
     * Internationalization property holder.
     *
     * @since 1.0.2
     */
    private final String holder;
}
