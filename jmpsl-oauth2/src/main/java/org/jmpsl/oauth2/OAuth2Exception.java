/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OAuth2Exception.java
 * Last modified: 02/11/2022, 14:32
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

package org.jmpsl.oauth2;

import org.springframework.http.HttpStatus;

import org.jmpsl.core.exception.RestServiceAuthServerException;

/**
 * Custom exceptions (extends {@link RestServiceAuthServerException}) used in OAuth2 JMPS library module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class OAuth2Exception {

    /**
     * Custom exception extending {@link RestServiceAuthServerException}, returning JSON POJO object message and throw after
     * unexpected OAuth2 processing error (unable to authorize user).
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class OAuth2AuthenticationProcessingException extends RestServiceAuthServerException {
        public OAuth2AuthenticationProcessingException() {
            super(HttpStatus.UNAUTHORIZED, OAuth2LocaleSet.AUTHENTICATION_PROCESSING_EXC);
        }
    }

    /**
     * Custom exception extending {@link RestServiceAuthServerException}, returning JSON POJO object message and throw after
     * attempt to login via unusupported OAuth2 supplier.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class OAuth2SupplierNotImplementedException extends RestServiceAuthServerException {
        public OAuth2SupplierNotImplementedException() {
            super(HttpStatus.NOT_FOUND, OAuth2LocaleSet.SUPPLIER_NOT_IMPLEMENTED_EXC);
        }
    }

    /**
     * Custom exception extending {@link RestServiceAuthServerException}, returning JSON POJO object message and throw after
     * attempt to redirect into usupported URI after successful authorize via OAuth2.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class OAuth2UriNotSupportedException extends RestServiceAuthServerException {
        public OAuth2UriNotSupportedException() {
            super(HttpStatus.BAD_REQUEST, OAuth2LocaleSet.URI_NOT_SUPPORTED_EXC);
        }
    }
}
