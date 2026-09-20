/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: SecurityEnv.java
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
