/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: UnableToPerformSftpActionException.java
 * Last modified: 02/11/2022, 14:32
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

package org.jmpsl.file.socket;

import org.jmpsl.core.exception.RestServiceServerException;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

/**
 * Custom exception throws after unable to connect with SFTP server or unable to perform other general SFTP action.
 * Extended {@link RestServiceServerException}, so return JSON object in response body part.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class UnableToPerformSftpActionException extends RestServiceServerException {

    public UnableToPerformSftpActionException() {
        super(SERVICE_UNAVAILABLE, "Unable to connect with SFTP server. Try again later.", new Object());
    }
}
