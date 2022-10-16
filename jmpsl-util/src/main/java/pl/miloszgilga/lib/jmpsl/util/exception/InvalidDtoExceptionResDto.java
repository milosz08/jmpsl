/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: InvalidDtoExceptionResDto.java
 * Last modified: 15/10/2022, 20:17
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

import lombok.Getter;
import java.util.List;

/**
 * Simple POJO class for javax validator errors from request's DTO objects. Rewirte to JSON in REST template.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
public class InvalidDtoExceptionResDto extends ServerExceptionResDto {

    private final List<String> errors;

    public InvalidDtoExceptionResDto(final ServerExceptionResDto res, final List<String> errors) {
        super(res.getServletTimestampUTC(), res.getStatusCode(), res.getStatusText(), res.getPath(), res.getMethod());
        this.errors = errors;
    }

    public InvalidDtoExceptionResDto(final ServerExceptionResDto res, final String error) {
        super(res.getServletTimestampUTC(), res.getStatusCode(), res.getStatusText(), res.getPath(), res.getMethod());
        this.errors = List.of(error);
    }
}
