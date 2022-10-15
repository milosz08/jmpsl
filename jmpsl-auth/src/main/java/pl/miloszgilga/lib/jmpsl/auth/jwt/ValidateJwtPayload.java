/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: ValidateJwtPayload.java
 * Last modified: 14/10/2022, 22:51
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

package pl.miloszgilga.lib.jmpsl.auth.jwt;

import java.util.Optional;
import io.jsonwebtoken.Claims;

/**
 * Payload POJO class for storing validated token info (validation type and optional claims). If token is invalid,
 * claims parameter is null.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class ValidateJwtPayload {
    private final JwtValidationType type;
    private final Optional<Claims> claims;

    /**
     * Constructor available to create full set of {@link ValidateJwtPayload} POJO class.
     *
     * @param type enum type of {@link JwtValidationType} enum class
     * @param claims claims object as {@link Optional} objects
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public ValidateJwtPayload(JwtValidationType type, Optional<Claims> claims) {
        this.type = type;
        this.claims = claims;
    }

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

    /**
     * @return one of the {@link JwtValidationType} enum class
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public JwtValidationType getType() {
        return type;
    }

    /**
     * @return claims wrapped in {@link Optional} class
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public Optional<Claims> getClaims() {
        return claims;
    }
}
