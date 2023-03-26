/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OAuth2Cookie.java
 * Last modified: 06/03/2023, 17:16
 * Project name: jmps-library
 *
 * Licensed under the MIT license; you may not use this file except in compliance with the License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL COPIES OR
 * SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 *
 * The software is provided “as is”, without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
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
