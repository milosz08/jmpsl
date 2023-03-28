/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: ServerExceptionResDto.java
 * Last modified: 06/03/2023, 17:16
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

package org.jmpsl.core.exception;

import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;

import org.springframework.util.Assert;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Simple POJO class for storing server exception JSON object values (without error message parameters).
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
@Builder
@AllArgsConstructor
public class ServerExceptionResDto {
    private final String timestamp;
    private final int status;
    private final String error;
    private final String method;

    /**
     * Static method responsible for generate {@link ServerExceptionResDto} object from request parameters and status
     * code in form of {@link HttpStatus} object.
     *
     * @param status server response status code from {@link HttpStatus} object
     * @param req request object {@link HttpServletRequest} injected by Tomcat Servlet Container
     * @return generated {@link ServerExceptionResDto} object
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if status of req parameter is null
     */
    public static ServerExceptionResDto generate(HttpStatus status, HttpServletRequest req) {
        Assert.noNullElements(new Object[] { status, req }, "Status or req parameter cannot be null.");
        return ServerExceptionResDto.builder()
            .method(req.getMethod())
            .status(status.value())
            .error(status.name())
            .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toString())
            .build();
    }
}
