/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: GeneralServerExceptionResDto.java
 * Last modified: 20/02/2023, 01:14
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

/**
 * Simple POJO class for misc server errors returned to the client in JSON format. Rewirte to JSON in REST template.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
public class GeneralServerExceptionResDto extends ServerExceptionResDto {

    private final String message;

    public GeneralServerExceptionResDto(ServerExceptionResDto res, String message) {
        super(res.getServletTimestampUTC(), res.getStatusCode(), res.getStatusText(), res.getPath(), res.getMethod());
        this.message = message;
    }
}