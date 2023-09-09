/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: ServerExceptionResDto.java
 * Last modified: 28/03/2023, 23:14
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
