/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: OAuth2Exception.java
 * Last modified: 18/10/2022, 21:17
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

package pl.miloszgilga.lib.jmpsl.oauth2;

import static org.springframework.http.HttpStatus.*;
import pl.miloszgilga.lib.jmpsl.util.exception.BasicAuthServerException;

/**
 * Custom exceptions (extends {@link BasicAuthServerException}) used in OAuth2 JMPS library module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class OAuth2Exception {

    /**
     * Custom exception extending {@link BasicAuthServerException}, returning JSON POJO object message and throw after
     * unexpected OAuth2 processing error (unable to authorize user).
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class OAuth2AuthenticationProcessingException extends BasicAuthServerException {
        public OAuth2AuthenticationProcessingException() {
            super(UNAUTHORIZED, "Unable to authorize user via OAuth2 service. Try again later.", new Object());
        }

        public OAuth2AuthenticationProcessingException(final String message, final Object... args) {
            super(UNAUTHORIZED, message, args);
        }
    }

    /**
     * Custom exception extending {@link BasicAuthServerException}, returning JSON POJO object message and throw after
     * unable to find OAuth2 authentication provider/supplier.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class OAuth2SupplierNotExistException extends BasicAuthServerException {
        public OAuth2SupplierNotExistException() {
            super(NOT_FOUND, "Passed OAuth2 service is invalid.", new Object());
        }
    }

    /**
     * Custom exception extending {@link BasicAuthServerException}, returning JSON POJO object message and throw after
     * attempt to login via unusupported OAuth2 supplier.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class OAuth2SupplierNotImplementedException extends BasicAuthServerException {
        public OAuth2SupplierNotImplementedException() {
            super(NOT_FOUND, "Passed OAuth2 service is not supported.", new Object());
        }
    }

    /**
     * Custom exception extending {@link BasicAuthServerException}, returning JSON POJO object message and throw after
     * attempt to redirect into usupported URI after successful authorize via OAuth2.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class OAuth2UriNotSupportedException extends BasicAuthServerException {
        public OAuth2UriNotSupportedException() {
            super(BAD_REQUEST, "Redirect URI is not supported by OAuth2 service.", new Object());
        }
    }
}
