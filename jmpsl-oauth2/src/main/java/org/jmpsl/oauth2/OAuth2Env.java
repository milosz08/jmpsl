/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OAuth2Env.java
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
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
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
