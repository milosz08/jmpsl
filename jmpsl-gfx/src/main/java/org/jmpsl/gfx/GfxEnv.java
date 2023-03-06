/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: GfxEnv.java
 * Last modified: 18/11/2022, 04:36
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
