/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: ServerExceptionResDto.java
 * Last modified: 15/10/2022, 20:07
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

package pl.miloszgilga.lib.jmpsl.util.exception;

import lombok.*;

import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;

import pl.miloszgilga.lib.jmpsl.util.TimeUtil;

import static java.util.Objects.isNull;

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
    private final String servletTimestampUTC;
    private final int statusCode;
    private final String statusText;
    private final String path;
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
     * @throws NullPointerException if status of req parameter is null
     */
    public static ServerExceptionResDto generate(final HttpStatus status, final HttpServletRequest req) {
        if (isNull(status) || isNull(req)) throw new NullPointerException("Status or req parameter cannot be null.");
        return ServerExceptionResDto.builder()
                .path(req.getServletPath())
                .method(req.getMethod())
                .statusCode(status.value())
                .statusText(status.name())
                .servletTimestampUTC(TimeUtil.serializedUTC())
                .build();
    }
}
