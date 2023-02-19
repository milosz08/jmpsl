/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: CommunicationEnv.java
 * Last modified: 15/02/2023, 00:52
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

package org.jmpsl.communication;

import lombok.*;
import org.springframework.core.env.Environment;

import java.util.Locale;
import org.jmpsl.core.env.*;

import static org.jmpsl.core.env.EnvPropertyHandler.getPostTypedProperty;

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
     * Define email Freemarker templates directory path. By default "classpath:/templates". Property required.
     * Templates should be in /resources directory.
     *
     * @since 1.0.2
     */
    __COM_FREEMARKER_PATH("jmpsl.communication.mail.freemarker-templates-dir", "classpath:/templates", false),

    /**
     * Avaialble application locales (defined as array list, for ex. <i>fr,pl,en_GB,en_US</i>). Property not required.
     * By default it is en_US.
     *
     * @see Locale
     * @since 1.0.2
     */
    __COM_AVAILABLE_LOCALES("jmpsl.communication.locale.available-locales", "en_US", false),

    /**
     * Default selected application locale (defined as locale string, for ex. <i>en_US</i>). Property not required.
     * By default it is en_US.
     *
     * @see Locale
     * @since 1.0.2
     */
    __COM_DEFAULT_LOCALE("jmpsl.communication.locale.default-locale", "en_US", false);

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
