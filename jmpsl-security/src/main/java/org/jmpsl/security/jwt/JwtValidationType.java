/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: JwtValidationType.java
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

/**
 * Enum representing JWT validation state.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public enum JwtValidationType {

    /**
     * Enum constant represents malformed JWT.
     *
     * @since 1.0.2
     */
    MALFORMED("Passed JSON Web Token is malformed."),

    /**
     * Enum constant represents expired JWT.
     *
     * @since 1.0.2
     */
    EXPIRED("Passed JSON Web Token is expired."),

    /**
     * Enum constant represents invalid JWT.
     *
     * @since 1.0.2
     */
    INVALID("Passed JSON Web Token is invalid."),

    /**
     * Enum constant represents other problems with JWT (nullable claim/claims value/values).
     *
     * @since 1.0.2
     */
    OTHER("Some of the JSON Web Token claims are nullable."),

    /**
     * Enum constant represents JWT is valid.
     *
     * @since 1.0.2
     */
    GOOD("JSON Web Token is valid.");

    private final String message;

    JwtValidationType(String message) {
        this.message = message;
    }

    /**
     * Getter method returning JWT validation state message (for logger).
     *
     * @return validation custom message
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public String getMessage() {
        return message;
    }
}
