/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: SecurityEnv.java
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

package org.jmpsl.security;

import lombok.Getter;
import lombok.AllArgsConstructor;

import org.springframework.core.env.Environment;

import org.jmpsl.core.env.EnvDataPayload;
import org.jmpsl.core.env.IEnvEnumExtender;
import org.jmpsl.core.env.EnvPropertyHandler;

/**
 * Enum responsible for storing all environment variables from <code>application.properties</code> file for JMPSL
 * Security library module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 * @see EnvPropertyHandler
 * @see EnvDataPayload
 */
@Getter
@AllArgsConstructor
public enum SecurityEnv implements IEnvEnumExtender {

    /**
     * Define, if OAuth2 service is active. By default "false". Property required.
     *
     * @since 1.0.2
     */
    __SEC_OAUTH2_ACTIVE("jmpsl.security.oauth2-active", "false", true),

    /**
     * Define One Time Access token lenght. By default "10". Property required.
     *
     * @since 1.0.2
     */
    __SEC_OTA_LENGTH("jmpsl.security.ota.length", "10", true),

    /**
     * Define password hash strength of BCrypt password encoder. By default "10". Property required.
     *
     * @since 1.0.2
     */
    __SEC_PSW_ENC_STRENGTH("jmpsl.security.password-encoder-strength", "10", true),

    /**
     * Define Json Web Token secret salt. Property required.
     *
     * @since 1.0.2
     */
    __SEC_JWT_SECRET("jmpsl.security.jwt.secret", null, true),

    /**
     * Define Json Web Token issuer. Property required.
     *
     * @since 1.0.2
     */
    __SEC_JWT_ISSUER("jmpsl.security.jwt.issuer", null, true),

    /**
     * Define Json Web Token expiration time (in minutes). By default it is 5 minutes.
     *
     * @since 1.0.2
     */
    __SEC_JWT_EXPIRED_MINUTES("jmpsl.security.jwt.expired-minutes", "5", false),

    /**
     * Define Refresh token expiration time (in days). By default it is 90 days (circa about 3 months).
     *
     * @since 1.0.2
     */
    __SEC_REFRESH_TOKEN_EXPIRED_DAYS("jmpsl.security.jwt.refresh-token-expired-days", "90", false),

    /**
     * Define CORS policy client (front-end application url). Property required.
     *
     * @since 1.0.2
     */
    __SEC_CORS_CLIENT("jmpsl.security.cors.client", null, true),

    /**
     * Define CORS max age. By default is 3600 milis.
     *
     * @since 1.0.2
     */
    __SEC_CORS_MAX_AGE("jmpsl.security.cors.max-age", "3600", true);

    private final String name;
    private final String defaultValue;
    private final boolean isRequired;

    @Override
    public String getProperty(Environment env) {
        return EnvPropertyHandler.getPostTypedProperty(new EnvDataPayload(env, name, defaultValue, isRequired), String.class);
    }

    @Override
    public <T> T getProperty(Environment env, Class<T> targetClazz) {
        return EnvPropertyHandler.getPostTypedProperty(new EnvDataPayload(env, name, defaultValue, isRequired), targetClazz);
    }
}
