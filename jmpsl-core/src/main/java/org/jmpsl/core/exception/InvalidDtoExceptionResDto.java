/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: InvalidDtoExceptionResDto.java
 * Last modified: 18/05/2023, 00:01
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

import java.util.Map;

/**
 * Simple POJO class for jakarta validator errors from request's DTO objects. Rewirte to JSON in REST template.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
public class InvalidDtoExceptionResDto extends ServerExceptionResDto {

    private final Map<String, String> errors;

    public InvalidDtoExceptionResDto(ServerExceptionResDto res, Map<String, String> errors) {
        super(res.getTimestamp(), res.getStatus(), res.getError(), res.getMethod());
        this.errors = errors;
    }

    public InvalidDtoExceptionResDto(ServerExceptionResDto res, String fieldName, String value) {
        super(res.getTimestamp(), res.getStatus(), res.getError(), res.getMethod());
        this.errors = Map.of(fieldName, value);
    }
}
