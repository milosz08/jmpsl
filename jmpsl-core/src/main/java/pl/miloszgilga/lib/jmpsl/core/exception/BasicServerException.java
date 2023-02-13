/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: BasicServerException.java
 * Last modified: 15/10/2022, 20:26
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

package pl.miloszgilga.lib.jmpsl.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;

/**
 * Simple server exception extending basic {@link RuntimeException} with {@link HttpStatus} parameter passed in
 * exception constructor.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
public class BasicServerException extends RuntimeException {

    private final HttpStatus status;

    public BasicServerException(HttpStatus status, String message, Object... args) {
        super(format(message, args));
        this.status = status;
    }
}
