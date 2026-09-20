/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: GfxEnv.java
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

package org.jmpsl.gfx;

import lombok.Getter;
import lombok.AllArgsConstructor;

import org.springframework.core.env.Environment;

import org.jmpsl.core.env.EnvDataPayload;
import org.jmpsl.core.env.IEnvEnumExtender;
import org.jmpsl.core.env.EnvPropertyHandler;

/**
 * Enum responsible for storing all environment variables from <code>application.properties</code> file for JMPSL GFX
 * library module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 * @see EnvPropertyHandler
 * @see EnvDataPayload
 */
@Getter
@AllArgsConstructor
public enum GfxEnv implements IEnvEnumExtender {

    /**
     * Define SSH/SFTP server path for static user graphics resources. By default "". Property required.
     *
     * @since 1.0.2
     */
    __GFX_SFTP_PATH("jmpsl.gfx.user-gfx.static-images-content-path", "", true),

    /**
     * Define font location for user default profile image generator. Property non-required. Font file should be in
     * /resources directory.
     *
     * @since 1.0.2
     */
    __GFX_USER_FONT_LINK("jmpsl.gfx.user-gfx.preferred-font-link", null, false),

    /**
     * Define font name for user default profile image generator. Property required.
     *
     * @since 1.0.2
     */
    __GFX_USER_FONT_NAME("jmpsl.gfx.user-gfx.preferred-font-name", null, true),

    /**
     * Define string array representation of hex colors used to generated random background image in default user
     * profile image. Property non-required. Colors must be separated by a comma.
     *
     * @since 1.0.2
     */
    __GFX_USER_HEX_COLORS("jmpsl.gfx.user-gfx.preferred-hex-colors", null, false),

    /**
     * Define hex representing foreground color of user initials on default generated profile image. By default
     * "#ffffff". Property non-required.
     *
     * @since 1.0.2
     */
    __GFX_USER_FG_COLOR("jmpsl.gfx.user-gfx.preferred-foreground-color", "#ffffff", false);

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
