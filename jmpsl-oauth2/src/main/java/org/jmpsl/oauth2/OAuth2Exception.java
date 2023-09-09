/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2Exception.java
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

package org.jmpsl.oauth2;

import org.springframework.http.HttpStatus;

import org.jmpsl.core.i18n.LocaleSet;
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
            super(HttpStatus.UNAUTHORIZED, LocaleSet.OAUTH2_AUTHENTICATION_PROCESSING_EXC);
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
            super(HttpStatus.NOT_FOUND, LocaleSet.OAUTH2_SUPPLIER_NOT_IMPLEMENTED_EXC);
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
            super(HttpStatus.BAD_REQUEST, LocaleSet.OAUTH2_URI_NOT_SUPPORTED_EXC);
        }
    }
}
