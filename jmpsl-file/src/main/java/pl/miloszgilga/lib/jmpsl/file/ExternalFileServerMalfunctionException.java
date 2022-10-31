/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: ExternalFileServerMalfunctionException.java
 * Last modified: 31/10/2022, 12:40
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

package pl.miloszgilga.lib.jmpsl.file;

import org.springframework.http.HttpStatus;
import pl.miloszgilga.lib.jmpsl.util.exception.BasicServerException;

/**
 * Custom exception throws after SFTP image sending malfunction. Extended {@link BasicServerException}, so return
 * JSON object in response body part.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class ExternalFileServerMalfunctionException extends BasicServerException {

    public ExternalFileServerMalfunctionException() {
        super(HttpStatus.SERVICE_UNAVAILABLE, "Unable to send file. Try again later.", new Object());
    }
}
