/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: CorsPolicyConfigurer.java
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

package org.jmpsl.security;

import org.springframework.util.Assert;
import org.springframework.http.HttpMethod;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;

/**
 * Configuration class storing method for CORS policy MVC spring boot application configuration.
 */
@Configuration
public class CorsPolicyConfigurer {

    private final String corsClient;
    private final long maxAge;

    public CorsPolicyConfigurer(Environment env) {
        corsClient = SecurityEnv.__SEC_CORS_CLIENT.getProperty(env);
        maxAge = SecurityEnv.__SEC_CORS_MAX_AGE.getProperty(env, Long.class);
    }

    /**
     * Configure CORS policy based passed not nullable registry object and available methods from {@link HttpMethod}
     * enum class.
     *
     * @param registry spring boot {@link CorsRegistry} object not nullable object
     * @param availableMethods available methods as string array from {@link HttpMethod} enum class
     *
     * @throws IllegalArgumentException if {@link CorsRegistry} object is null
     */
    public void configureCorsPolicy(CorsRegistry registry, String[] availableMethods) {
        Assert.notNull(registry, "Registry object cannot be null.");
        registry.addMapping("/**")
            .allowedOrigins(corsClient)
            .allowedMethods(availableMethods)
            .allowCredentials(true)
            .allowedHeaders("*")
            .maxAge(maxAge);
    }

    /**
     * Configure CORS policy based passed not nullable registry object and all available methods from
     * {@link HttpMethod} enum class.
     *
     * @param registry spring boot {@link CorsRegistry} object not nullable object
     */
    public void configureCorsPolicy(CorsRegistry registry) {
        final String[] allowedOrigins = Arrays.stream(HttpMethod.values()).map(HttpMethod::name).toArray(String[]::new);
        configureCorsPolicy(registry, allowedOrigins);
    }
}
