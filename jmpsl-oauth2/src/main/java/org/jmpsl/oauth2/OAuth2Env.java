/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2Env.java
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

import org.springframework.core.env.Environment;

import org.jmpsl.core.env.EnvDataPayload;
import org.jmpsl.core.env.IEnvEnumExtender;
import org.jmpsl.core.env.EnvPropertyHandler;

/**
 * Enum responsible for storing all environment variables from <code>application.properties</code> file for JMPSL OAuth2
 * library module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 * @see EnvPropertyHandler
 * @see EnvDataPayload
 */
@Getter
@AllArgsConstructor
public enum OAuth2Env implements IEnvEnumExtender {

    /**
     * Define time (in minutes) after cookie in OAuth2 external service expire. By default "3". Property required.
     *
     * @since 1.0.2
     */
    __OAT_COOKIE_EXP("jmpsl.auth.oauth2.cookie-expired-minutes", "3", true),

    /**
     * Define string array representation of OAuth2 suppliers available in current application. Property required.
     * Suppliers must be separated by a comma.
     *
     * @since 1.0.2
     * @see OAuth2Supplier
     */
    __OAT_SUPPLIERS("jmpsl.oauth2.available-suppliers", null, true),

    /**
     * Define string array representation of OAuth2 redirect address after successfully user authentication. Property
     * required. Redirect address must be separated by a comma.
     *
     * @since 1.0.2
     */
    __OAT_REDIR_URIS("jmpsl.oauth2.redirect-uris", null, true);

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
