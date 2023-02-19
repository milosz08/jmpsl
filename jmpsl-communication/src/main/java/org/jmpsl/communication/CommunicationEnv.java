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
enum MailEnv implements IEnvEnumExtender {

    /**
     * Define email Freemarker templates directory path. By default "classpath:/templates". Property required.
     * Templates should be in /resources directory.
     *
     * @since 1.0.2
     */
    __COM_FREEMARKER_PATH("jmpsl.mail.freemarker-templates-dir", "classpath:/templates", true);

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
