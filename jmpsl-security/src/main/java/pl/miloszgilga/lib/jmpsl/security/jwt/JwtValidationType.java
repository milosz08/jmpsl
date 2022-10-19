/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: JwtValidationType.java
 * Last modified: 15/10/2022, 19:59
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

package pl.miloszgilga.lib.jmpsl.security.jwt;

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
