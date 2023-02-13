/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: CorsPolicyConfigurer.java
 * Last modified: 13/02/2023, 03:22
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

import org.springframework.http.HttpMethod;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;

import static org.springframework.util.Assert.notNull;
import static pl.miloszgilga.lib.jmpsl.security.SecurityEnv.*;

/**
 * Configuration class storing method for CORS policy MVC spring boot application configuration.
 */
@Configuration
public class CorsPolicyConfigurer {

    private final String corsClient;
    private final long maxAge;

    public CorsPolicyConfigurer(Environment env) {
        corsClient = __SEC_CORS_CLIENT.getProperty(env);
        maxAge = __SEC_CORS_MAX_AGE.getProperty(env, Long.class);
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
        notNull(registry, "Registry object cannot be null.");
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
