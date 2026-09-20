/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2Cookie.java
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

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * Enum class storing all cookies names for OAuth2 redirect session management.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
@AllArgsConstructor
public enum OAuth2Cookie {

    /**
     * Cookie name for authorization on register via OAuth2 service.
     *
     * @since 1.0.2
     */
    SESSION_REMEMBER("oauth2_auth_request"),

    /**
     * Cookie name for base redirect uri after successful authorization via OAuth2 service.
     *
     * @since 1.0.2
     */
    BASE_REDIRECT_URI("base_uri"),

    /**
     * Cookie name for login redirect uri after successful login via OAuth2 service.
     *
     * @since 1.0.2
     */
    AFTER_LOGIN_REDIRECT_URI("after_login_uri"),

    /**
     * Cookie name for signup redirect uri after successful signup via OAuth2 service.
     *
     * @since 1.0.2
     */
    AFTER_SIGNUP_REDIRECT_URI("after_signup_uri");

    private final String cookieName;
}
