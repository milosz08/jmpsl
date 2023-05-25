/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: CoreEnv.java
 * Last modified: 19/05/2023, 23:53
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

package org.jmpsl.core;

import lombok.Getter;
import lombok.AllArgsConstructor;

import org.springframework.core.env.Environment;

import java.util.Locale;

import org.jmpsl.core.env.EnvDataPayload;
import org.jmpsl.core.env.IEnvEnumExtender;
import org.jmpsl.core.env.EnvPropertyHandler;

/**
 * Enum responsible for storing all environment variables from <code>application.properties</code> file for JMPSL
 * Core library module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 * @see EnvPropertyHandler
 * @see EnvDataPayload
 */
@Getter
@AllArgsConstructor
public enum CoreEnv implements IEnvEnumExtender {

    /**
     * Avaialble application locales (defined as array list, for ex. <i>fr,pl,en_GB,en_US</i>). Property not required.
     * By default it is en_US.
     *
     * @see Locale
     * @since 1.0.2
     */
    __CORE_AVAILABLE_LOCALES("jmpsl.core.locale.available-locales", "en_US", false),

    /**
     * Default selected application locale (defined as locale string, for ex. <i>en_US</i>). Property not required.
     * By default it is en_US.
     *
     * @see Locale
     * @since 1.0.2
     */
    __CORE_DEFAULT_LOCALE("jmpsl.core.locale.default-locale", "en_US", false),

    /**
     * Default locale message bundles. Property not required. Default location is in classpath: 'i18n/messages'.
     *
     * @see Locale
     * @since 1.0.2
     */
    __CORE_LOCALE_BUNDLE_PATH("jmpsl.core.locale.messages-paths", "i18n/messages", false);

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
