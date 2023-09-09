/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: CommunicationEnv.java
 * Last modified: 25/05/2023, 15:38
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

package org.jmpsl.communication;

import lombok.Getter;
import lombok.AllArgsConstructor;

import org.springframework.core.env.Environment;

import org.jmpsl.core.env.EnvDataPayload;
import org.jmpsl.core.env.IEnvEnumExtender;
import org.jmpsl.core.env.EnvPropertyHandler;

/**
 * Enum responsible for storing all environment variables from <code>application.properties</code> file for JMPSL
 * Communication library module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 * @see EnvPropertyHandler
 * @see EnvDataPayload
 */
@Getter
@AllArgsConstructor
public enum CommunicationEnv implements IEnvEnumExtender {

    /**
     * Define email Freemarker templates directory path. By default "classpath:/templates". Property not required.
     * Templates should be in /resources directory.
     *
     * @since 1.0.2
     */
    __COM_FREEMARKER_PATH("jmpsl.communication.mail.freemarker-templates-dir", "classpath:/templates", false);

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
