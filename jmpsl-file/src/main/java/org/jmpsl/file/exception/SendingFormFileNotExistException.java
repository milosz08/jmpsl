/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: SendingFormFileNotExistException.java
 * Last modified: 03/11/2022, 01:12
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

package org.jmpsl.file.exception;

import org.springframework.http.HttpStatus;

import org.jmpsl.file.FileLocaleSet;
import org.jmpsl.core.exception.RestServiceServerException;

/**
 * Exception throw, when user attempt to send file without actual file. Extended {@link RestServiceServerException}, so return
 * JSON object in response body part.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class SendingFormFileNotExistException extends RestServiceServerException {

    public SendingFormFileNotExistException() {
        super(HttpStatus.NOT_FOUND, FileLocaleSet.SENDING_FORM_FILE_NOT_EXIST_EXC);
    }
}
