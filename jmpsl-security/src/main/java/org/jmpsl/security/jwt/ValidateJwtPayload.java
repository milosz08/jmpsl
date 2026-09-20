/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: ValidateJwtPayload.java
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

package org.jmpsl.security.jwt;

import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;

import io.jsonwebtoken.Claims;

import java.util.Optional;

/**
 * Payload POJO class for storing validated token info (validation type and optional claims). If token is invalid,
 * claims parameter is null.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
@Builder
@AllArgsConstructor
public class ValidateJwtPayload {
    private final JwtValidationType type;
    private final Optional<Claims> claims;

    /**
     * Constructor available to create {@link ValidateJwtPayload} POJO class withotut claims (claim {@link Optional})
     * object is by default empty.
     *
     * @param type enum type of {@link JwtValidationType} enum class
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public ValidateJwtPayload(JwtValidationType type) {
        this.type = type;
        this.claims = Optional.empty();
    }
}
