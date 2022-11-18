/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: SecurityEnv.java
 * Last modified: 18.11.2022, 02:54
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

package pl.miloszgilga.lib.jmpsl.security;

import lombok.*;
import org.springframework.core.env.Environment;

import pl.miloszgilga.lib.jmpsl.core.env.*;
import static pl.miloszgilga.lib.jmpsl.core.env.EnvPropertyHandler.getPostTypedProperty;

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
     * Define CORS policy client (front-end application url). Property required.
     *
     * @since 1.0.2
     */
    __SEC_CORS_CLIENT("jmpsl.security.cors.client", null, true);

    private final String name;
    private final String defaultValue;
    private final boolean isRequired;

    @Override
    public String getProperty(Environment env) {
        return getPostTypedProperty(new EnvDataPayload(env, name, defaultValue, isRequired), String.class);
    }

    @Override
    public <T> T getProperty(Environment env, Class<T> targetClazz) {
        return getPostTypedProperty(new EnvDataPayload(env, name, defaultValue, isRequired), targetClazz);
    }
}
