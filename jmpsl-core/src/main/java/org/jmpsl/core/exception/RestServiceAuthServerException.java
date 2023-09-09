/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: RestServiceAuthServerException.java
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

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;
import java.util.HashMap;

import org.jmpsl.core.i18n.ILocaleEnumSet;

/**
 * Simple server exception extending basic spring security {@link AuthenticationException} with {@link HttpStatus}
 * parameter passed in exception constructor.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
public class RestServiceAuthServerException extends AuthenticationException {

    private final HttpStatus status;
    private final ILocaleEnumSet localeEnumSet;
    private Map<String, Object> variables = new HashMap<>();

    public RestServiceAuthServerException(HttpStatus status, ILocaleEnumSet localeEnumSet) {
        super(localeEnumSet.getHolder());
        this.status = status;
        this.localeEnumSet = localeEnumSet;
    }

    public RestServiceAuthServerException(HttpStatus status, ILocaleEnumSet localeEnumSet, Map<String, Object> variables) {
        super(localeEnumSet.getHolder());
        this.status = status;
        this.localeEnumSet = localeEnumSet;
        this.variables = variables;
    }
}
