/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: BasicAuthServerException.java
 * Last modified: 13/02/2023, 02:52
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

package org.jmpsl.core.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import java.util.*;

/**
 * Simple server exception extending basic spring security {@link AuthenticationException} with {@link HttpStatus}
 * parameter passed in exception constructor.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
public class RestServiceAuthServerException extends AuthenticationException {

    private final HttpStatus status;
    private final String patternLocaleMessage;
    private Map<String, Object> variables = new HashMap<>();

    public RestServiceAuthServerException(HttpStatus status, String patterLocaleMessage) {
        super(patterLocaleMessage);
        this.status = status;
        this.patternLocaleMessage = patterLocaleMessage;
    }

    public RestServiceAuthServerException(HttpStatus status, String patterLocaleMessage, Map<String, Object> variables) {
        super(patterLocaleMessage);
        this.status = status;
        this.patternLocaleMessage = patterLocaleMessage;
        this.variables = variables;
    }
}
